package org.example.springjwt.service;

import org.example.springjwt.dto.ArticleTypeDTO;
import org.example.springjwt.dto.RegionDTO;


import org.example.springjwt.dto.bydto.RegionGetByLangDTO;
import org.example.springjwt.entity.ArticleTypeEntity;
import org.example.springjwt.entity.RegionEntity;
import org.example.springjwt.enums.Language;
import org.example.springjwt.exp.AppBadException;
import org.example.springjwt.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public ArticleTypeEntity doTo(ArticleTypeDTO dto) {
        ArticleTypeEntity entity = new ArticleTypeEntity();
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEng(dto.getNameEng());
        entity.setOrderNumber(dto.getOrderNumber());
        return entity;
    }
    private RegionDTO toDTO(RegionEntity entity) {
        RegionDTO dto = new RegionDTO();
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setVisible(entity.getVisible());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEng(entity.getNameEng());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setUpdateDate(entity.getUpdateDate());
        return dto;
    }
    public String created(RegionDTO dto) {
        RegionEntity entity=new RegionEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEng(dto.getNameEng());
        regionRepository.save(entity);
        return "created region";
    }

    public RegionDTO update(Integer id, RegionDTO dto) {
        Integer update = regionRepository.update(id, dto.getNameUz(), dto.getNameEng(), dto.getNameRu(), LocalDateTime.now());
        if (update == 1) {
            return dto;
        }
        return null;
    }

    public String delete(Integer id) {
        Optional<RegionEntity> optionalArticleType = regionRepository.findById(id);
        if (optionalArticleType.isEmpty()) {
            throw new AppBadException("not found articleType");
        }
        RegionEntity entity = optionalArticleType.get();
        Integer byId = regionRepository.deleteAllById(id);
        if (byId == 1) {
            return "deleted ArticleType";
        }
        throw new AppBadException("not found deleted");
    }

    public PageImpl<RegionDTO> getByList(Integer size, Integer page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<RegionEntity> entityPage = regionRepository.findByNamePattern(pageable);
        List<RegionEntity> content = entityPage.getContent();
        long totalElements = entityPage.getTotalElements();

        List<RegionDTO> dtoList = new LinkedList<>();

        for (RegionEntity entity : entityPage) {
            dtoList.add(toDTO(entity));
        }
        return new PageImpl<>(dtoList, pageable, totalElements);

    }


    public List<RegionGetByLangDTO> getByLang(Language language) {
        List<RegionEntity> allArticleType = regionRepository.findAllArticleType();
        List<RegionGetByLangDTO>dtoList=new ArrayList<>();
        for (RegionEntity entity:allArticleType){
            RegionGetByLangDTO dto=new RegionGetByLangDTO();
            dto.setId(entity.getId());
            dto.setOrderNumber(entity.getOrderNumber());
            switch (language){
                case uz:
                    dto.setName(entity.getNameUz());
                    break;
                case eng:
                    dto.setName(entity.getNameEng());
                    break;
                case rus:
                    dto.setName(entity.getNameRu());
                    break;
            }
            dtoList.add(dto);
        }
        return dtoList;
    }


}
