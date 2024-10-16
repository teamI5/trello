package com.sparta.trellocopy.domain.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sparta.trellocopy.domain.card.entity.Card;
import com.sparta.trellocopy.domain.common.exception.NotFoundException;
import com.sparta.trellocopy.domain.file.entity.File;
import com.sparta.trellocopy.domain.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final AmazonS3 amazonS3;
    private final FileRepository fileRepository;

    @Value("${AWS_BUCKET}")
    private String bucket;

    // 파일 업로드
    public String uploadFile(MultipartFile multipartFile, Card card) throws IOException {

        String originalFilename = multipartFile.getOriginalFilename();
        String uploadFileName = generateUniqueFileName(originalFilename);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, originalFilename,
                multipartFile.getInputStream(), metadata);

        // 파일 URL 생성
        String fileUrl = amazonS3.getUrl(bucket, uploadFileName).toString();

        // DB에 파일 메타데이터 저장
        File file = new File(
                card,
                uploadFileName,
                originalFilename,
                fileUrl,
                multipartFile.getContentType(),
                multipartFile.getSize()
        );
        fileRepository.save(file);

        return fileUrl;

    }

    // 파일 다운로드 (조회)?
    @Transactional(readOnly = true)
    public ResponseEntity<UrlResource> downloadFile(Long fileId) throws IOException {

        File file = findFileById(fileId);

        UrlResource urlResource = new UrlResource(file.getFileUrl());

        String contentDisposition = "attachment; filename=\"" +  file.getOriginalFilename() + "\"";

        // header에 CONTENT_DISPOSITION 설정을 통해 클릭 시 다운로드 진행?
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);

    }

    // 파일 삭제
    @Transactional
    public void deleteFile(Long fileId) {

        File file = findFileById(fileId);

        amazonS3.deleteObject(bucket, file.getUploadFileName());

        fileRepository.delete(file);
    }

    private File findFileById(Long fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new NotFoundException("해당 파일 찾을 수 없음"));
    }

    // 파일 이름의 유일성을 보장하기 위해 UUID
    private String generateUniqueFileName(String originalFilename) {
        return UUID.randomUUID().toString() + "_" + originalFilename;
    }

}
