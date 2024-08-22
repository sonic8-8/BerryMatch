package com.gongcha.berrymatch.postFile;

import com.gongcha.berrymatch.postFile.requestDTO.PostFileUploadServiceRequest;
import com.gongcha.berrymatch.postFile.responseDTO.PostFileUploadResponse;
import com.gongcha.berrymatch.s3bucket.S3Service;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostFileService {

    private final S3Service s3Service;

    @Value("${AWS_S3_BUCKET_NAME}")
    private String bucketName;
    @Value("${AWS_S3_BUCKET_REGION}")
    private String region;

    private final PostFileRepository postFileRepository;


    public PostFileUploadResponse savePostFile(PostFileUploadServiceRequest request, String key) {

        MultipartFile file = request.getFile();

        String url = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, key);

        PostFile postFile = PostFile.builder()
                .originalFileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .size(file.getSize())
                .fileKey(key)
                .fileUrl(url)
                .build();

        postFileRepository.save(postFile);

        return PostFileUploadResponse.of(postFile);
    }


}
