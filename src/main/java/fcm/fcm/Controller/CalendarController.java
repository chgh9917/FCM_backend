package fcm.fcm.Controller;

import fcm.fcm.Entity.CalendarEntity;
import fcm.fcm.Service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5173")
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    @PostMapping("/calendar/upload")
    public ResponseEntity<Map<String, Object>> uploadImages(@RequestParam(value = "files", required = false) List<MultipartFile> files,
                                                            @RequestParam("title") String title,
                                                            @RequestParam("description") String description,
                                                            @RequestParam("email") String email) {
        LocalDateTime eventDate = LocalDateTime.now();
        List<String> imagePaths = new ArrayList<>();
        List<String> predictResults = new ArrayList<>();

        Path uploadDir = Paths.get("uploads");

        if (!Files.exists(uploadDir)) {
            try {
                Files.createDirectories(uploadDir);
            } catch (IOException e) {
                return ResponseEntity.status(500).body(Map.of("message", "디렉터리 생성 실패"));
            }
        }

        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    try {
                        String fileName = file.getOriginalFilename();
                        Path path = uploadDir.resolve(fileName);
                        Files.write(path, file.getBytes());
                        imagePaths.add(path.toString());

                        // 예측 결과 받아오기
                        String predictResult = sendImageToFlask(file);
                        predictResults.add(predictResult);

                    } catch (IOException e) {
                        return ResponseEntity.status(500).body(Map.of("message", "파일 저장 실패"));
                    }
                }
            }
        }

        calendarService.saveCalendarEventWithDetails(eventDate, imagePaths, title, description, email, predictResults);

        Map<String, Object> response = Map.of(
                "message", "파일 업로드 및 이벤트 저장 성공",
                "predictResults", predictResults
        );

        return ResponseEntity.ok(response);
    }

    // 특정 날짜의 이벤트를 가져오는 API
    @GetMapping("/calendar/events")
    public ResponseEntity<List<CalendarEntity>> getEvents(@RequestParam("date") String date) {
        LocalDate eventDate = LocalDate.parse(date);
        List<CalendarEntity> events = calendarService.getEventsByDate(eventDate);
        return ResponseEntity.ok(events);
    }

    // 모든 이벤트 가져오는 API
    @GetMapping("/calendar/all-events")
    public ResponseEntity<List<CalendarEntity>> getAllEvents() {
        List<CalendarEntity> events = calendarService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    private String sendImageToFlask(MultipartFile file) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:5000/predict";  // Flask 서버 URL

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 파일을 ByteArrayResource로 변환
        ByteArrayResource fileResource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();  // 파일 이름 설정
            }
        };

        // MultiValueMap에 파일을 추가
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", fileResource);

        // HttpEntity에 파일과 헤더 설정
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Flask 서버로 POST 요청 전송
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        return response.getBody();
    }
}