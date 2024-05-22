package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment implements Serializable {
    @Column(name = "contents")
    private String contents;
    @Id
    @Column(name = "comment_date")
    private LocalDateTime commentDate;
    @Column(name = "likes")
    private int likes;

    @Id
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_name")
    private User user;
}
