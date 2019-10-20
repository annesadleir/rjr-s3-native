package uk.co.littlestickyleaves.lambda;

import software.amazon.awssdk.services.s3.S3Client;

/**
 * A path for the tracing agent to use to produce configuration files
 * https://medium.com/graalvm/introducing-the-tracing-agent-simplifying-graalvm-native-image-configuration-c3b56c486271
 *
 * sample command:
 * $JAVA_HOME/bin/java -agentlib:native-image-agent=config-output-dir=src/main/resources/META-INF/native-image
 * -cp target/rjr-s3-native-1.0.jar uk.co.littlestickyleaves.lambda.TracingAgentPath
 */
public class TracingAgentPath {

    public static void main(String[] args) throws Exception {
        S3Client s3Client = S3Client.create();
        String input = "{\"bucket\":\"papirtape\",\"fileKey\":\"iwonder.txt\",\"lines\":2}";
        String result = new S3LineFetcher(s3Client).handleRaw(input);
        System.out.println(result);
    }

}
