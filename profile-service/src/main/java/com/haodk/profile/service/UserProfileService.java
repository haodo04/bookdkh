package com.haodk.profile.service;

import org.springframework.stereotype.Service;

import com.haodk.profile.dto.request.ProfileCreationRequest;
import com.haodk.profile.dto.response.UserProfileResponse;
import com.haodk.profile.entity.UserProfile;
import com.haodk.profile.mapper.UserProfileMapper;
import com.haodk.profile.repository.UserProfileRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileService {
    UserProfileRepository userProfileRepository;
    UserProfileMapper userProfileMapper;

    public UserProfileResponse createProfile(ProfileCreationRequest request) {
        UserProfile userProfile = userProfileMapper.toUserProfile(request);
        userProfile = userProfileRepository.save(userProfile);
        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    public UserProfileResponse getUserProfile(Long userId) {
        UserProfile userProfile =
                userProfileRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Profile not found"));
        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    public List<UserProfile> getAllUserProfile() {
        return userProfileRepository.findAll();
    }

    public void deleteProfile(Long userId) {
        userProfileRepository.deleteByUserId(userId);
    }
}
