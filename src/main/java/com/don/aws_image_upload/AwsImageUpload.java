package com.don.aws_image_upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AwsImageUpload {

	public static void main(String[] args) {
		System.setProperty("aws.java.v1.disableDeprecationAnnouncement", "true");
		SpringApplication.run(AwsImageUpload.class, args);
	}

}
