package com.gongcha.berrymatch.s3bucket;

import com.gongcha.berrymatch.ApiResponse;
import com.gongcha.berrymatch.s3bucket.requestDTO.S3PostFileUploadRequest;
import com.gongcha.berrymatch.s3bucket.responseDTO.S3PostFileUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController("/api")
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/upload")
    public ApiResponse<S3PostFileUploadResponse> upload(S3PostFileUploadRequest request) throws IOException {

        s3Service.isValidateVideo(request.toServiceRequest());

        return ApiResponse.ok(s3Service.uploadVideo(request.toServiceRequest()));
    }

}
