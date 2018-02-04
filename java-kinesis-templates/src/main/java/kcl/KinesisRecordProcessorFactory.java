package kcl;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessorFactory;

public class KinesisRecordProcessorFactory implements IRecordProcessorFactory {
    public IRecordProcessor createProcessor() {
        return new KinesisRecordProcessor();
    }
}