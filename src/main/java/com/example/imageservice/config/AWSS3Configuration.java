package com.example.imageservice.config;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import static com.amazonaws.services.s3.internal.Constants.MB;


@Configuration
public class AWSS3Configuration {

    private Logger logger = LoggerFactory.getLogger(AWSS3Configuration.class);

    @Primary
    @Bean
    public AmazonS3 s3client() {
        AWSCredentials awsCredentials = new BasicAWSCredentials("test", "awsSecretKey");
        return AmazonS3ClientBuilder
                .standard()
                .withRegion("eu-central-1")
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

    @Bean
    public TransferManager transferManager(){

        TransferManager tm = TransferManagerBuilder.standard()
                .withS3Client(s3client())
                .withDisableParallelDownloads(false)
                .withMinimumUploadPartSize(Long.valueOf(5 * MB))
                .withMultipartUploadThreshold(Long.valueOf(16 * MB))
                .withMultipartCopyPartSize(Long.valueOf(5 * MB))
                .withMultipartCopyThreshold(Long.valueOf(100 * MB))
                .withExecutorFactory(()->createExecutorService())
                .build();
        

        return tm;
    }

    private ThreadPoolExecutor createExecutorService() {
        ThreadFactory threadFactory = new ThreadFactory() {
            private int threadCount = 1;

            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("jsa-amazon-s3-transfer-manager-worker-" + threadCount++);
                return thread;
            }
        };
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(20, threadFactory);
    }
}
