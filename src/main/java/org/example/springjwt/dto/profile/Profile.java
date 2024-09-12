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
public class Profile {
    protected Integer id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private ProfileStatus status;
    private ProfileRole role;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Boolean visible;
    private String jwt;
}
