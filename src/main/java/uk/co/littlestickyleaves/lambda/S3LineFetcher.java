package uk.co.littlestickyleaves.lambda;

import com.fasterxml.jackson.jr.ob.JSON;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import uk.co.littlestickyleaves.aws.lambda.base.LambdaWorker;
import uk.co.littlestickyleaves.lambda.dto.LinesInstruction;
import uk.co.littlestickyleaves.lambda.dto.LinesResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.stream.Collectors;

public class S3LineFetcher implements LambdaWorker {

    private final S3Client s3Client;

    public S3LineFetcher(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String handleRaw(String input) {
        try {
            LinesInstruction linesInstruction = JSON.std.beanFrom(LinesInstruction.class, input);
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(linesInstruction.getBucket())
                    .key(linesInstruction.getFileKey())
                    .build();
            try (BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(s3Client.getObject(getObjectRequest)))) {
                String readLines = bufferedReader.lines()
                        .limit(linesInstruction.getLines())
                        .collect(Collectors.joining(" / "));
                LinesResult linesResult = new LinesResult(linesInstruction, readLines);
                return JSON.std.asString(linesResult);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
