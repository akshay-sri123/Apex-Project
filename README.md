# Apex-Project

Update 1:
The conversion system is working poperly. The reason we're getting a negative number in the ouput is that the the cost and
impressions are going over their -128 to 127 limit. However, the long value stored in them is stored properly and the reason for
such is that we're printing the byte[] using Arrays.toString(byte[]). If we converted the values into long using Converter.readLong
there is no problem and the output is correct.
