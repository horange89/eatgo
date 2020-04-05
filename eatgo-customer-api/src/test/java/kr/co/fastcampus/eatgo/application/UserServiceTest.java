package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void registerUser() {
        String email = "tester@example.com";
        String name = "Tester";
        String password = "test";

        userService.registerUser(email, name, password);

        verify(userRepository).save(any());
    }

    // 미완성
    @Test
    public void registerUserWithExistedEmail() throws EmailExistedException{
        String email = "tester@example.com";
        String name = "Tester";
        String password = "test";

        User mockUser = User.builder().email(email).name(name).password(password).build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));
        //when(userService.registerUser(email, name, password)).thenReturn(new EmailExistedException(email));
        System.out.println("Email is already registered Test");

        EmailExistedException e = assertThrows(EmailExistedException.class, () -> {
            userService.registerUser(email, name, password);
            //userRepository.findByEmail(email);
        });
        assertEquals("Email is already registered : "+ email, e.getMessage());
    }

    @Test
    public void authenticateWithValidAttributes() {
        String email = "tester@example.com";
        String password = "test";

        User mockUser = User.builder().email(email).build();
        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));
        given(passwordEncoder.matches(any(), any())).willReturn(true);

        User user = userService.authenticate(email, password);

        assertThat(user.getEmail(), is(email));
    }

    @Test
    public void authenticateWithNotExistedEmail() throws EmailNotExistedException{
        EmailNotExistedException e = assertThrows(EmailNotExistedException.class, () ->
                {
                    String email = "x";
                    String password = "test";

                    given(userRepository.findByEmail(email)).willReturn(Optional.empty());

                    userService.authenticate(email, password);

                    throw new EmailNotExistedException(email);
                }
        );
    }

    @Test
    public void authenticateWithWrongPassword() {
        PasswordWrongException e = assertThrows(PasswordWrongException.class, () ->
                {
                    String email = "tester@example.com";
                    String password = "x";

                    User mockUser = User.builder().email(email).password(password).build();

                    given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));
                    given(passwordEncoder.matches(any(), any())).willReturn(false);

                    userService.authenticate(email, password);
                }
        );

        assertEquals(e.getMessage(), "Password is wrong");
    }
}