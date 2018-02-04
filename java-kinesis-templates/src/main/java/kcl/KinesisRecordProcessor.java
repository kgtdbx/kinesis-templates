package kcl;

import com.amazonaws.services.kinesis.clientlibrary.exceptions.InvalidStateException;
import com.amazonaws.services.kinesis.clientlibrary.exceptions.ShutdownException;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorCheckpointer;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.types.InitializationInput;
import com.amazonaws.services.kinesis.clientlibrary.types.ProcessRecordsInput;
import com.amazonaws.services.kinesis.clientlibrary.types.ShutdownInput;
import com.amazonaws.services.kinesis.model.Record;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class KinesisRecordProcessor implements IRecordProcessor {

    private static final Log LOG = LogFactory.getLog(KinesisRecordProcessor.class);
    private String kinesisShardID;

    public void initialize(InitializationInput initializationInput) {
        LOG.info("Initializing record processor");
        this.kinesisShardID = initializationInput.getShardId();
        LOG.info("Record processor initialized for shard " + this.kinesisShardID);
    }

    public void processRecords(ProcessRecordsInput processRecordsInput) {
        List<Record> records = processRecordsInput.getRecords();
        LOG.info("Processing " + records.size() + " records");
        for (Record record : records) {
            processRecord(record);
        }

        checkpoint(processRecordsInput.getCheckpointer());
    }

    private void processRecord(Record record) {
        //Replace with actual logic
        System.out.println("Processed record with sequence number: " + record.getSequenceNumber() + ". Value: " +
        new String(record.getData().array()));
    }

    public void shutdown(ShutdownInput shutdownInput) {
        LOG.info("Shutting down processor for shard " + kinesisShardID);
        checkpoint(shutdownInput.getCheckpointer());
    }

    private void checkpoint(IRecordProcessorCheckpointer checkpointer) {
        LOG.info("Checkpointing shard " + kinesisShardID);
        try {
            checkpointer.checkpoint();
        }
        catch (InvalidStateException ex) {
            LOG.error("Checkpoint failed.", ex);
        }
        catch (ShutdownException ex) {
            LOG.error("Checkpoint failed due to shutdown exception.", ex);
        }
    }
}
