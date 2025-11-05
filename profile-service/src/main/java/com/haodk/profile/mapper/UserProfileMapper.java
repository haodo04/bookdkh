package com.haodk.profile.mapper;

import org.mapstruct.Mapper;

import com.haodk.profile.dto.request.ProfileCreationRequest;
import com.haodk.profile.dto.response.UserProfileResponse;
import com.haodk.profile.entity.UserProfile;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(ProfileCreationRequest request);

    UserProfileResponse toUserProfileResponse(UserProfile entity);
}
