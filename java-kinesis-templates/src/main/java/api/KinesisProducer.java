package api;

import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.PutRecordsRequest;
import com.amazonaws.services.kinesis.model.PutRecordsRequestEntry;
import com.amazonaws.services.kinesis.model.PutRecordsResult;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class KinesisProducer {
    private String streamName;
    private AmazonKinesis client;

    public KinesisProducer(String streamName) {
        this.streamName = streamName;
        client = AmazonKinesisClientBuilder.standard().build();
    }

    public void writeToKinesis(List<String> records) {
        List<PutRecordsRequestEntry> putRecordRequestList = new ArrayList<>();

        for (int i = 0; i < records.size(); i++) {
            String record = records.get(i);
            PutRecordsRequestEntry entry = new PutRecordsRequestEntry();
            entry.setData(ByteBuffer.wrap(record.toString().getBytes()));
            entry.setPartitionKey("partition-key-" + i);
            putRecordRequestList.add(entry);
        }

        PutRecordsRequest putRecordsRequest = new PutRecordsRequest();
        putRecordsRequest.setStreamName(streamName);
        putRecordsRequest.setRecords(putRecordRequestList);
        PutRecordsResult putRecordsResult = client.putRecords(putRecordsRequest);
    }
}
