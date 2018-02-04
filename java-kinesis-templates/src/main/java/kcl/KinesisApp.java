package kcl;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessorFactory;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import conf.ConfigurationUtility;
import org.apache.commons.configuration2.Configuration;

import java.util.UUID;

public class KinesisApp {

    private static AWSCredentialsProvider credentialsProvider;

    public static void main(String[] args) {
        //Get configuration properties
        Configuration conf  = ConfigurationUtility.getConfiguration("src/main/resources/config.properties");
        String applicationName = conf.getString("kinesis.application_name");
        String kinesisStreamName = conf.getString("kinesis.stream_name");

        //Setup worker
        String workerID = UUID.randomUUID().toString();
        initializeCredentials();
        credentialsProvider.getCredentials();
        KinesisClientLibConfiguration kinesisConf = new KinesisClientLibConfiguration(applicationName, kinesisStreamName,
                credentialsProvider, workerID);

        //Set initial position in stream to the oldest available record
        kinesisConf.withInitialPositionInStream(InitialPositionInStream.TRIM_HORIZON);

        //Create new worker and run
        IRecordProcessorFactory factory = new KinesisRecordProcessorFactory();
        Worker worker = new Worker.Builder().config(kinesisConf)
                .recordProcessorFactory(factory)
                .build();

        worker.run();
    }

    private static void initializeCredentials() {
        credentialsProvider =  new ProfileCredentialsProvider();
        //TODO: add exception handling
        credentialsProvider.getCredentials();
    }
}