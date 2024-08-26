package org.example.springjwt.controller;



import org.example.springjwt.dto.ArticleTypeDTO;
import org.example.springjwt.dto.bydto.ArticleTypeByLangDTO;
import org.example.springjwt.enums.Language;
import org.example.springjwt.service.ArticleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articleType")
public class ArticleTypeController {

//     1. Create  (ADMIN)
//        (order_number,name_uz, name_ru, name_en)
//            2. Update by id (ADMIN)
//        (order_number,name_uz, name_ru, name_en)
//            3. Delete by id (ADMIN)
//       4. Get List (ADMIN) (Pagination)
//            (id,key,name_uz, name_ru, name_en,visible,created_date)
//            5. Get By Lang (Language keladi shu language dagi name larini berib yuboramiz)
//         (id,key,name) (name ga tegishli name_.. dagi qiymat qo'yiladi.)

    @Autowired
    private ArticleTypeService articleTypeService;

    //CREATE
    @PostMapping("/created")
    public ResponseEntity<String>created(@RequestBody ArticleTypeDTO dto){
        return ResponseEntity.ok(articleTypeService.created(dto));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ArticleTypeDTO>update(@PathVariable Integer id,@RequestBody ArticleTypeDTO dto){
        return ResponseEntity.ok(articleTypeService.update(id,dto));
    }
    @DeleteMapping("/deleted/{id}")
    public ResponseEntity<String>delete(@PathVariable Integer id){
        return ResponseEntity.ok(articleTypeService.delete(id));
    }

    @GetMapping("/getByList")
    public ResponseEntity<PageImpl<ArticleTypeDTO>>getByList(Integer size,Integer page){
          return ResponseEntity.ok(articleTypeService.getByList(size,page));
    }
   @GetMapping("/getByLang")
    public ResponseEntity<List<ArticleTypeByLangDTO>>getByLang(@RequestHeader(value = "Accept-Language",defaultValue = "UZ")
                                                            Language language){
        return ResponseEntity.ok(articleTypeService.getByLang(language));
   }

}
