package fcm.fcm.repository;

import fcm.fcm.Entity.BoardEntity;
import fcm.fcm.Entity.grade.BoardGrade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    List<BoardEntity> findByBoardGrade(BoardGrade community);
}
