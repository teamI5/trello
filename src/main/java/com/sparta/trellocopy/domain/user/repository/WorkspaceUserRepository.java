package com.sparta.trellocopy.domain.user.repository;

import com.sparta.trellocopy.domain.user.entity.WorkspaceUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser, Long> {
}
