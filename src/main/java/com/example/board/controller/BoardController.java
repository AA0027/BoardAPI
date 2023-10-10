package com.example.board.controller;

import com.example.board.dao.BoardDao;
import com.example.board.dao.Member;
import com.example.board.service.BoardService;
import com.example.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/myBoard")
public class BoardController {
    private final BoardService service;
    private final UserService userService;


    @GetMapping("/welcome")
    public String welcome()
    {
        return "welcome";
    }


    //계시물 리스트를 보여준다
    @GetMapping("/boardList")
    public String showList(Model model, @RequestParam(value="page", defaultValue="0") int page
                            ,@AuthenticationPrincipal User user)
    {
        Page<BoardDao> list = service.getList(page);
        int totalCount = service.getTotalCount();
        int pageCount = totalCount / 10; // 1
        if(totalCount % 10 > 0){ // 나머지가 있을 경우 1page를 추가
            pageCount++;
        }
        int currentPage = page;
        model.addAttribute("list",list);
        model.addAttribute("loginInfo",user);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("currentPage", currentPage);
        return "list";
    }


    @GetMapping("/boardList/board")
    public String writeBoard(){return "writeForm";}
    //계시물 작성하기
    @PostMapping("/boardList/board")
    public String writeBoard(@AuthenticationPrincipal User user, @RequestParam String title, @RequestParam String content)
    {

        String email = user.getUsername();
        Member member = userService.getMember(email);
        BoardDao b = service.save(member, title, content);
        return "redirect:/myBoard/boardList";
    }
    //계시물 상세보기
    @GetMapping("/boardList/detail")
    public String detailBoard(@RequestParam(value="id") long id, Model model)
    {
        BoardDao board = service.getBoard(id);
        int count = board.getViewCnt()+ 1;
        board.setViewCnt(count);
        service.update(board);
        model.addAttribute("board",board);
        return "board";
    }
    //계시물 수정
    @GetMapping("/boardList/board/update")
    public String updateForm(@RequestParam(value="id") long boardId, Model model)
    {
        BoardDao board = service.getBoard(boardId);
        model.addAttribute("board", board);
        return "updateForm";
    }
    @PostMapping("/boardList/board/update")
    public String updateForm(@RequestParam(value="id") long id, @RequestParam String title, @RequestParam String content)
    {
        service.update(id, title, content);
        return "redirect:/myBoard/boardList";
    }

    //게시물 삮제
    @GetMapping("/boardList/board/delete")
    public String deleteBoard(@RequestParam long id)
    {
        service.delete(id);
        return "redirect:/myBoard/boardList";
    }
}
