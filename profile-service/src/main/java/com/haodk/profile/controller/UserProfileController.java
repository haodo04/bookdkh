package com.haodk.profile.controller;

import com.haodk.profile.entity.UserProfile;
import org.springframework.web.bind.annotation.*;

import com.haodk.profile.dto.request.ProfileCreationRequest;
import com.haodk.profile.dto.response.UserProfileResponse;
import com.haodk.profile.service.UserProfileService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserProfileController {
    UserProfileService userProfileService;

    @PostMapping("/users")
    UserProfileResponse createProfile(@RequestBody ProfileCreationRequest request) {
        return userProfileService.createProfile(request);
    }

    @GetMapping("/{userId}")
    UserProfileResponse getProfileById(@PathVariable Long userId) {
        return userProfileService.getUserProfile(userId);
    }

    @GetMapping("/all")
    List<UserProfile> getAllProfile() {
        return userProfileService.getAllUserProfile();
    }

    @DeleteMapping("/{userId}")
    void deleteProfileById(@PathVariable Long userId) {
        userProfileService.deleteProfile(userId);
    }
}
