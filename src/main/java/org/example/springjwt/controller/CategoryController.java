package org.example.springjwt.controller;

import org.example.springjwt.dto.CategoryDTO;
import org.example.springjwt.dto.bydto.CategoryByLangDTO;
import org.example.springjwt.enums.Language;
import org.example.springjwt.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/edit/{id}")
    public ResponseEntity<String>edit(@PathVariable Integer id,
                                      @RequestBody CategoryDTO dto){
        return ResponseEntity.ok(categoryService.edit(dto,id));
    }
    @DeleteMapping("/deleted/{id}")
    public ResponseEntity<String>deleted(@PathVariable Integer id){
        return ResponseEntity.ok(categoryService.deleted(id));
    }
    @GetMapping("/getByList")
    public ResponseEntity<PageImpl<CategoryDTO>>getByList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                          @RequestParam(value = "size", defaultValue = "2") Integer size){
        return ResponseEntity.ok(categoryService.getByList(page,size));
    }
    @GetMapping("/getByLang")
    public ResponseEntity<List<CategoryByLangDTO>>getByLang(@RequestHeader(value = "Accept-Language",defaultValue = "uz")
                                                                Language language){
        return ResponseEntity.ok(categoryService.getByLang(language));
    }
}
