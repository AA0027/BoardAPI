package com.example.board.service;

import com.example.board.DataNotFoundException;
import com.example.board.dao.BoardDao;
import com.example.board.dao.Member;
import com.example.board.dto.Board;
import com.example.board.repo.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository repo;
    public int getTotalCount(){return repo.findAll().size();}
    public BoardDao save(Member member, String title, String content)
    {
        BoardDao b = BoardDao.builder().title(title)
                .content(content)
                .createDate(LocalDateTime.now())
                .viewCnt(0)
                .member(member).build();
        repo.save(b);
        return b;
    }

    public void delete(long id)
    {
        repo.deleteById(id);
    }
    @Transactional
    public void update(long id, String title, String content)
    {
        BoardDao b = repo.findById(id)
                .orElseThrow(()->new DataNotFoundException("Not founded boardId is "+id));
        b.setTitle(title);
        b.setContent(content);
        repo.save(b);
    }
    @Transactional
    public void update(BoardDao board)
    {
        repo.save(board);
    }

    public BoardDao getBoard(long id)
    {
        BoardDao b = repo.findById(id).orElseThrow(()->new DataNotFoundException("Not founded board "+id));
        return b;
    }

    public Page<BoardDao> getList(int page)
    {
        Pageable pageable = PageRequest.of(page, 10);
        return repo.findAll(pageable);
    }
}
