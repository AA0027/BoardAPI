package com.example.board.repo;

import com.example.board.dao.BoardDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardDao, Long> {

    Page<BoardDao> findAll(Pageable pageable);
}
