package com.gongcha.berrymatch.s3bucket.requestDTO;

import com.gongcha.berrymatch.s3bucket.S3PostFile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class S3PostFileUploadServiceRequest {

    private MultipartFile file;

    @Builder
    public S3PostFileUploadServiceRequest(MultipartFile file) {
        this.file = file;
    }

    public static S3PostFile toEntity

}
