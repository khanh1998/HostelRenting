package org.avengers.capstone.hostelrenting.service.impl;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.*;
import org.avengers.capstone.hostelrenting.dto.file.UploadFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.Objects;

/**
 * @author duattt on 10/11/20
 * @created 11/10/2020 - 11:01
 * @project youthhostelapp
 */
@Service
public class FileStorageServiceImp {
    private static final Logger logger = LoggerFactory.getLogger(FileStorageServiceImp.class);
    @Value("${azure.storage-location}")
    private String storageLocation;
    @Value("${azure.storage-connection}")
    private String storageConnection;
    @Value("${azure.storage-container}")
    private String storageContainer;

    public UploadFileResponse storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        UploadFileResponse response = null;

        CloudBlobContainer cloudBlobContainer = checkBlobContainer();
        try {
            response = uploadBlob(cloudBlobContainer, file);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }
        return response;
    }

    public Resource loadFileAsResource(String fileName) {
//        try {
//            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
//            Resource resource = new UrlResource(filePath.toUri());
//            if (resource.exists()) {
//                return resource;
//            } else {
//                throw new EntityNotFoundException(AzureProperties.class, "File name", fileName);
//            }
//        } catch (MalformedURLException e) {
//            throw new EntityNotFoundException(Files.class, "File not found " + fileName);
//        }
        return null;
    }

    private CloudBlobContainer checkBlobContainer() {
        CloudStorageAccount storageAccount;
        CloudBlobClient blobClient = null;
        CloudBlobContainer container = null;

        // Parse the connection string and create a blob client to interact with Blob storage
        try {
            storageAccount = CloudStorageAccount.parse(storageConnection);
            blobClient = storageAccount.createCloudBlobClient();
            container = blobClient.getContainerReference(storageContainer);

        // Create the container if it does not exist with public access.
            logger.info("Creating container: " + container.getName());
            container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(), new OperationContext());
        } catch (URISyntaxException | InvalidKeyException | StorageException e) {
            logger.error("ERROR when checking blob container", e);
            e.printStackTrace();
        }

        return container;
    }

    private UploadFileResponse uploadBlob(CloudBlobContainer container, MultipartFile file) throws IOException {
        //Getting a blob reference
        CloudBlockBlob blob = null;
        try {
            blob = container.getBlockBlobReference(file.getOriginalFilename());
            InputStream fis = new BufferedInputStream(file.getInputStream());
            blob.upload(fis, file.getSize());
        } catch (URISyntaxException | StorageException | FileNotFoundException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }

        String fileDownloadUri = ServletUriComponentsBuilder.fromUriString(storageLocation)
                .path(file.getOriginalFilename())
                .toUriString();

        UploadFileResponse res = new UploadFileResponse(file.getName(), fileDownloadUri, file.getContentType(), file.getSize());

        return res;
    }
}
