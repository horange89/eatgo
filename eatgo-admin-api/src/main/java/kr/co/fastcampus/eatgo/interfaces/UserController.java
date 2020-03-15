package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // 1. User List
    // 2. User create -> 회원 가입
    // 3. User update
    // 4. User delete -> level: 0 휴먼 상태
    // (1: customer, 2: restaurant owner, 3: admin)
    @GetMapping("/users")
    public List<User> list() {
        List<User> users = userService.getUsers();

        return users;
    }

    @PostMapping("/users")
    public ResponseEntity<?> create(@RequestBody User resource) throws URISyntaxException {
        String name = resource.getName();
        String email = resource.getEmail();

        User user = userService.addUser(email, name);

        String url = "user/" + user.getId();
        return ResponseEntity.created(new URI(url)).body("{}");
    }

    @PatchMapping("/users/{id}")
    public String update(@PathVariable("id") Long id, @RequestBody User resource) {
        String name = resource.getName();
        String email = resource.getEmail();
        Long level = resource.getLevel();

        userService.updateUser(id, email, name, level);
        return "{}";
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deactiveUser(id);

        return "{}";
    }
}
