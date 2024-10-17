package com.sparta.trellocopy.domain.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparta.trellocopy.domain.card.entity.Card;
import com.sparta.trellocopy.domain.card.repository.CardRepository;
import com.sparta.trellocopy.domain.common.exception.NotFoundException;
import com.sparta.trellocopy.domain.file.dto.FileDto;
import com.sparta.trellocopy.domain.file.entity.File;
import com.sparta.trellocopy.domain.file.repository.FileRepository;
import com.sparta.trellocopy.domain.user.dto.AuthUser;
import com.sparta.trellocopy.domain.user.entity.User;
import com.sparta.trellocopy.domain.user.entity.WorkspaceRole;
import com.sparta.trellocopy.domain.user.entity.WorkspaceUser;
import com.sparta.trellocopy.domain.user.repository.UserRepository;
import com.sparta.trellocopy.domain.user.repository.WorkspaceUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final AmazonS3 amazonS3;
    private final FileRepository fileRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final WorkspaceUserRepository workspaceUserRepository;

    @Value("${AWS_BUCKET}")
    private String bucket;

//  파일 업로드
    public List<FileDto> uploadFiles(String fileType,
                                     List<MultipartFile> multipartFiles,
                                     Long cardId, AuthUser authUser) {

        Card card = cardRepository.findByIdOrElseThrow(cardId);
        User user = findUserById(authUser.getId());
        checkUserRole(card.getLists().getBoard().getWorkspace().getId(), user);

        List<FileDto> s3files = new ArrayList<>();
        String uploadFilePath = fileType + "/" + getFolderName();

        for (MultipartFile multipartFile : multipartFiles) {

            if (multipartFile == null || multipartFile.isEmpty()) {
                throw new IllegalArgumentException("파일 비어있음");
            }

            validFile(multipartFile);

            String originalFileName = multipartFile.getOriginalFilename();
            String uploadFileName = getUuidFileName(originalFileName);
            String uploadFileUrl = "";

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFile.getSize());
            objectMetadata.setContentType(multipartFile.getContentType());

            try (InputStream inputStream = multipartFile.getInputStream()) {

                String keyName = uploadFilePath + "/" + uploadFileName; // ex) 구분/년/월/일/파일.확장자

                // S3에 폴더 및 파일 업로드
                amazonS3.putObject(
                        new PutObjectRequest(bucket, keyName, inputStream, objectMetadata));

                // S3에 업로드한 폴더 및 파일 URL
                uploadFileUrl = amazonS3.getUrl(bucket, keyName).toString();

                File file = new File(
                        card,
                        uploadFileName,
                        originalFileName,
                        uploadFileUrl,
                        uploadFilePath,
                        multipartFile.getContentType(),
                        multipartFile.getSize()
                );

                fileRepository.save(file);

            } catch (IOException e) {
                log.error("파일 업로드 실패", e);
            }

            s3files.add(
                    FileDto.builder()
                            .originalFilename(originalFileName) // 여기서 'originalFileName'을 'originalFilename'으로 수정
                            .uploadFileName(uploadFileName)
                            .filePath(uploadFilePath)
                            .fileUrl(uploadFileUrl)
                            .fileType(multipartFile.getContentType())
                            .fileSize(String.valueOf(multipartFile.getSize()))
                            .build());
        }

        return s3files;
    }

    // 파일 다운로드 (조회)?
    @Transactional(readOnly = true)
    public FileDto downloadFile(Long cardId, Long fileId) {

        File file = findFileById(fileId);

        if (!file.getCard().getId().equals(cardId)) {
            throw new IllegalArgumentException("파일 카드 일치하지 않음");
        }

        String keyName = file.getFilePath() + "/" + file.getUploadFileName();
        FileDto fileDto = null;

        try {
            // S3에서 파일 메타데이터 가져오기
            ObjectMetadata metadata = amazonS3.getObjectMetadata(bucket, keyName);
            String uploadFileUrl = amazonS3.getUrl(bucket, keyName).toString();

            // S3FileDto 객체 생성
            fileDto = FileDto.builder()
                    .originalFilename(file.getOriginalFilename())
                    .uploadFileName(file.getUploadFileName())
                    .filePath(file.getFilePath())
                    .fileUrl(uploadFileUrl)
                    .fileType(metadata.getContentType())
                    .fileSize(String.valueOf(metadata.getContentLength())) // getContentLength 파일의 크기를 바이트 단위로 가져옴
                    .build();
        } catch (Exception e) {
            log.error("파일을 찾을 수 없음: {}", e.getMessage());
        }

        return fileDto;

    }

    // 파일 삭제
    public String deleteFile(Long cardId, Long fileId, AuthUser authUser) {

        Card card = cardRepository.findByIdOrElseThrow(cardId);
        User user = findUserById(authUser.getId());
        checkUserRole(card.getLists().getBoard().getWorkspace().getId(), user);

        String result = "파일 삭제 성공";

        try {
            File file = findFileById(fileId);
            String keyName = file.getFilePath() + "/" + file.getUploadFileName(); // ex) 구분/년/월/일/파일.확장자

            boolean isObjectExist = amazonS3.doesObjectExist(bucket, keyName);
            if (isObjectExist) {
                amazonS3.deleteObject(bucket, keyName);
                fileRepository.delete(file);
            } else {
                result = "파일을 찾을 수 없음";
            }
        } catch (NotFoundException e) {
            log.error("파일을 찾을 수 없음", e);
            result = "파일을 찾을 수 없음";
        }

        return result;
    }

//  UUID 파일명 반환 고유 파일명
    public String getUuidFileName(String fileName) {
        String ext = fileName.substring(fileName.indexOf(".") + 1);
        return UUID.randomUUID().toString() + "." + ext;
    }

//  년/월/일 폴더명 반환
    private String getFolderName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String str = sdf.format(date);
        return str.replace("-", "/");
    }

    private File findFileById(Long fileId){
        File file = fileRepository.findById(fileId)
                .orElseThrow(()-> new NotFoundException("파일을 찾을 수 없음"));

        return file;
    }

    private final List<String> fileTypes = Arrays.asList(
            "image/jpeg", "image/png", "application/pdf", "text/csv");
    private final long maxFileSize = 5 * 1024 * 1024; // 5MB

    // 첨부파일 형식 , 크기 예외처리
    private void validFile(MultipartFile multipartFile) {
        String fileType = multipartFile.getContentType();
        long fileSize = multipartFile.getSize();

        if (!fileTypes.contains(fileType)) {
            throw new IllegalArgumentException("지원하지 않는 타입");
        }

        if (fileSize > maxFileSize) {
            throw new IllegalArgumentException("크기 5MB 초과");
        }

    }

    // 유저의 역할 확인 읽기 전용일 경우 예외처리
    private void checkUserRole(Long workspaceId, User user) {

        WorkspaceUser workspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(workspaceId,user.getId())
                .orElseThrow(() -> new NotFoundException("사용자가 해당 워크스페이스에 존재하지 않음"));

        if (workspaceUser.getRole().equals(WorkspaceRole.READ_ONLY)){
            throw new IllegalArgumentException("권한이 없습니다");
        }
    }

    private User findUserById(Long userId) {
        User user = userRepository.findByIdOrElseThrow(userId);

        return user;
    }

}
