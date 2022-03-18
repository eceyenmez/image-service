package com.example.imageservice.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.example.imageservice.model.PredefinedImageType;
import com.example.imageservice.model.S3Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.s3.transfer.Upload;

@Service
public class AWSS3Service {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    protected TransferManager transferManager;

    @Value("dummyBucketName")
    protected String bucketName;

    protected Logger logger = LoggerFactory.getLogger(AWSS3Service.class);

    public String generateS3Directory(PredefinedImageType type, String fileName){

        S3Directory s3Directory = new S3Directory(type,fileName);
        return s3Directory.getS3Directory();
    }

    public boolean doesObjectExist(String objectName)
    {
        String awsBucketName = "test";

        //---mocking s3 result start--- delete this part and to test
        Random rd = new Random(); // creating Random object
        System.out.println("random generated boolean: "+rd.nextBoolean());
        return rd.nextBoolean();
        //mocking s3 result end---

        //return amazonS3.doesObjectExist(awsBucketName, objectName); //uncomment this
    }

    public S3Object downloadFile(String s3FilePath) {

        S3Object s3object = new S3Object();

        try {
            s3object = amazonS3.getObject(new GetObjectRequest(bucketName, s3FilePath));
        } catch (Exception e) {
            //Mocking s3
            logger.info("Act like image downloaded "+e);
        }

        return s3object;

    }

    public void uploadFile(String keyName, File file) {

        final PutObjectRequest request = new PutObjectRequest(bucketName, keyName, file);

        Upload upload = transferManager.upload(request);

        try {

            upload.waitForCompletion();

        } catch (Exception e) {
            //Mocking s3
            logger.info("Act like image uploaded "+e);
        }
    }

    public void deleteFile(String s3FilePath){
        //Mocking s3
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, s3FilePath));
        }catch (Exception e){
            logger.info("Act like image deleted");
        }
    }

}
