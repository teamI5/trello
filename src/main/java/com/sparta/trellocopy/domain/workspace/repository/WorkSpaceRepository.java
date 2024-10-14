package com.sparta.trellocopy.domain.workspace.repository;

import com.sparta.trellocopy.domain.workspace.entity.WorkSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkSpaceRepository extends JpaRepository<WorkSpace, Long> {

    public List<WorkSpace> findByUsers_User_Id(Long userId);
}
