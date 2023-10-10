package com.example.board.service;

import com.example.board.DataNotFoundException;
import com.example.board.dao.Answer;
import com.example.board.dao.BoardDao;
import com.example.board.dao.Member;
import com.example.board.repo.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepo;

    public Answer getAnswer(long id)
    {
        Answer answer = answerRepo.findById(id)
                .orElseThrow(()->new DataNotFoundException("Not found answer"));
        return answer;
    }
    public Answer create(Member member, BoardDao board, String reply)
    {
        Answer answer = new Answer(member, board, reply);
        answerRepo.save(answer);
        return answer;
    }

    //계시물에 답변들을 가져오는 기능
    public List<Answer> allAnswer(long boardId)
    {
        List<Answer> answers = answerRepo.findAll();
        if(answers == null)
            throw new DataNotFoundException("No answers");
        List<Answer> result = new ArrayList<>();
        for(Answer answer : answers)
        {
            if(answer.getBoard().getBoard_id() == boardId)
                result.add(answer);
        }
        if(result == null)
            throw new DataNotFoundException("No answers");
        return result;
    }
    //업데이트
    public Answer update(Answer answer, String re)
    {
        answer.setReply(re);
        Answer result = answerRepo.save(answer);
        return result;
    }
    //답변 삮제
    public void deleteAnswer(long answerId)
    {
        answerRepo.deleteById(answerId);
    }
}
