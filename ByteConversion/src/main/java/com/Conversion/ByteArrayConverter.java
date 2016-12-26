package com.Conversion;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ByteArrayConverter {
	
	static Schema schema;
	
	public ByteArrayConverter() throws NoSuchFieldException, IllegalAccessException {
	}
	
	public void setSchema(Schema schema) {
		this.schema = schema;
	}
	
	public static class Person {
		public String name;
		public int age;
		public boolean nullbit;
		public String address;
		
		public long getNum() {
			return num;
		}
		
		public void setNum(long num) {
			this.num = num;
		}
		
		public long num;
		
		public boolean getNullbit() {
			return nullbit;
		}
		
		public void setNullbit(boolean nullbit) {
			this.nullbit = nullbit;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public int getAge() {
			return age;
		}
		
		public void setAge(int age) {
			this.age = age;
		}
		
		public String getAddress() {
			return address;
		}
		
		public void setAddress(String address) {
			this.address = address;
		}
	}
	
	public static class Schema {
		List<FieldInfo> fields = new ArrayList<>();
		
		void addField(String name, Type type) {
			FieldInfo fi = new FieldInfo();
			fi.setName(name);
			fi.setType(type);
			fields.add(fi);
		}
	}
	
	public static Schema createSchema() {
		
		Schema s = new Schema();
		
		s.addField("nullbit", Type.BOOLEAN);
		s.addField("name", Type.STRING);
		s.addField("age", Type.INTEGER);
		s.addField("address", Type.STRING);
		s.addField("num", Type.LONG);
		return s;
	}
	
	String getString(Object o, String fieldName) throws NoSuchFieldException, IllegalAccessException {
		Field f = o.getClass().getField(fieldName);
		return (String) f.get(o);
	}
	
	int getInteger(Object o, String fieldName) throws NoSuchFieldException, IllegalAccessException {
		Field f = o.getClass().getField(fieldName);
		return (Integer) f.get(o);
	}
	
	boolean getBoolean(Object o, String fieldName) throws IllegalAccessException, NoSuchFieldException {
		Field f=o.getClass().getField(fieldName);
		return (boolean) f.get(o);
	}
	
	long getLong(Object o, String fieldName) throws NoSuchFieldException, IllegalAccessException {
		Field f=o.getClass().getField(fieldName);
		return (long) f.get(o);
	}
	
	public int getRequiredLen(Object o) throws NoSuchFieldException, IllegalAccessException {
		int len = 0;
		for (FieldInfo field : schema.fields) {
			//System.out.println("examining field " + field);
			switch (field.type) {
				case BOOLEAN :
					len += 1;
					break;
				case INTEGER:
					len += 4;
					break;
				case STRING :
					len += 4;
					String s = getString(o, field.name);
					len += s.getBytes().length;
					len += 1;
					break;
				case SHORT:
					len +=2;
					break;
				case BYTE:
					len +=1;
					break;
				case DOUBLE:
					len +=8;
					break;
				case FLOAT:
					len +=4;
					break;
				case LONG:
					len +=8;
					break;
			}
		}
		return len;
	}
	
	public int calcVarOffset() throws NoSuchFieldException, IllegalAccessException {
		int varoff=0;
		for(FieldInfo field : schema.fields)
		{
			switch(field.type)
			{
				case INTEGER:
					varoff+=4;
					break;
				case STRING:
					varoff+=4;
					varoff+=1;
					break;
				case SHORT:
					varoff +=2;
					break;
				case BYTE:
					varoff +=1;
					break;
				case DOUBLE:
					varoff +=8;
					break;
				case FLOAT:
					varoff +=4;
					break;
				case LONG:
					varoff +=8;
					break;
				case BOOLEAN :
					varoff += 1;
					break;
			}
		}
		return varoff;
	}
	
	int offset=0;
	int varoffset=0;
	
	void writeInt(byte[] bytes, int val)
	{
		bytes[offset+0] = (byte)(val & 0xFF);
		bytes[offset+1] = (byte)((val & 0xFF00) >> 8);
		bytes[offset+2] = (byte)((val & 0xFF0000) >> 16);
		bytes[offset+3] = (byte)((val & 0xFF000000) >> 24);
	}
	
	void writeString(byte[] bytes, String str) throws NoSuchFieldException, IllegalAccessException
	{
		// System.out.println("Var offset :"+varoffset);
		writeInt(bytes, varoffset);
		offset+=1;
		
		bytes[offset]=(byte)str.length();
		
		byte[] strBytes=str.getBytes();
		
		for(int i=0;i<strBytes.length;i++)
		{
			bytes[i+varoffset]=strBytes[i];
		}
		varoffset += strBytes.length;
	}
	
	void writeBoolean(byte[] bytes, boolean bool)
	{
		if(bool==true)
		{
			bytes[offset]=1;
		}
		else {
			bytes[offset]=0;
		}
	}
	
	void writeLong(byte[] bytes, long val)
	{
		bytes[offset+0] = (byte)(val & 0xFFL);
		bytes[offset+1] = (byte)((val & 0xFF00L) >> 8);
		bytes[offset+2] = (byte)((val & 0xFF0000L) >> 16);
		bytes[offset+3] = (byte)((val & 0xFF000000L) >> 24);
		bytes[offset+4] = (byte)((val & 0xFF00000000L) >> 32);
		bytes[offset+5] = (byte)((val & 0xFF0000000000L) >> 40);
		bytes[offset+6] = (byte)((val & 0xFF000000000000L) >> 48);
		bytes[offset+7] = (byte)((val & 0xFF00000000000000L) >> 56);
	}
	
	
	public byte[] convert(Object o) throws NoSuchFieldException, IllegalAccessException {
		int required = getRequiredLen(o);
		varoffset=calcVarOffset();
		System.out.println("Length required to store object is " + required);
		byte[] bytes = new byte[required];
		
		for (FieldInfo field : schema.fields) {
			switch (field.type) {
				case INTEGER:
					int val = getInteger(o, field.name);
					writeInt(bytes, val);
					offset+=4;
					break;
				case STRING:
					String str=getString(o, field.name);
					writeString(bytes, str);
					offset+=4;
					break;
				case BOOLEAN:
					boolean bool=getBoolean(o, field.name);
					writeBoolean(bytes, bool);
					offset+=1;
					break;
				case LONG:
					long val2=getLong(o, field.name);
					writeLong(bytes, val2);
					offset+=8;
					break;
			}
		}
		return bytes;
	}
	
	int revoffset=0;
	int rev_varoffet=0;
	
	public int readInteger(byte[] bytes) throws NoSuchFieldException, IllegalAccessException
	{
		int val=(((int)bytes[revoffset+0]) << 0)|
				(((int)bytes[revoffset+1]) << 8)|
				(((int)bytes[revoffset+2]) << 16)|
				(((int)bytes[revoffset+3]) << 24);
		
		return val;
	}
	
	public long readLong(byte[] bytes)
	{
		long val=(((long) bytes[revoffset+0] & 0xFF))|
				(((long)bytes[revoffset+1] & 0xFF) << 8)|
				(((long)bytes[revoffset+2] & 0xFF) << 16)|
				(((long)bytes[revoffset+3] & 0xFF) << 24)|
				(((long)bytes[revoffset+4] & 0xFF) << 32)|
				(((long)bytes[revoffset+5] & 0xFF) << 40)|
				(((long)bytes[revoffset+6] & 0xFF) << 48)|
				(((long)bytes[revoffset+7] & 0xFF) << 54);
		return val;
	}
	
	public String readString(byte[] bytes) throws NoSuchFieldException, IllegalAccessException
	{
		String str="";
		//System.out.println("reverser var offset  : "+rev_varoffet);
		
		revoffset+=1;
		int len=(int)bytes[revoffset];
		
		for(int i=0;i<len;i++)
		{
			str+=(char)bytes[rev_varoffet+i];
		}
		rev_varoffet+=len;
		
		return str;
	}
	
	public boolean readBoolean(byte[] bytes)
	{
		boolean bool;
		if((int)bytes[revoffset]==1)
		{
			bool=true;
		}
		else
		{
			bool=false;
		}
		return bool;
	}
	
	
	public Object convertFromBytes(byte[] bytes, Object o1) throws NoSuchFieldException, IllegalAccessException
	{
		rev_varoffet=calcVarOffset();
		
		for(FieldInfo field : schema.fields)
		{
			switch (field.type)
			{
				case INTEGER:
					int val=readInteger(bytes);
					o1.getClass().getField(field.name).set(o1,val);
					revoffset+=4;
					break;
				case STRING:
					String str=readString(bytes);
					o1.getClass().getField(field.name).set(o1, str);
					revoffset+=4;
					break;
				case LONG:
					long val1=readLong(bytes);
					o1.getClass().getField(field.name).set(o1,val1);
					revoffset+=8;
					break;
				case BOOLEAN:
					boolean bool=readBoolean(bytes);
					o1.getClass().getField(field.name).set(o1, bool);
					revoffset+=1;
					break;
			}
		}
		return o1;
	}
	
	public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
		Schema s = createSchema();
		
		ByteArrayConverter conv = new ByteArrayConverter();
		conv.setSchema(s);
		
		Person p = new Person();
		p.setNullbit(false);
		p.setName("Akshay");
		p.setAge(21);
		p.setAddress("Pune");
		p.setNum(9890712840L);
		
		byte[] data = conv.convert(p);
		
		System.out.print("Generated byte[] is : ");
		for(byte by : data)
		{
			System.out.print(by);
		}
		System.out.println();
		
		Person unknown =new Person();
		unknown = (Person) conv.convertFromBytes(data, unknown);
		
		System.out.println(unknown.getName()+"    "+
				unknown.getAddress()+"  "+
				unknown.getAge()+"  "+
				unknown.getNum()+"  "+
				unknown.getNullbit());
	}
}