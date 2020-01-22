package com.example.study1.web;

import com.example.study1.domain.Board;
import com.example.study1.domain.User;
import com.example.study1.repository.BoardRepository;
import com.example.study1.support.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String create(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/login";
        }
        return "board/boardForm";
    }

    @PostMapping("")
    public String create(HttpSession session, Board board) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/login";
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        board.setUser(loginUser);
        board.setCreateDate(Date.from(Instant.now()));
        boardRepository.save(board);
        return "redirect:/board/list";

    }
    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model){
        Board board = boardRepository.findById(id).get();
        model.addAttribute("board", board);
        return "board/boardShow";
    }

    @GetMapping("list")
    public String list(Model model) {
        model.addAttribute("boards", boardRepository.findAll());
        return "board/boardList";
    }
}
