package com.gongcha.berrymatch.postFile;

import com.gongcha.berrymatch.post.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post_file")
public class PostFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String originalFileName;

    private String fileType;

    private Long size;

    private String fileKey;

    private String fileUrl;

    private String thumbFileUrl;

    @Builder
    public PostFile(String originalFileName, String thumbFileUrl, String fileType, Long size, String fileKey, String fileUrl, Post post) {
        this.post = post;
        this.originalFileName = originalFileName;
        this.fileType = fileType;
        this.size = size;
        this.fileKey = fileKey;
        this.fileUrl = fileUrl;
        this.thumbFileUrl = thumbFileUrl;
    }
}
