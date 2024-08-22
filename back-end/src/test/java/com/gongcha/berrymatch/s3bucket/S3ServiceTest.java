package com.gongcha.berrymatch.s3bucket;

import com.gongcha.berrymatch.postFile.requestDTO.PostFileUploadServiceRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import software.amazon.awssdk.services.s3.S3Client;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@SpringBootTest
class S3ServiceTest {

    @MockBean
    private S3Service s3Service;


    @DisplayName("S3 Bucket에 영상을 업로드 할 수 있다.")
    @Test
    void uploadFile() throws IOException {
        // given
        MockMultipartFile file = new MockMultipartFile(
                "video",                      // 파일 이름
                "test.mp4",                   // 원본 파일 이름
                "video/mp4",                  // MIME 타입
                new FileInputStream(new File("C:\\Users\\YOU\\Videos\\test_video.mp4"))                    // 파일 내용
        );

        PostFileUploadServiceRequest request = PostFileUploadServiceRequest.builder()
                .file(file)
                .build();

        BDDMockito.given(s3Service.uploadVideo(request)).willReturn("key");

        // when
        String key = s3Service.uploadVideo(request);

        // then
        assertThat(key).isNotNull()
                .isEqualTo("key");
    }

}