package fcm.fcm.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CALENDAR")
@Getter @Setter
public class CalendarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String title; // 제목 필드 추가

    @Column(nullable = false)
    private String description; // 설명 필드 추가

    private String email;

    // 이미지 경로들을 리스트로 저장
    @ElementCollection
    @CollectionTable(name = "calendar_images", joinColumns = @JoinColumn(name = "calendar_id"))
    @Column(name = "image_path")
    private List<String> imagePaths = new ArrayList<>();

    @ElementCollection
    @Column(length = 5000)
    private List<String> predictResults;

    // 기본 생성자
    public CalendarEntity() {}

    // 필요한 생성자
    public CalendarEntity(LocalDateTime date, String title, String description, String email, List<String> predictResults) {
        this.date = date;
        this.title = title;
        this.description = description;
        this.email = email;
        this.predictResults = predictResults;
    }

    public CalendarEntity(LocalDateTime date, String title, String description, List<String> imagePaths, String email, List<String> predictResults) {
        this.date = LocalDateTime.now();
        this.title = title;
        this.description = description;
        this.imagePaths = imagePaths;
        this.email = email;
        this.predictResults = predictResults;
    }

    // 이미지 경로 추가 메서드
    public void addImagePath(String imagePath) {
        this.imagePaths.add(imagePath);
    }
}