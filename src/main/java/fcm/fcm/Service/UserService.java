package fcm.fcm.Service;

import fcm.fcm.Entity.UserEntity;
import fcm.fcm.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(UserEntity user) {
        userRepository.save(user);
    }

    public UserEntity findUser(String email) {
        return userRepository.findByEmail(email);
    }
}
