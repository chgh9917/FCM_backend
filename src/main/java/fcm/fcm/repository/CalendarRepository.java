package fcm.fcm.repository;

import fcm.fcm.Entity.CalendarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<CalendarEntity, Long> {

    List<CalendarEntity> findAllByDateBetween(LocalDate startDate, LocalDate endDate);

    List<CalendarEntity> findByDate(LocalDate date);
}