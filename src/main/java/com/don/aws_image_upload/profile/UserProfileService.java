package com.don.aws_image_upload.profile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.don.aws_image_upload.bucket.BucketName;
import com.don.aws_image_upload.fileStore.FileStore;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.web.multipart.MultipartFile;

import static io.netty.handler.codec.http.HttpUtil.getMimeType;
import static org.springframework.util.MimeTypeUtils.*;

@Service
public class UserProfileService {
    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService,FileStore fileStore){
        this.userProfileDataAccessService=userProfileDataAccessService;
        this.fileStore=fileStore;
    }

    public List<UserProfile> getUserProfiles() {
        return userProfileDataAccessService.getUserProfiles();
    }


    public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
        //check if file is not empty and Check if file is an image
        checkFileContent(file);
        //the User exists in out database
        UserProfile user = getUser(userProfileId);
        //grab metadata from file if exists
        Optional<Map<String,String>> metadata= Optional.ofNullable(extractMetadata(file));
        String path = String.format("Profile-Image/%s",user.getUserProfileId());
        String filename = String.format("%s-%s",file.getOriginalFilename(), UUID.randomUUID());
        try {
            //store image in s3 and update database (userProfileImageLink)with s3 with image link
            fileStore.save(BucketName.PROFILE_IMAGE.getBucketName(),path, filename, metadata , file.getInputStream());
            user.setUserProfileImageLink(filename);
        }catch (IOException e){
            throw new IllegalStateException("Failed to upload file",e);
        }

    }

    public byte[] downloadUserProfileId(UUID userProfileId) {
        UserProfile user= getUser(userProfileId);
        String path = String.format("Profile-Image/%s",
                user.getUserProfileId());
        return user.getUserProfileImageLink()
                .map(key->fileStore.download(path,key))
                .orElse(new byte[0]);

    }

    private UserProfile getUser(UUID userProfileId) {
        return userProfileDataAccessService.getUserProfiles()
                .stream()
                .filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId)).findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("User Profile %s not found", userProfileId)));
    }

    private void checkFileContent(MultipartFile file) {
        List<String> allowedImageTypes = Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE);
        if(file == null || file.isEmpty()|| !allowedImageTypes.contains(file.getContentType())){
            throw new IllegalStateException("File is either empty, null, or not of filetype png, jpeg, or gif");
        }
    }

    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Name", file.getOriginalFilename());
        metadata.put("Content-Size", String.valueOf(file.getSize()));
        metadata.put("Content-Type", file.getContentType());
        return metadata;
    }



}
