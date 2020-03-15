package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        List<User> users = userRepository.findAll();

        return users;
    }

    public User addUser(String email, String name) {
        User user = User.builder().email(email).name(name).level(1L).build();

        return  userRepository.save(user);
    }

    public User updateUser(Long id, String email, String name, Long level) {
        User user = userRepository.findById(id).orElse(null);
        
        user.setName(name);
        user.setEmail(email);
        user.setLevel(level);

        return user;
    }

    public User deactiveUser(long id) {
        // TODO : 실제로 작업 필요함.
        User user = userRepository.findById(id).orElse(null);

        user.deactive();

        return user;
    }
}
