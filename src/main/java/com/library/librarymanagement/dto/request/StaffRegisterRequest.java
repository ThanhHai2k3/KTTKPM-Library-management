package com.library.librarymanagement.dto.request;

import com.library.librarymanagement.enums.RoleName;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffRegisterRequest {

    private String username;
    private String password;
    private String fullName;
    private String position;
}
