package org.example.springjwt.dto.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.example.springjwt.enums.ProfileRole;
import org.example.springjwt.enums.ProfileStatus;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateProfileDTO {
    private String name;
    private String surname;
    private String phone;
    private String password;
    private ProfileRole role;
    private Boolean visible=true;
    private String photoId;
    private ProfileStatus status;
    private LocalDateTime updateDate;
}
