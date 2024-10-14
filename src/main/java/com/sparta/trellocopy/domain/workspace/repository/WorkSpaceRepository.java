package com.sparta.trellocopy.domain.workspace.repository;

import com.sparta.trellocopy.domain.workspace.entity.WorkSpace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkSpaceRepository extends JpaRepository<WorkSpace, Long> {
}
