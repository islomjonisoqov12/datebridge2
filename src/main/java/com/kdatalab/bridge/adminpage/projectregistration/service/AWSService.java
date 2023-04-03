package com.kdatalab.bridge.adminpage.projectregistration.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AWSService {

    private final AmazonS3 s3Client;

    @Value("${aws.bucket}")
    private String bucketName;

    public void uploadImageToAws(MultipartFile file, String filePath) throws IOException {

        String path = filePath + "/" + file.getOriginalFilename();

        s3Client.putObject(bucketName,path, file.getInputStream(), null);
    }

    public void deleteImageFromAws(String path) {
        ObjectListing objectListing = s3Client.listObjects(bucketName, path);
        objectListing.getObjectSummaries().forEach(s3ObjectSummary -> s3Client.deleteObject(new DeleteObjectRequest(bucketName, s3ObjectSummary.getKey())));
    }
}
