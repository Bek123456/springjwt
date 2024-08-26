package org.example.springjwt.service;

import org.example.springjwt.dto.CategoryDTO;
import org.example.springjwt.entity.CategoryEntity;
import org.example.springjwt.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String created(CategoryDTO dto) {
        CategoryEntity save = categoryRepository.save(toDo(dto));
        return "created category";
    }

}
