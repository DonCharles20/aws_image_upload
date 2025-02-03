package com.don.aws_image_upload.datastore;

import com.don.aws_image_upload.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {
    private static final List<UserProfile> USER_PROFILES= new ArrayList<>();
            //UUID.random()
    static {
        USER_PROFILES.add(new UserProfile(UUID.fromString("10c79cc8-7d7b-42a0-b6e6-dd860000817f"),"Seer",null));
        USER_PROFILES.add(new UserProfile(UUID.fromString("5c891c35-b4ae-4113-b0cb-c0735aab37f2"),"Tom",null));
    }

    public static List<UserProfile> getUserProfiles() {
        return USER_PROFILES;
    }
}
