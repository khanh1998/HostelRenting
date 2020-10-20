package org.avengers.capstone.hostelrenting.dto.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author duattt on 10/11/20
 * @created 11/10/2020 - 11:12
 * @project youthhostelapp
 */
@AllArgsConstructor
@Setter
@Getter
public class UploadFileResponse implements Serializable {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
}
