package com.gongcha.berrymatch.postFile;

import com.gongcha.berrymatch.post.Post;
import jakarta.persistence.*;

@Entity
public class PostFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Post post;

    private String originalFileName;

    private String storedFileName;

    private String fileType;

    private Long size;

    private String fileKey;

    private String fileUrl;
}
