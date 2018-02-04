package api;

import java.util.ArrayList;
import java.util.List;

public class KinesisProducerSample {

    public static void main(String[] args) {
        List<String> records = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            records.add("test-" + i);
        }

        KinesisProducer producer = new KinesisProducer("test");
        producer.writeToKinesis(records);
    }
}
