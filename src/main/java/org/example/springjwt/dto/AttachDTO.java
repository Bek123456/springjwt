package org.example.springjwt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachDTO {
    private UUID id;
    private String originalName;
    private String path;
    private Long size;
    private String extension;
    private LocalDateTime createdDate=LocalDateTime.now();
}
