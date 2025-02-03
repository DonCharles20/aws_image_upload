package com.don.aws_image_upload.profile;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class UserProfile {
    private final UUID UserProfileId;
    private final String username;
    private String userProfileImageLink;//s3 Key

    public UserProfile(UUID userProfileId, String username, String userProfileImageLink) {
        UserProfileId = userProfileId;
        this.username = username;
        this.userProfileImageLink = userProfileImageLink;
    }


    public UUID getUserProfileId() {
        return UserProfileId;
    }


    public String getUsername() {
        return username;
    }


    public Optional<String> getUserProfileImageLink() {
        return Optional.ofNullable(userProfileImageLink);
    }

    public void setUserProfileImageLink(String userProfileImageLink) {
        this.userProfileImageLink = userProfileImageLink;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(UserProfileId, that.UserProfileId) &&
                Objects.equals(username, that.username) &&
                Objects.equals(userProfileImageLink, that.userProfileImageLink);
        /*Objects.equals is used to check if any of the objects are null
        * this will resolve any issues in the case null is passed */
    }

    @Override
    public int hashCode() {
        return Objects.hash(UserProfileId, username, userProfileImageLink);
    }
}
