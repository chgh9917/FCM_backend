package fcm.fcm.Entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
public class SessionStoryge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_uuid", nullable = false, unique = true)
    private String sessionUUID;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    public SessionStoryge() {}

    public SessionStoryge(String sessionUUID, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.sessionUUID = sessionUUID;
        this.createdAt = LocalDateTime.now();
        this.expiresAt = LocalDateTime.now().plus(1, ChronoUnit.MINUTES);
    }

}
