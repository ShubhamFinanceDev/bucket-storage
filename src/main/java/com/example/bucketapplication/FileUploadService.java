package com.example.bucketapplication;

import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class FileUploadService {

    String bucketName = "uat-bucket";

    /*  you can find namespace in your bucket details page under
    Bucket information in OCI console */
    String namespaceName = "frmprjvkdzni";

    @Autowired
    private OciStorageConfig configuration;

    public void upload(MultipartFile file) throws Exception {

        String objectName = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();


        //build upload request

        PutObjectRequest putObjectRequest =
                PutObjectRequest.builder()
                        .namespaceName(namespaceName)
                        .bucketName(bucketName)
                        .objectName(objectName)
                        .contentLength(file.getSize())
                        .putObjectBody(inputStream)
                        .build();

        //upload the file

        try {
            configuration.getObjectStorage().putObject(putObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }finally{
            configuration.getObjectStorage().close();
        }
    }
}
