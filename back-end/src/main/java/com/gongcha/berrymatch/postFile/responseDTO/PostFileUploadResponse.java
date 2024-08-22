package com.gongcha.berrymatch.postFile.responseDTO;

import com.gongcha.berrymatch.postFile.PostFile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostFileUploadResponse {

    private String originalFileName;
    private String storedFileName;
    private String fileType;
    private Long size;
    private String fileKey;
    private String fileUrl;

    @Builder
    public PostFileUploadResponse(String originalFileName, String storedFileName, String fileType, Long size, String fileKey, String fileUrl) {
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.fileType = fileType;
        this.size = size;
        this.fileKey = fileKey;
        this.fileUrl = fileUrl;
    }

    public static PostFileUploadResponse of(PostFile postFile) {
        return PostFileUploadResponse.builder()
                .originalFileName(postFile.getOriginalFileName())
                .fileType(postFile.getFileType())
                .size(postFile.getSize())
                .fileKey(postFile.getFileKey())
                .fileUrl(postFile.getFileUrl())
                .build();
    }

}
