package com.gongcha.berrymatch.s3bucket;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class S3ServiceTest {

    @Autowired
    private S3Service s3Service;

    @DisplayName("S3 Bucket에 영상을 업로드 할 수 있다.")
    @Test
    void uploadFile() throws IOException {
        // given
        byte[] videoBytes = new byte[] {
                0x00, 0x00, 0x00, 0x18, 0x66, 0x74, 0x79, 0x70, // MP4 파일 헤더 일부
                0x6D, 0x70, 0x34, 0x32, 0x00, 0x00, 0x00, 0x00,
                0x6D, 0x70, 0x34, 0x31
        };

        MultipartFile file = new MockMultipartFile(
                "video",
                "testvideo.mp4",
                "video/mp4",
                videoBytes
        );

        // when
        String fileName = s3Service.uploadVideo(file);

        // then
        assertThat(fileName).isNotNull();
    }

}