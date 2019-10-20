package uk.co.littlestickyleaves.lambda.dto;

public class LinesResult {

    private String bucket;

    private String fileKey;

    private int lines;

    private String result;

    public LinesResult() {
    }

    public LinesResult(String bucket, String fileKey, int lines, String result) {
        this.bucket = bucket;
        this.fileKey = fileKey;
        this.lines = lines;
        this.result = result;
    }

    public LinesResult(LinesInstruction linesInstruction, String result) {
        this(linesInstruction.getBucket(), linesInstruction.getFileKey(), linesInstruction.getLines(),
                result);
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
