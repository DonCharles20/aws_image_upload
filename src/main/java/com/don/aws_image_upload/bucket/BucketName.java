package com.don.aws_image_upload.bucket;

public enum BucketName {
    PROFILE_IMAGE("don-image-upload");

    private final String bucketName;

    BucketName(String bucketName){
        this.bucketName=bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
