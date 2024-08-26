package org.example.springjwt.controller;

import org.example.springjwt.dto.CategoryDTO;
import org.example.springjwt.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {
//  1. Create  (ADMIN)
//        (order_number,name_uz, name_ru, name_en)
//  2. Update by id (ADMIN)
//        (order_number,name_uz, name_ru, name_en)
//  3. Delete by id (ADMIN)
//  4. Get List (ADMIN) - order by order_number
//            (id,order_number,name_uz, name_ru, name_en,visible,created_date)
//  5. Get By Lang (Language keladi shu language dagi name larini berib yuboramiz)
//        (id,order_number,name) (name ga tegishli name_... dagi qiymat qo'yiladi.)


    @Autowired
    private CategoryService categoryService;

    @PostMapping("/created")
    public ResponseEntity<String>create(@RequestBody CategoryDTO dto){
        return ResponseEntity.ok(categoryService.created(dto));
    }
}
