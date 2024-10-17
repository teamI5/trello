package com.sparta.trellocopy.domain.workspace.service;

import com.sparta.trellocopy.domain.user.dto.AuthUser;
import com.sparta.trellocopy.domain.workspace.dto.WorkspaceResponse;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkspaceConcurrencyService {

    private final WorkspaceService workspaceService;

    // 맴버 추가 낙관적 락 예외 발생시 반복
    public WorkspaceResponse addUserAtWorkSpaceRepeat(
            Long workspaceId,
            String email,
            AuthUser authUser
    ){
        int tries = 0;
        int maxTry = 10;

        while (tries<maxTry){
            try {
                return workspaceService.addUserAtWorkSpace(workspaceId, email, authUser);
            }catch (OptimisticLockException e){
                tries++;
                try{
                    Thread.sleep(50);
                }catch (InterruptedException ex){
                    throw new RuntimeException();
                }
            }
        }
        throw new RuntimeException("사용자가 많아 맴버 추가가 지연되고 있습니다. 잠시 후 다시 시도해 주세요.");
    }
}
