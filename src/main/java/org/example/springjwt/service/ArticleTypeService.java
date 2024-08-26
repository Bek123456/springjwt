package org.example.springjwt.service;



import org.example.springjwt.dto.ArticleTypeDTO;
import org.example.springjwt.dto.bydto.ArticleTypeByLangDTO;
import org.example.springjwt.entity.ArticleTypeEntity;
import org.example.springjwt.enums.Language;
import org.example.springjwt.exp.AppBadException;
import org.example.springjwt.repository.ArticleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;

    public ArticleTypeEntity doTo(ArticleTypeDTO dto) {
        ArticleTypeEntity entity = new ArticleTypeEntity();
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEng(dto.getNameEng());
        entity.setOrderNumber(dto.getOrderNumber());
        return entity;
    }
    private ArticleTypeDTO toDTO(ArticleTypeEntity entity) {
        ArticleTypeDTO dto = new ArticleTypeDTO();
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setVisible(entity.getVisible());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEng(entity.getNameEng());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setUpdateDate(entity.getUpdateDate());
        return dto;
    }
    public String created(ArticleTypeDTO dto) {
        ArticleTypeEntity save = articleTypeRepository.save(doTo(dto));
        return "created ArticleType";
    }

    public ArticleTypeDTO update(Integer id, ArticleTypeDTO dto) {
        Integer update = articleTypeRepository.update(id, dto.getNameUz(), dto.getNameEng(), dto.getNameRu(), LocalDateTime.now());
        if (update == 1) {
            return dto;
        }
        return null;
    }

    public String delete(Integer id) {
        Optional<ArticleTypeEntity> optionalArticleType = articleTypeRepository.findById(id);
        if (optionalArticleType.isEmpty()) {
            throw new AppBadException("not found articleType");
        }
        ArticleTypeEntity entity = optionalArticleType.get();
        Integer byId = articleTypeRepository.deleteAllById(id);
        if (byId == 1) {
            return "deleted ArticleType";
        }
        throw new AppBadException("not found deleted");
    }

    public PageImpl<ArticleTypeDTO> getByList(Integer size, Integer page) {

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<ArticleTypeEntity> entityPage = articleTypeRepository.findByNamePattern(pageable);
        List<ArticleTypeEntity> content = entityPage.getContent();
        long totalElements = entityPage.getTotalElements();

        List<ArticleTypeDTO> dtoList = new LinkedList<>();
        for (ArticleTypeEntity entity : entityPage) {
            dtoList.add(toDTO(entity));
        }
            return new PageImpl<>(dtoList, pageable, totalElements);
    }


    public List<ArticleTypeByLangDTO> getByLang(Language language) {
        //(id,key,name)
        List<ArticleTypeEntity> allArticleType = articleTypeRepository.findAllArticleType();
        List<ArticleTypeByLangDTO>dtoList=new ArrayList<>();
        for (ArticleTypeEntity entity:allArticleType){
            ArticleTypeByLangDTO dto=new ArticleTypeByLangDTO();
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
