package fcm.fcm.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "calendar")
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

    // 기본 생성자
    public CalendarEntity() {}

    // 필요한 생성자
    public CalendarEntity(LocalDateTime date, String title, String description) {
        this.date = date;
        this.title = title;
        this.description = description;
    }

    // Getter, Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    // 이미지 경로 추가 메서드
    public void addImagePath(String imagePath) {
        this.imagePaths.add(imagePath);
    }
}