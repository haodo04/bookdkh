package com.haodk.identity.mapper;

import com.haodk.identity.dto.request.ProfileCreationRequest;
import com.haodk.identity.dto.request.UserCreationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mapping(target = "userId", ignore = true)
    ProfileCreationRequest toProfileCreationRequest(UserCreationRequest request);
}
