# AWS Kinesis Templates

Code templates to use when writing applications that read from and write to Amazon Kinesis. Contains implementations of the KCL, as well as examples that demonstrate standard Kinesis API usage (though Amazon doesn't recommend using the API).

## Getting Started

Create a Kinesis stream using the AWS CLI.
```
aws kinesis create-stream --stream-name test --shard-count 1
```

The IAM user must have read/write permission to Kinesis. The KCL uses DynamoDB for checkpointing, so the user must additionally have read/write permission there. 

## Java Templates

Two sets of templates are provided:
- API templates (producer and consumer)
- KCL template (consumer)

Amazon [recommends](https://docs.aws.amazon.com/streams/latest/dev/developing-consumers-with-sdk.html) using the KCL to read from Kinesis, but the API examples can be useful for basic testing and validation.

## Additional Documentation
- [KCL implementation documentation](https://docs.aws.amazon.com/streams/latest/dev/kinesis-record-processor-implementation-app-java.html)
- [AWS implementation example](https://github.com/awslabs/amazon-kinesis-client)