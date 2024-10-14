package com.sparta.trellocopy.domain.list.repository;

import com.sparta.trellocopy.domain.list.entity.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<List,Long> {
}
