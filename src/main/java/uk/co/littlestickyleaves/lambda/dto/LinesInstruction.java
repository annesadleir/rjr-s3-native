package uk.co.littlestickyleaves.lambda.dto;

public class LinesInstruction {

    private String bucket;

    private String fileKey;

    private int lines;

    public LinesInstruction() {
    }

    public LinesInstruction(String bucket, String fileKey, int lines) {
        this.bucket = bucket;
        this.fileKey = fileKey;
        this.lines = lines;
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
}
