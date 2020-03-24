package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class UserServiceTest {


    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        userService = new UserService(userRepository);
    }

    @Test
    public void registerUser() {
        String email = "tester@example.com";
        String name = "Tester";
        String password = "test";

        userService.registerUser(email, name, password);

        verify(userRepository).save(any());
    }

    //TODO 람다식 공부 후에 예외처리 추가
    /*
    @Test
    public void registerUserWithExistedEmail() {
        String email = "tester@example.com";
        String name = "Tester";
        String password = "test";

        Throwable e = assertThrows(EmailExistedException.class, () -> {
            throw new EmailExistedException(email);
        });

        assertEquals("Email is already registered : "+email, e.getMessage());
        userService.registerUser(email, name, password);

        verify(userRepository, never()).save(any());
    }*/
}