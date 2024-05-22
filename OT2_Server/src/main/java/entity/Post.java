package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post implements Serializable {
    @Id
    @Column(name = "post_id")
    private String id;
    @Column(name = "title")
    private String title;
    @Column(name = "contents")
    private String contents;
    @Column(name = "views")
    private int views;
    @Column(name = "likes")
    private int likes;
    @Column(name = "shares")
    private int shares;

    @Embedded
    protected Approval approval;

    @ManyToOne
    @JoinColumn(name = "user_name")
    private User user;

}

