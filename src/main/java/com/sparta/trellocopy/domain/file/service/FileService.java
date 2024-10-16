package com.sparta.trellocopy.domain.file.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {

//    private final AmazonS3 amazonS3;
    private final String bucket = "${AWS_BUCKET}";

    private String uploadFile() {

        return "";

    }

}
