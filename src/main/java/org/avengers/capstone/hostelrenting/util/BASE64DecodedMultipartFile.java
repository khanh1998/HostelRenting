package org.avengers.capstone.hostelrenting.util;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author duattt on 10/21/20
 * @created 21/10/2020 - 12:18
 * @project youthhostelapp
 */
@Builder
public class BASE64DecodedMultipartFile implements MultipartFile {
    private final byte[] fileContent;
    private String fileName;
    private String contentType;

    public BASE64DecodedMultipartFile(byte[] fileContent, String fileName, String contentType) {
        super();
        this.fileContent = fileContent;
        this.fileName = fileName;
        this.contentType = contentType;
    }


    @Override
    public String getName() {
        return fileName;
    }

    @Override
    public String getOriginalFilename() {
        return fileName;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return fileContent == null || fileContent.length == 0;
    }

    @Override
    public long getSize() {
        return fileContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return fileContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(fileContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(fileContent);
    }
}