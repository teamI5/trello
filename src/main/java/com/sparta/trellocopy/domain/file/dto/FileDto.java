package com.sparta.trellocopy.domain.file.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class FileDto {

    private String originalFilename;
    private String uploadFileName;
    private String fileUrl;
    private String fileType;
    private String fileSize;

}
