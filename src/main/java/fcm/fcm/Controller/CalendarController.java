package fcm.fcm.Controller;

import fcm.fcm.Entity.CalendarEntity;
import fcm.fcm.Service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/calendar")
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    // 이미지 업로드 및 날짜에 대한 이벤트 저장 API
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImages(@RequestParam("date") String date,
                                               @RequestParam("files") List<MultipartFile> files) {
        LocalDate eventDate = LocalDate.parse(date); // 문자열을 LocalDate로 변환
        List<String> imagePaths = new ArrayList<>();

        // 파일 저장 로직
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    String fileName = file.getOriginalFilename();
                    Path path = Paths.get("uploads/" + fileName); // 파일 저장 경로 설정
                    Files.write(path, file.getBytes()); // 파일 저장

                    imagePaths.add(path.toString()); // 저장된 파일 경로를 리스트에 추가
                } catch (IOException e) {
                    return ResponseEntity.status(500).body("파일 저장 실패");
                }
            }
        }

        // 데이터베이스에 날짜 및 이미지 경로 저장
        calendarService.saveCalendarEvent(eventDate, imagePaths);

        return ResponseEntity.ok("파일 업로드 및 이벤트 저장 성공");
    }

    // 특정 날짜의 이벤트를 가져오는 API
    @GetMapping("/events")
    public ResponseEntity<List<CalendarEntity>> getEvents(@RequestParam("date") String date) {
        LocalDate eventDate = LocalDate.parse(date);
        List<CalendarEntity> events = calendarService.getEventsByDate(eventDate);
        return ResponseEntity.ok(events);
    }

    // 모든 이벤트 가져오는 API
    @GetMapping("/all-events")
    public ResponseEntity<List<CalendarEntity>> getAllEvents() {
        List<CalendarEntity> events = calendarService.getAllEvents();
        return ResponseEntity.ok(events);
    }
}