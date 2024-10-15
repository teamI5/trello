package com.sparta.trellocopy.domain.list.repository;

import com.sparta.trellocopy.domain.list.entity.Lists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ListRepository extends JpaRepository<Lists,Long> {


    @Query("select l from Lists l order by l.orderNumber asc ")
    List<Lists> orderNumberAsc();

    Optional<Lists> findByOrderNumber(Long orderNumber);

    List<Lists> findAllByBoardId(Long boardId);
}
