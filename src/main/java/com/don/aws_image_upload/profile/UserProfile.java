package com.don.aws_image_upload.profile;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class UserProfile {
    private UUID UserProfileId;
    private String username;
    private String userProfileImageLink;//s3 Key

    public UserProfile(UUID userProfileId, String username, String userProfileImageLink) {
        UserProfileId = userProfileId;
        this.username = username;
        this.userProfileImageLink = userProfileImageLink;
    }


    public UUID getUserProfileId() {
        return UserProfileId;
    }

    public void setUserProfileId(UUID userProfileId) {
        UserProfileId = userProfileId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
    }

    @Override
    public int hashCode() {
        return Objects.hash(UserProfileId, username, userProfileImageLink);
    }
}
