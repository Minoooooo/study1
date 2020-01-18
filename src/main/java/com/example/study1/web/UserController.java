package com.example.study1.web;


import com.example.study1.domain.User;
import com.example.study1.repository.UserRepository;
import com.example.study1.support.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/users")
public class UserController {
//    private List<User> users = new ArrayList<User>();

    @Autowired
    UserRepository userRepository;

    @GetMapping("/create")
    public String create() {
        return "form";
    }

    @PostMapping("/create")
    public String create(User user) {
        System.out.println("user : " + user);
//        users.add(user);                             //리스트에 유저 정보 저장
        userRepository.save(user);                   // api 를 통해 유저 정보 저장 (JPA, H2에 저장)
        return "redirect:/users/list";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @PostMapping("login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            System.out.println("Login Failure!");
            return "redirect:/users/login";
        }
        if (!user.matchPassword(password)) {
            System.out.println("Login Failure!");
            return "redirect:/users/login";
        }
        System.out.println("Login Success!");
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);

        return "redirect:/";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }

    @GetMapping("/list")
    public String list(Model model) {
//        model.addAttribute("users", users); // 리스트에있는걸 템플릿으로 보내줌  그후 템플릿 -> 클라이언트
        model.addAttribute("users", userRepository.findAll());
        //     == 위와 동일한것을 리스트 말고 api를통해서함
        return "list";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) throws Exception {
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        if (!loginUser.matchId(id)) {
            throw new Exception("자신의 정보가 아닙니다");
        }
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        return "updateForm";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, User updatedUser, HttpSession session) throws Exception {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (!loginUser.matchId(id)) {
            throw new Exception("자신의 정보가 아닙니다");
        }
        User user = userRepository.findById(id).get();
        user.update(updatedUser);
        userRepository.save(user);
        return "redirect:/users/list";
    }

}

