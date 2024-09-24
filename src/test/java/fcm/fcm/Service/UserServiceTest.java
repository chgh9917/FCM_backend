package fcm.fcm.Service;

import fcm.fcm.Entity.UserEntity;
import fcm.fcm.repository.UserRepository;
import org.junit.jupiter.api.Test;

class UserServiceTest {

    private final UserRepository userRepository;

    UserServiceTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    void join() {
        UserEntity user = new UserEntity();
        user.setEmail("test@test.com");
        user.setPassword("123456");
        user.setAge(10);
        user.setName("test");
        user.setHeight(187);
        user.setWeight(90);

        userRepository.save(user);
    }

    @Test
    void findUser() {
    }
}