package com.sparta.trellocopy.domain.board.repository;

import com.sparta.trellocopy.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByWorkspace_Id(Long workspaceId);
}
