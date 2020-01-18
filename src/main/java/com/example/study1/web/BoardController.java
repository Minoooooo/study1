package com.example.study1.web;

import com.example.study1.domain.Board;
import com.example.study1.domain.User;
import com.example.study1.repository.BoardRepository;
import com.example.study1.repository.UserRepository;
import com.example.study1.support.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.Date;

@Controller
@RequestMapping("board")
public class BoardController {

    @Autowired
    BoardRepository boardRepository;

    @GetMapping("create")
    public String create() {
        return "boardForm";
    }

    @PostMapping("create")
    public String create(HttpSession session, Board board) {
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/user/login";
        }
        board.setUser(loginUser);
        board.setCreateDate(Date.from(Instant.now()));
        boardRepository.save(board);
        return "redirect:/board/list";

    }

    @GetMapping("list")
    public String list(Model model){
        model.addAttribute("boards",boardRepository.findAll());
        return "boardList";
    }
}
