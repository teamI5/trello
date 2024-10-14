package com.sparta.trellocopy.domain.workspace.repository;

import com.sparta.trellocopy.domain.workspace.entity.WorkSpaceUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkSpaceUserRepository extends JpaRepository<WorkSpaceUser, Long> {
}
