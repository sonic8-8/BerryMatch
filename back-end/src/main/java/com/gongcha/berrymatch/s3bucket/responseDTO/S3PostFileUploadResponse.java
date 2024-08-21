package com.gongcha.berrymatch.s3bucket.responseDTO;

import com.gongcha.berrymatch.s3bucket.S3PostFile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class S3PostFileUploadResponse {

    private String originalFileName;
    private String storedFileName;
    private String fileType;
    private Long size;
    private String fileKey;
    private String fileUrl;

    @Builder
    public S3PostFileUploadResponse (String originalFileName, String storedFileName, String fileType, Long size, String fileKey, String fileUrl) {
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.fileType = fileType;
        this.size = size;
        this.fileKey = fileKey;
        this.fileUrl = fileUrl;
    }

    public static S3PostFileUploadResponse of(S3PostFile s3PostFile) {
        return S3PostFileUploadResponse.builder()
                .originalFileName(s3PostFile.getOriginalFileName())
                .storedFileName(s3PostFile.getStoredFileName())
                .fileType(s3)
                .build()
    }

}
