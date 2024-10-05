package fcm.fcm.Service;

import fcm.fcm.Entity.CalendarEntity;
import fcm.fcm.repository.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CalendarService {

    @Autowired
    private CalendarRepository calendarRepository;


    public void saveCalendarEventWithDetails(LocalDateTime eventDate, List<String> imagePaths, String title, String description, String email, List<String> predictResults) {
        // CalendarEntity에 이벤트 날짜, 이미지 경로, 제목, 설명 저장
        CalendarEntity event = new CalendarEntity(eventDate, title, description, email, predictResults);
        event.setImagePaths(imagePaths); // 이미지 경로 설정

        // 저장 로직 (Repository를 사용하여 DB에 저장)
        calendarRepository.save(event);
    }


    // 특정 날짜의 이벤트를 불러오는 메서드
    public List<CalendarEntity> getEventsByDate(LocalDate date) {
        return calendarRepository.findByDate(date); // 날짜별 이벤트 불러오기
    }

    // 모든 이벤트 불러오기
    public List<CalendarEntity> getAllEvents() {
        return calendarRepository.findAll(); // 모든 이벤트 불러오기
    }
}