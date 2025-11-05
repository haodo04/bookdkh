package com.haodk.profile.dto.request;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileCreationRequest {
    String id;
    Long userId;
    String password;
    String firstName;
    String lastName;
    LocalDate dob;
    String city;
}
