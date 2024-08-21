package com.gongcha.berrymatch.s3bucket;

import com.gongcha.berrymatch.s3bucket.requestDTO.S3PostFileUploadServiceRequest;
import com.gongcha.berrymatch.s3bucket.responseDTO.S3PostFileUploadResponse;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${AWS_S3_BUCKET_NAME}")
    private String bucketName;
    private final S3Client s3Client;
    private final S3Template s3Template;

    /**
     * S3 버킷에 영상을 업로드해주는 메서드
     */
    public S3PostFileUploadResponse uploadVideo(S3PostFileUploadServiceRequest request) throws IOException {


        MultipartFile file = request.getFile();

        String directory = "highlight_video/";
        String storedFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String key = directory + storedFilename;

        s3Template.upload(bucketName, key, file.getInputStream());

        S3PostFileUploadResponse.of()


        return S3PostFileUploadResponse.of()

    }

    /**
     * 영상 파일 크기와 타입을 검증해주는 메서드
     */
    private static boolean isValidateVideo(S3PostFileUploadServiceRequest request) {
        MultipartFile file = request.getFile();

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("Video/")) {
            throw new IllegalArgumentException("영상 파일만 업로드할 수 있습니다.");
        }

        Long maxFileSize = 500 * 1024 * 1024L;
        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException("영상 파일 크기는 500MB를 초과할 수 없습니다.");
        }

        return true;
    }

}

