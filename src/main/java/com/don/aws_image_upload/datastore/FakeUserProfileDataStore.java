package com.don.aws_image_upload.datastore;

import com.don.aws_image_upload.profile.UserProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FakeUserProfileDataStore {
    private static final List<UserProfile> USER_PROFILES= new ArrayList<>();

    static {
        USER_PROFILES.add(new UserProfile(UUID.randomUUID(),"seer",null));
        USER_PROFILES.add(new UserProfile(UUID.randomUUID(),"ton",null));
    }

    public static List<UserProfile> getUserProfiles() {
        return USER_PROFILES;
    }
}
