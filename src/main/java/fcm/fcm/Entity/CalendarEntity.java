package fcm.fcm.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "calendar")
public class CalendarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    // 이미지 경로들을 리스트로 저장
    @ElementCollection
    @CollectionTable(name = "calendar_images", joinColumns = @JoinColumn(name = "calendar_id"))
    @Column(name = "image_path")
    private List<String> imagePaths = new ArrayList<>();

    // 생성자, getter, setter
    public CalendarEntity() {
    }

    public CalendarEntity(LocalDate date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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