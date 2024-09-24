package fcm.fcm.repository;

import fcm.fcm.Entity.CalendarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<CalendarEntity, Long> {

    // 특정 날짜에 해당하는 데이터를 찾는 메서드
    Optional<CalendarEntity> findByDate(LocalDate date);

    // 특정 기간(월)의 데이터를 모두 찾는 메서드
    List<CalendarEntity> findAllByDateBetween(LocalDate startDate, LocalDate endDate);
}