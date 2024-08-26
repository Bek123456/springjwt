package org.example.springjwt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleTypeDTO {
    private Integer id;
    private String orderNumber;
    private String nameUz;
    private String nameEng;
    private String nameRu;
    private LocalDateTime updateDate;
    private LocalDateTime createdDate;
    private Boolean visible;
}
