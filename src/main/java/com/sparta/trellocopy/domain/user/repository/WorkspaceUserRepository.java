package com.sparta.trellocopy.domain.user.repository;

import com.sparta.trellocopy.domain.user.entity.WorkspaceUser;
import com.sparta.trellocopy.domain.workspace.entity.WorkSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser, Long> {

    @Query("SELECT wu FROM WorkspaceUser wu JOIN FETCH wu.user WHERE wu.workspace.id = :workspaceId AND wu.user.id = :userId")
    Optional<WorkspaceUser> findByWorkspaceIdAndUserId(@Param("workspaceId") Long workspaceId, @Param("userId") Long userId);

    @Query("SELECT wu.workspace FROM WorkspaceUser wu JOIN FETCH wu.workspace WHERE wu.user.id = :userId")
    List<WorkSpace> findAllWorkspacesByUserId(@Param("userId") Long userId);
}
