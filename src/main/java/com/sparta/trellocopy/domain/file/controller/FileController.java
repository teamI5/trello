package com.sparta.trellocopy.domain.file.controller;

import com.sparta.trellocopy.domain.card.entity.Card;
import com.sparta.trellocopy.domain.file.dto.FileDto;
import com.sparta.trellocopy.domain.file.service.FileService;
import com.sparta.trellocopy.domain.user.dto.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/cards/{cardId}/files/upload")
    public ResponseEntity<Object> uploadFile(
            @PathVariable Long cardId,
            @RequestParam(value = "fileType") String fileType,
            @RequestPart(value = "files") List<MultipartFile> multipartFiles,
            @AuthenticationPrincipal AuthUser authUser) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fileService.uploadFiles(
                        fileType, multipartFiles, cardId, authUser));
    }

    // 파일 다운로드 요청 처리
    @GetMapping("/cards/{cardId}/files/download/{fileId}")
    public ResponseEntity<FileDto> downloadFile(
            @PathVariable Long cardId,
            @PathVariable Long fileId) throws IOException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fileService.downloadFile(cardId, fileId));
    }

    // 파일 삭제 요청 처리
    @DeleteMapping("/cards/{cardId}/files/delete/{fileId}")
    public ResponseEntity<Object> deleteFile(
            @PathVariable Long cardId,
            @PathVariable Long fileId,
            @AuthenticationPrincipal AuthUser authUser) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fileService.deleteFile(cardId, fileId, authUser));
    }

}
