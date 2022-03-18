package com.example.imageservice.model;

public class S3Directory {

    PredefinedImageType predefinedImageType;
    String first4Chars;
    String second4Chars;
    String uniqueOriginalFileName;

    public S3Directory(PredefinedImageType predefinedImageType, String uniqueOriginalFileName) {
        this.predefinedImageType = predefinedImageType;
        this.uniqueOriginalFileName = replaceCharInFileName(uniqueOriginalFileName);
        this.first4Chars = subsFirst4Chars(this.uniqueOriginalFileName);
        this.second4Chars = subsSecond4Chars(this.uniqueOriginalFileName);
    }

    private String subsFirst4Chars(String s) {
        int lastIndexOfDot = s.lastIndexOf('.');
        return lastIndexOfDot <= 4 ? s.substring(0, lastIndexOfDot) : s.substring(0, 4);
    }

    private String subsSecond4Chars(String s) {
        int lastIndexOfDot = s.lastIndexOf('.');
        return lastIndexOfDot > 4 && lastIndexOfDot <= 8 ? "" : s.substring(4, 8);
    }

    private String replaceCharInFileName(String fileName) {
        return fileName.replace('/', '_');
    }

    public String getS3Directory() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/").append(predefinedImageType.name());
        stringBuilder.append("/" + first4Chars);
        if (!"".equals(second4Chars)) {
            stringBuilder.append("/" + second4Chars);
        }
        stringBuilder.append("/" + uniqueOriginalFileName);

        return stringBuilder.toString();

    }


}
