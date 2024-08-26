package org.example.springjwt.controller;

import org.example.springjwt.dto.RegionDTO;

import org.example.springjwt.dto.bydto.RegionGetByLangDTO;
import org.example.springjwt.enums.Language;
import org.example.springjwt.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/region")
public class RegionController {


//    1. Create  (ADMIN)
//        (order_number,name_uz, name_ru, name_en)
//    2. Update by id (ADMIN)
//        (key,name_uz, name_ru, name_en)
//    3. Delete by id (ADMIN)
//    4. Get List (ADMIN)
//        (id,key,name_uz, name_ru, name_en,visible,created_date)
//    5. Get By Lang (Language keladi shu language dagi name larini berib yuboramiz)
//        (id,key,name) (name ga tegishli name_.. dagi qiymat qo'yiladi.) (visible true)

    @Autowired
    private RegionService regionService;

    //CREATE
    @PostMapping("/created")
    public ResponseEntity<String> created(@RequestBody RegionDTO dto){
        return ResponseEntity.ok(regionService.created(dto));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<RegionDTO>update(@PathVariable Integer id, @RequestBody RegionDTO dto){
        return ResponseEntity.ok(regionService.update(id,dto));
    }
    @DeleteMapping("/deleted/{id}")
    public ResponseEntity<String>delete(@PathVariable Integer id){
        return ResponseEntity.ok(regionService.delete(id));
    }

    @GetMapping("/getByList")
    public ResponseEntity<PageImpl<RegionDTO>>getByList(Integer size, Integer page){
        return ResponseEntity.ok(regionService.getByList(size,page));
    }

    @GetMapping("/getByLang")
    public ResponseEntity<List<RegionGetByLangDTO>>getByLang(@RequestHeader(value = "Accept-Language",defaultValue = "UZ")
                                                              Language language){
        return ResponseEntity.ok(regionService.getByLang(language));
    }


}
