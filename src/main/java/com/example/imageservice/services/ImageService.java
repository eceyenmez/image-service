package com.example.imageservice.services;

import com.amazonaws.services.s3.model.S3Object;
import com.example.imageservice.model.PredefinedImageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class ImageService {

    final AWSS3Service AWSS3Service;

    public ImageService(AWSS3Service AWSS3Service) {
        this.AWSS3Service = AWSS3Service;
    }

    protected Logger logger = LoggerFactory.getLogger(ImageService.class);

    public String getImage(PredefinedImageType type, String imageName) {

        String requestedS3Directory = AWSS3Service.generateS3Directory(type, imageName);

        try {
            if (AWSS3Service.doesObjectExist(requestedS3Directory)) {
                return requestedS3Directory;
            } else {
                String originalS3Directory = AWSS3Service.generateS3Directory(PredefinedImageType.ORIGINAL, imageName);
                if (AWSS3Service.doesObjectExist(originalS3Directory)) {
                    System.out.println("DOWNLOADING");
                    S3Object s3object = AWSS3Service.downloadFile(originalS3Directory);
                    InputStream temp = s3object.getObjectContent();
                    String resizedS3Directory = AWSS3Service.generateS3Directory(type, imageName);
                    AWSS3Service.uploadFile(resizedS3Directory, PredefinedImageType.resize(temp));
                    return resizedS3Directory;
                }else {
                    logger.info("Image does not exists");
                    return "";
                }
            }

        } catch (Exception e) {
            //Mocking s3
            System.out.println("Act like it worked fine" + e);

        }

        return requestedS3Directory;

    }


    //func flush
    public void deleteImage(PredefinedImageType type, String imageName) {

        if (type.equals(PredefinedImageType.ORIGINAL)) {
            //delete all
            for (PredefinedImageType predefinedImageType : PredefinedImageType.values()) {
                String requestedS3Directory = AWSS3Service.generateS3Directory(predefinedImageType, imageName);
                AWSS3Service.deleteFile(requestedS3Directory);
            }

        } else {
            //delete one
            String requestedS3Directory = AWSS3Service.generateS3Directory(type, imageName);
            AWSS3Service.deleteFile(requestedS3Directory);
        }

    }


}
