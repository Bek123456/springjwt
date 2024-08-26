package org.example.springjwt.service;

import org.example.springjwt.dto.CategoryDTO;
import org.example.springjwt.dto.bydto.CategoryByLangDTO;
import org.example.springjwt.entity.CategoryEntity;
import org.example.springjwt.entity.RegionEntity;
import org.example.springjwt.enums.Language;
import org.example.springjwt.exp.AppBadException;
import org.example.springjwt.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    private CategoryEntity toDo(CategoryDTO dto) {
        CategoryEntity entity=new CategoryEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameEng(dto.getNameEng());
        entity.setNameRu(dto.getNameRu());
        entity.setNameUz(dto.getNameUz());
        return entity;
    }
    private CategoryDTO doTo(CategoryEntity entity){
        CategoryDTO dto=new CategoryDTO();
        dto.setId(entity.getId());
        dto.setUpdateDate(entity.getUpdateDate());
        dto.setCreateDate(entity.getCreatedDate());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEng(entity.getNameEng());
        dto.setOrderNumber(entity.getOrderNumber());
        return dto;
    }
    public String created(CategoryDTO dto) {
        CategoryEntity save = categoryRepository.save(toDo(dto));
        return "created category";
    }


    public String edit(CategoryDTO dto, Integer id) {
        Integer update = categoryRepository.update(dto.getNameEng(),
                                                   dto.getNameRu(),
                                                   dto.getNameUz(),
                                                   dto.getOrderNumber(),
                                                   LocalDateTime.now(), id);
        if (update!=1){
            throw new AppBadException("not update");
        }
        return "edit category";
    }

    public String deleted(Integer id) {
        Integer byId = categoryRepository.deleteAllById(id);
        if (byId!=1){
            throw new AppBadException("not category");
        }
        return "delete category";
    }

    public PageImpl<CategoryDTO> getByList(Integer page, Integer size) {

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<CategoryEntity> entityPage = categoryRepository.findByPage(pageable);
        List<CategoryEntity> content = entityPage.getContent();
        List<CategoryDTO>dtoList=new ArrayList<>();
        long totalElements = entityPage.getTotalElements();
        for (CategoryEntity entity:content){
            dtoList.add(doTo(entity));
        }
        return new PageImpl<>(dtoList,pageable,totalElements);
    }

    public List<CategoryByLangDTO> getByLang(Language language) {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        List<CategoryByLangDTO>categoryByLangDTOS=new ArrayList<>();
        for (CategoryEntity entity:categoryEntityList){
            CategoryByLangDTO dto=new CategoryByLangDTO();
            dto.setId(entity.getId());
            dto.setOrderName(entity.getOrderNumber());
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
            categoryByLangDTOS.add(dto);
        }
        return categoryByLangDTOS;
    }
}
