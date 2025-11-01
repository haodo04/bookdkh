package com.haodk.identity.mapper;

import org.mapstruct.Mapper;

import com.haodk.identity.dto.request.PermissionRequest;
import com.haodk.identity.dto.response.PermissionResponse;
import com.haodk.identity.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
