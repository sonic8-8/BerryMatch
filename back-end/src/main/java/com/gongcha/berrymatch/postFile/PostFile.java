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

    private String name;

    private String url;

    @Enumerated(EnumType.STRING)
    private PostFileType postFileType;

    private int size;
}
