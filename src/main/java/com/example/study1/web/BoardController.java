package com.example.study1.web;

import com.example.study1.domain.Board;
import com.example.study1.domain.User;
import com.example.study1.repository.BoardRepository;
import com.example.study1.support.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.Date;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardRepository boardRepository;

    @GetMapping("/create")
    public String create(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/login";
        }
        return "/board/boardForm";
    }

    @PostMapping("")
    public String create(HttpSession session, Board board) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/login";
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        System.out.println(board.getUser());
        board.setUser(loginUser);
        board.setCreateDate(Date.from(Instant.now()));
        boardRepository.save(board);
        return "redirect:/board/list";

    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("board", boardRepository.findById(id).get());
        return "/board/boardShow";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/login";
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Board board = boardRepository.findById(id).get();
        if (!board.isSameWriter(loginUser)) {
            System.out.println("로그인정보 일치하지않음.");
            return "/users/login";
        }
        model.addAttribute("board", board);
        return "/board/updateForm";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, HttpSession session, Board updatedBoard) {

        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/login";
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Board board = boardRepository.findById(id).get();
        if (!board.isSameWriter(loginUser)) {
            return "/users/login";
        }
        board.update(updatedBoard);
        board.setCreateDate(Date.from(Instant.now()));
        boardRepository.save(board);
        return String.format("redirect:/board/%d", id);
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/login";
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Board board = boardRepository.findById(id).get();
        if (!board.isSameWriter(loginUser)) {
            return "/users/login";
        }
        boardRepository.deleteById(id);
        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("boards", boardRepository.findAll());
        return "/board/boardList";
    }
}
