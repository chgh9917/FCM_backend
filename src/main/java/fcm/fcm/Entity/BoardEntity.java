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

    @Column(name = "CREATE_AT")
    private LocalDateTime createAt;

    @Column(name = "UPDATE_AT")
    private LocalDateTime updateAt;
}
