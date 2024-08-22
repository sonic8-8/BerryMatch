package com.gongcha.berrymatch.postFile;

import com.gongcha.berrymatch.ApiResponse;
import com.gongcha.berrymatch.postFile.requestDTO.PostFileUploadRequest;
import com.gongcha.berrymatch.postFile.responseDTO.PostFileUploadResponse;
import com.gongcha.berrymatch.s3bucket.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController("/api/s3")
public class PostFileController {

    private final S3Service s3Service;
    private final PostFileService postFileService;

    // URL은 테스트용으로 넣어놨습니다.
    @PostMapping("/api/s3/upload")
    public ApiResponse<PostFileUploadResponse> videoUpload(@RequestParam("file") MultipartFile file) throws IOException {

        PostFileUploadRequest request = PostFileUploadRequest.of(file);

        String key = s3Service.uploadVideo(request.toServiceRequest());

        return ApiResponse.ok(postFileService.savePostFile(request.toServiceRequest(), key));
    }


}
