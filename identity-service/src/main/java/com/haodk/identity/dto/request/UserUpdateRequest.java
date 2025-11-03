package com.haodk.identity.dto.request;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String username;

    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;

    String firstName;

    String lastName;

    LocalDate dob;

    List<String> roles;
}
