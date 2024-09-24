package fcm.fcm.Dto;

import java.time.LocalDate;

public class CalendarDto {

    private LocalDate date;
    private String imagePath;  // 이미지 경로를 저장

    public CalendarDto(LocalDate date, String imagePath) {
        this.date = date;
        this.imagePath = imagePath;
    }

    // getters and setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}