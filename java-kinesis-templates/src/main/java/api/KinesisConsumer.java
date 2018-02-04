package api;

import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.*;

import java.util.ArrayList;
import java.util.List;

public class KinesisConsumer {

    private String streamName;
    private AmazonKinesis client;

    public KinesisConsumer(String streamName) {
        this.streamName = streamName;
    }

    public void instantiateClient() {
        client = AmazonKinesisClientBuilder.standard().build();
    }

    public String getStreamName() {
        return streamName;
    }

    public List<Shard> getShards() {
        DescribeStreamRequest describeStreamRequest = new DescribeStreamRequest();
        describeStreamRequest.setStreamName(streamName);

        String shardStartId = null;
        List<Shard> shards = new ArrayList<>();

        do {
            describeStreamRequest.setExclusiveStartShardId(shardStartId);
            DescribeStreamResult result = client.describeStream(describeStreamRequest);
            shards.addAll(result.getStreamDescription().getShards());
            if (result.getStreamDescription().getHasMoreShards() && shards.size() > 0) {
                shardStartId = shards.get(shards.size() - 1).getShardId();
            }
            else {
                shardStartId = null;
            }
        } while (shardStartId != null);

        return shards;
    }

    public void readStream(String shardIterator) {
        GetRecordsRequest getRecordsRequest = new GetRecordsRequest();
        getRecordsRequest.setShardIterator(shardIterator);
        getRecordsRequest.setLimit(200);
        GetRecordsResult getRecordsResult = client.getRecords(getRecordsRequest);
        List<Record> records = getRecordsResult.getRecords();

        for(Record record : records) {
            System.out.println("Data: " + new String(record.getData().array()));
            System.out.println("Sequence number: " + record.getSequenceNumber());
            System.out.println();
        }
    }

    public String getShardIterator(String shardID) {
        String shardIterator;
        GetShardIteratorRequest shardIteratorRequest = new GetShardIteratorRequest();
        shardIteratorRequest.setStreamName("test");
        shardIteratorRequest.setShardId(shardID);
        shardIteratorRequest.setShardIteratorType("TRIM_HORIZON");

        GetShardIteratorResult shardIteratorResult = client.getShardIterator(shardIteratorRequest);
        return shardIteratorResult.getShardIterator();
    }
}