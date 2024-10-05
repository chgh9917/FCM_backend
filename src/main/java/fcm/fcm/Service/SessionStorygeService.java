package fcm.fcm.Service;

import fcm.fcm.Entity.SessionStoryge;
import fcm.fcm.repository.SessionStorygeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class SessionStorygeService {

    private final SessionStorygeRepository sessionStorygeRepository;

    @Autowired
    public SessionStorygeService(SessionStorygeRepository sessionStorygeRepository) {
        this.sessionStorygeRepository = sessionStorygeRepository;
    }

    // 새로운 세션 생성
    public SessionStoryge createSession() {
        String sessionUUID = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusHours(1);  // 1시간 후 만료

        SessionStoryge session = new SessionStoryge(sessionUUID, now, expiresAt);
        return sessionStorygeRepository.save(session);
    }

    // 세션 UUID로 세션 조회
    public Optional<SessionStoryge> getSessionByUUID(String sessionUUID) {
        return sessionStorygeRepository.findBySessionUUID(sessionUUID);
    }

    // 세션 삭제
    public void deleteSession(String sessionUUID) {
        sessionStorygeRepository.findBySessionUUID(sessionUUID).ifPresent(sessionStorygeRepository::delete);
    }
}
