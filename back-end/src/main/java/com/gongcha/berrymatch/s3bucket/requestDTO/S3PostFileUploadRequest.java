package com.gongcha.berrymatch.s3bucket.requestDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Getter
@NoArgsConstructor
public class S3PostFileUploadRequest {

    private MultipartFile file;

    @Builder
    public S3PostFileUploadRequest(MultipartFile file) {
        this.file = file;
    }

    public S3PostFileUploadServiceRequest toServiceRequest () {
        return S3PostFileUploadServiceRequest.builder()
                .file(file)
                .build();
    }
}
