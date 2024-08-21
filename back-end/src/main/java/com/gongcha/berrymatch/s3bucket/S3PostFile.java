package com.gongcha.berrymatch.s3bucket;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class S3PostFile {

    private String originalFileName;
    private String storedFileName;
    private String fileType;
    private Long size;
    private String fileKey;
    private String fileUrl;

    @Builder
    public S3PostFile(String originalFileName, String storedFileName, String fileType, Long size, String fileKey, String fileUrl) {
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.fileType = fileType;
        this.size = size;
        this.fileKey = fileKey;
        this.fileUrl = fileUrl;
    }

}
