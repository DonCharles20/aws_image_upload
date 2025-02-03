package com.don.aws_image_upload.fileStore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
public class FileStore {
    private final AmazonS3 s3;
    public FileStore(AmazonS3 s3){
        this.s3=s3;
    }

        public void save(String bucketname,String path,
                         String fileName,
                         Optional<Map<String, String>> optionalMetadata,
                         InputStream inputStream) throws IOException {

            byte[] fileBytes = inputStream.readAllBytes();
            InputStream resettableStream = new ByteArrayInputStream(fileBytes);

            ObjectMetadata metadata= new ObjectMetadata();
            metadata.setContentLength(fileBytes.length);
            optionalMetadata.ifPresent(map ->{
                if (!map.isEmpty()){
                    map.forEach(metadata::addUserMetadata);
                }
            });



            try{
                s3.putObject(new PutObjectRequest(bucketname, fileName, resettableStream, metadata));
            } catch (AmazonServiceException e) {
                throw new IllegalStateException("Failed to store file to S3", e);
            }

            System.out.println("File uploaded successfully");
        }


    public byte[] download(String path,String key) {
        try{
            S3Object object = s3.getObject(path, key);
            return IOUtils.toByteArray(object.getObjectContent());
        }catch (AmazonServiceException|IOException e){
            throw new IllegalStateException("failed to download file",e);
        }
    }
}
