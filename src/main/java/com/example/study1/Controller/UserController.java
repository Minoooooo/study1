package com.example.study1.Controller;


import com.example.study1.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/users")
public class UserController {
    @GetMapping("/create")
    public String create(){
        return "form";
    }
    @PostMapping("/create")
    public String create(User user){
        System.out.println("user : " + user);
        return "index";
    }
//    HashMap<Long, User> users = new HashMap<Long, User>();
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @PostMapping("/join")
//    public ResponseEntity join(@RequestBody UserCreateRequest usercreaterequest) {
//
//        System.out.println("11");
//        User user = toUser(usercreaterequest);
//        userRepository.save(user);
//        return ResponseEntity.ok(user);
//
//    }
//
//    @GetMapping("/{id}")
//    public User getUser(@PathVariable Long id) {
//
//        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
//    }
//
//    private User toUser(UserCreateRequest usercreaterequest) {
//        return new User(usercreaterequest.getName());
//    }
//
//
}

