package org.example.springjwt.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "region_entity")
public class RegionEntity {
    //id,order_number,name_uz, name_ru, name_en,visible,created_date
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "order_number",nullable = false)
    private String orderNumber;
    @Column(name = "name_uz",nullable = false)
    private String nameUz;
    @Column(name = "name_eng",nullable = false)
    private String nameEng;
    @Column(name = "name_ru",nullable = false)
    private String nameRu;
    @Column(name = "visible",nullable = false)
    private Boolean visible=true;
    @Column(name = "created_date",nullable = false)
    private LocalDateTime createdDate=LocalDateTime.now();
    @Column(name = "update_date")
    private LocalDateTime updateDate;
}
