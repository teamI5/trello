package com.sparta.trellocopy.domain.workspace.repository;

import com.sparta.trellocopy.domain.workspace.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkSpaceRepository extends JpaRepository<Workspace, Long> {

    List<Workspace> findByUsers_User_Id(Long userId);
}
