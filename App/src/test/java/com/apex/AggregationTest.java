package com.apex;


import Aggregator.Aggregator;
import Aggregator.AggregatorById;
import Aggregator.AggregatorSet;
import Aggregator.Reader;
import com.opencsv.CSVReader;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import java.io.FileReader;
import java.io.IOException;

public class AggregationTest {

    @Test

    public void  checkValueofagregation() throws IOException {


        AggregatorSet aggregatorSet = new AggregatorSet();

        for (int i = 1; i <= 7; i++) {
            AggregatorById aggregator = new AggregatorById(i);
            aggregatorSet.addAggregator(aggregator);
        }

        CSVReader csvReader = new CSVReader(new FileReader("/home/vishal/Documents/modinputfile.csv"), ',');
        Reader read=new Reader();

        try {
            read.reader(csvReader,aggregatorSet);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        for(Aggregator aggr : aggregatorSet.getAggregatorList())
        {
            aggr.dump();
        }

    }



}
