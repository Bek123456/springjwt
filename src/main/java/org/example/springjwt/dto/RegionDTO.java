package org.example.springjwt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionDTO {
    private Integer id;
    private String orderNumber;
    private String nameUz;
    private String nameEng;
    private String nameRu;

    private Boolean visible=true;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
