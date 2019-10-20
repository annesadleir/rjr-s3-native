package uk.co.littlestickyleaves.lambda;

import com.fasterxml.jackson.jr.ob.JSON;
import org.junit.Before;
import org.junit.Test;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import uk.co.littlestickyleaves.lambda.dto.LinesInstruction;
import uk.co.littlestickyleaves.lambda.dto.LinesResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class S3LineFetcherTest {

    private static final String MULTILINE = "one line\ntwo lines\nthree lines\nfour lines";

    private S3LineFetcher testObject;
    private S3Client mockS3Client = mock(S3Client.class);

    @Before
    public void setUp() throws Exception {
        testObject = new S3LineFetcher(mockS3Client);
    }

    @Test
    public void handleRaw() throws IOException {
//        // arrange
//        String bucket = "bucket";
//        String fileKey = "fileKey";
//        int lines = 2;
//        LinesInstruction linesInstruction = new LinesInstruction(bucket, fileKey, lines);
//        String inputString = JSON.std.asString(linesInstruction);
//        GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucket).key(fileKey).build();
//        InputStream testFile = new ByteArrayInputStream(MULTILINE.getBytes(Charset.defaultCharset()));
//        ResponseInputStream<GetObjectResponse> x =
//        when(mockS3Client.getObject(getObjectRequest)).thenReturn(x);
//
//        // act
//        String result = testObject.handleRaw(inputString);
//
//        // assert
//        LinesResult linesResult = JSON.std.beanFrom(LinesResult.class, result);
//        assertEquals("one line/two lines", linesResult.getResult());


    }

}