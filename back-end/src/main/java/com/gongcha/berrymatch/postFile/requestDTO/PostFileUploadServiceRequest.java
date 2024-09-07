package com.gongcha.berrymatch.postFile.requestDTO;

import com.gongcha.berrymatch.postFile.PostFile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class PostFileUploadServiceRequest {

    private MultipartFile file;

    @Builder
    public PostFileUploadServiceRequest(MultipartFile file) {
        this.file = file;
    }

}
