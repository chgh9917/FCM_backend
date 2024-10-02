package fcm.fcm.Entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "COMMENT")
public class CommentEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Long BoardId;

    private String writer;

    private int level;

    public CommentEntity() {}

    public CommentEntity(String content, Long BoardId, String writer, int level) {
        this.content = content;
        this.BoardId = BoardId;
        this.writer = writer;
        this.level = level;
    }
}
