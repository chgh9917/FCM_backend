package fcm.fcm.Service;

import fcm.fcm.Entity.CalendarEntity;
import fcm.fcm.repository.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CalendarService {

    @Autowired
    private CalendarRepository calendarRepository;

    // 새 날짜와 이미지를 저장하는 메서드
    public CalendarEntity saveCalendarEvent(LocalDate date, List<String> imagePaths) {
        CalendarEntity calendarEntity = new CalendarEntity(date);
        calendarEntity.setImagePaths(imagePaths); // 이미지 경로 추가
        return calendarRepository.save(calendarEntity); // 저장
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