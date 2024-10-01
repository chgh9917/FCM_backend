package fcm.fcm.Entity;

import fcm.fcm.Entity.grade.BoardGrade;
import fcm.fcm.Entity.grade.UserGrade;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class BoardEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "BOARD_GRADE")
    private BoardGrade boardGrade;

    private String writer;

    private String title;

    private String content;

    //비밀글 사용 암호: 작성 -> 비밀글 사용, NULL -> 비밀글 미사용
    private String boardPassword;

    @Column(name = "CREATE_AT")
    private LocalDateTime createAt;

    @Column(name = "UPDATE_AT")
    private LocalDateTime updateAt;

    public BoardEntity(BoardEntity post) {
        this.boardGrade = post.getBoardGrade();
        this.writer = post.getWriter();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.boardPassword = post.getBoardPassword();

        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }

    public BoardEntity() {

    }
}
