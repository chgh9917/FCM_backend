package fcm.fcm.repository;

import fcm.fcm.Entity.SessionStoryge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionStorygeRepository extends JpaRepository<SessionStoryge, Long> {
    Optional<SessionStoryge> findBySessionUUID(String sessionUUID);
}
