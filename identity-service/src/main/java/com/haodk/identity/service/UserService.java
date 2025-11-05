package com.haodk.identity.service;

import java.util.HashSet;
import java.util.List;

import com.haodk.identity.mapper.ProfileMapper;
import com.haodk.identity.repository.httpclient.ProfileClient;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.haodk.identity.constant.PredefinedRole;
import com.haodk.identity.dto.request.UserCreationRequest;
import com.haodk.identity.dto.request.UserUpdateRequest;
import com.haodk.identity.dto.response.UserResponse;
import com.haodk.identity.entity.Role;
import com.haodk.identity.entity.User;
import com.haodk.identity.exception.AppException;
import com.haodk.identity.exception.ErrorCode;
import com.haodk.identity.mapper.UserMapper;
import com.haodk.identity.repository.RoleRepository;
import com.haodk.identity.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    ProfileMapper profileMapper;
    PasswordEncoder passwordEncoder;
    ProfileClient profileClient;

    public UserResponse createUser(UserCreationRequest request) {
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);

        user.setRoles(roles);
        user = userRepository.save(user);

        Long newUserId = user.getId();
        var profileRequest = profileMapper.toProfileCreationRequest(request);
        profileRequest.setUserId(newUserId);

        try {
            profileClient.createProfile(profileRequest);
        } catch (feign.FeignException e) {
            throw new AppException(ErrorCode.PROFILE_CREATION_FAILED);
        }

        return userMapper.toUserResponse(user);
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasAuthority('APPROVE_POST')")
    public List<User> getUsers() {
        log.info("In method get Users");
        return userRepository.findAll();
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(Long id) {
        log.info("In method get user by id");
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserResponse updateUser(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
