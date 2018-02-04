package api;

import com.amazonaws.services.kinesis.model.Shard;

import java.util.List;

public class KinesisConsumerSample {
    public static void main(String[] args) {
        KinesisConsumer consumer = new KinesisConsumer("test");
        consumer.instantiateClient();
        List<Shard> shards = consumer.getShards();

        for(Shard shard : shards) {
            consumer.readStream(consumer.getShardIterator(shard.getShardId()));
        }
    }
}