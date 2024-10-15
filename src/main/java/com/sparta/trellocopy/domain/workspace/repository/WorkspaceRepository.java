package com.sparta.trellocopy.domain.workspace.repository;

import com.sparta.trellocopy.domain.workspace.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
}
