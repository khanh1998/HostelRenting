package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.file.UploadFileResponse;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.service.impl.FileStorageServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author duattt on 10/11/20
 * @created 11/10/2020 - 10:44
 * @project youthhostelapp
 */

@RestController
@RequestMapping("/api/v1")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private FileStorageServiceImp fileStorageService;

    @Autowired
    public void setFileStorageService(FileStorageServiceImp fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

//    @PostMapping(value = "images", produces = {"application/json"})
//    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
//        UploadFileResponse res = uploadFile(file);
//
//        ApiSuccess<?> apiSuccess = new ApiSuccess<>(res, "Your file has been uploaded successfully!");
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
//    }

    @PostMapping(value = "/images", produces = {"application/json"})
    public ResponseEntity<?> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {

        List<UploadFileResponse> resFiles = Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resFiles, "Your file has been uploaded successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    private UploadFileResponse uploadFile(MultipartFile file) {
        UploadFileResponse uploadFileResponse = fileStorageService.storeFile(file);

        return uploadFileResponse;
    }


}
