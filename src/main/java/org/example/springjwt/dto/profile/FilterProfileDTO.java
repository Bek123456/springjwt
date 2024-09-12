package org.example.springjwt.dto.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.example.springjwt.enums.ProfileRole;

import java.time.LocalDate;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilterProfileDTO {
    private Integer id;
    private String name;
    private String surname;
    private ProfileRole role;
    private LocalDate fromDate;
    private LocalDate toDate;
}
