package com.don.aws_image_upload.profile;

import com.don.aws_image_upload.datastore.FakeUserProfileDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserProfileDataAccesService {

    private final FakeUserProfileDataStore fakeUserProfileDataStore;

    public UserProfileDataAccesService(FakeUserProfileDataStore fakeUserProfileDataStore){
        this.fakeUserProfileDataStore=fakeUserProfileDataStore;
    }

    List<UserProfile>getUserProfiles(){
        return fakeUserProfileDataStore.getUserProfiles();
    }
}
