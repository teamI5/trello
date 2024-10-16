package com.sparta.trellocopy.domain.file.controller;

import com.sparta.trellocopy.domain.card.entity.Card;
import com.sparta.trellocopy.domain.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/cards/{cardId}/files/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam MultipartFile file,
            @PathVariable Long cardId) throws IOException {
        Card card = findCardById(cardId);  // 이 부분은 CardService로부터 Card를 받아오는 로직이 필요
        String fileUrl = fileService.uploadFile(file, card);
        return ResponseEntity.ok(fileUrl); // 업로드된 파일의 URL 반환
    }

    // 파일 다운로드 요청 처리
    @GetMapping("/cards/{cardId}/files/download/{fileId}")
    public ResponseEntity<UrlResource> downloadFile(
            @PathVariable Long fileId) throws IOException {
        return fileService.downloadFile(fileId);
    }

    // 파일 삭제 요청 처리
    @DeleteMapping("/cards/{cardId}/files/delete/{fileId}")
    public ResponseEntity<Void> deleteFile(
            @PathVariable Long fileId) {
        fileService.deleteFile(fileId);
        return ResponseEntity.noContent().build();  // 성공적으로 삭제 시 204 응답
    }

    // Card 엔티티를 받아오는 로직 필요
    private Card findCardById(Long cardId) {
        // 여기에 cardId로 Card를 찾아오는 로직이 필요. CardService에서 Card를 찾아서 반환
        // 예를 들어, cardService.findById(cardId)와 같은 방식으로 Card 객체를 받아올 수 있음.
        return new Card();  // 실제 구현에서는 CardService로 대체
    }

}
