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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

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