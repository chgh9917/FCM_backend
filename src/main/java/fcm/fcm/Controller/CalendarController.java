package fcm.fcm.Controller;

import fcm.fcm.Dto.CalendarDto;
import fcm.fcm.Entity.CalendarEntity;
import fcm.fcm.repository.CalendarRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/calendars")
public class CalendarController {

    private final CalendarRepository calendarRepository;

    public CalendarController(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    @GetMapping("/month")
    public ResponseEntity<List<CalendarDto>> getCalendarsByMonth(@RequestParam("year") int year, @RequestParam("month") int month) {
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

        // 특정 기간에 해당하는 Calendar 데이터를 가져옵니다.
        List<CalendarEntity> calendars = calendarRepository.findAllByDateBetween(startOfMonth, endOfMonth);

        // Calendar 데이터를 DTO로 변환하여 이미지 경로를 분리
        List<CalendarDto> calendarDtos = calendars.stream()
                .flatMap(calendar -> calendar.getImagePaths().stream()
                        .map(imagePath -> new CalendarDto(calendar.getDate(), imagePath)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(calendarDtos);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPhoto(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                              @RequestParam("file") MultipartFile file) throws IOException {
        String userHome = System.getProperty("user.home");
        String uploadDir = userHome + "/uploads/";
        String fileName = date.toString() + "-" + java.util.UUID.randomUUID().toString() + ".jpg";

        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File targetFile = new File(uploadDir + fileName);
        file.transferTo(targetFile);

        // 해당 날짜의 CalendarEntity 객체가 이미 존재하는지 확인하고, 없으면 새로 생성
        CalendarEntity calendar = calendarRepository.findByDate(date)
                .orElse(new CalendarEntity(date));

        // 이미지 경로 추가
        calendar.addImagePath(uploadDir + fileName);

        calendarRepository.save(calendar);

        return ResponseEntity.ok("사진이 성공적으로 업로드되었습니다.");
    }
}