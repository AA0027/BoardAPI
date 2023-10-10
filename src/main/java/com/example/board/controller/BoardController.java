package com.example.board.controller;

import com.example.board.dao.Answer;
import com.example.board.dao.BoardDao;
import com.example.board.dao.Member;
import com.example.board.service.AnswerService;
import com.example.board.service.BoardService;
import com.example.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@SessionAttributes("boardId")
@Controller
@RequiredArgsConstructor
@RequestMapping("/myBoard")
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;
    private final AnswerService answerService;



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
        Page<BoardDao> list = boardService.getList(page);
        int totalCount = boardService.getTotalCount();
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
        boardService.save(member, title, content);
        return "redirect:/myBoard/boardList";
    }
    //계시물 상세보기
    @GetMapping("/boardList/detail")
    public String detailBoard(@RequestParam(value="id") long id, Model model)
    {
        BoardDao board = boardService.getBoard(id);
        int count = board.getViewCnt()+ 1;
        board.setViewCnt(count);
        boardService.update(board);
        //답변 기능추가
        List<Answer> answers = answerService.allAnswer(id);

        model.addAttribute("answers",answers);
        model.addAttribute("board",board);
        model.addAttribute("boardId",id);
        return "board";
    }
    //계시물 수정
    @GetMapping("/boardList/board/update")
    public String updateForm(@RequestParam(value="id") long boardId, Model model)
    {
        BoardDao board = boardService.getBoard(boardId);
        model.addAttribute("board", board);
        return "updateForm";
    }
    @PostMapping("/boardList/board/update")
    public String updateForm(@RequestParam(value="id") long id, @RequestParam String title, @RequestParam String content)
    {
        boardService.update(id, title, content);
        return "redirect:/myBoard/boardList";
    }

    //게시물 삮제
    @GetMapping("/boardList/board/delete")
    public String deleteBoard(@RequestParam long id)
    {
        boardService.delete(id);
        return "redirect:/myBoard/boardList";
    }
    //---------------답변 관련---------------------
    //---------------답변 관련---------------------
    //---------------답변 관련---------------------



    //답변 작성 Form
    @GetMapping("/boardList/detail/answer/create")
    public String registerAnswer(@ModelAttribute("boardId") long boardId, Model model)
    {
        model.addAttribute("id",boardId);
        return "answerForm";
    }
    //답변 작성
    @PostMapping("/boardList/detail/answer/create")
    public String registerAnswer(@RequestParam String reply, @AuthenticationPrincipal User user
                        ,@ModelAttribute("boardId") long boardId)
    {
        System.out.println("boardId");
        BoardDao board = boardService.getBoard(boardId);
        String email = user.getUsername();
        Member member = userService.getMember(email);
        answerService.create(member, board, reply);
        return "redirect:/myBoard/boardList/detail?id="+boardId;
    }

    //답변 수정 Form
    @GetMapping("/boardList/detail/answer/update")
    public String updateAnswer(@RequestParam long id, Model model
                                ,@ModelAttribute("boardId") long boardId)
    {
        Answer a = answerService.getAnswer(id);
        model.addAttribute("answer", a);
        return "updateAnswer";
    }
    //답변 수정
    @PostMapping("/boardList/detail/answer/update")
    public String updateAnswer(@RequestParam long id, @RequestParam String reply, @ModelAttribute("boardId") long boardId)
    {
        Answer a = answerService.getAnswer(id);
        Answer answer = answerService.update(a, reply);
        return "redirect:/myBoard/boardList/detail?id="+boardId;
    }
    //답변 삮제
    @GetMapping("/boardList/detail/answer/delete")
    public String deleteAnswer(@RequestParam long id, @ModelAttribute("boardId") long boardId)
    {
        answerService.deleteAnswer(id);
        return "redirect:/myBoard/boardList/detail?id="+boardId;
    }
}
