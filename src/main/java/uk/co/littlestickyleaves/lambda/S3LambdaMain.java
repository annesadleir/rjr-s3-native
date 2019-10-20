package uk.co.littlestickyleaves.lambda;

import software.amazon.awssdk.services.s3.S3Client;
import uk.co.littlestickyleaves.aws.lambda.base.LambdaRunner;

public class S3LambdaMain {

    public static void main(String[] args) throws Exception {
        S3Client s3Client = S3Client.create();
        new LambdaRunner(new S3LineFetcher(s3Client)).loop();
    }
}
