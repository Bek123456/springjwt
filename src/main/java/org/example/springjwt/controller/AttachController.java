package org.example.springjwt.controller;

import org.example.springjwt.dto.AttachDTO;
import org.example.springjwt.entity.AttachEntity;
import org.example.springjwt.enums.Language;
import org.example.springjwt.repository.AttachRepository;
import org.example.springjwt.service.AttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/attach")
public class AttachController {

//    1. Upload  (ANY)
//    2. Open (by id)
//    3. Open general (by id)
//    4. Download (by id  with origin name)
//    5. Pagination (ADMIN)
//    6. Delete by id (delete from system and table) (ADMIN)

    @Autowired
    private AttachService attachService;
    @Autowired
    private AttachRepository attachRepository;
    @PostMapping("/upload")
    public ResponseEntity<AttachDTO>upload(@RequestParam("file")MultipartFile file){
        return ResponseEntity.ok(attachService.save(file));
    }
    @GetMapping("/open/{id}")
    public byte[] open(@PathVariable String id,
                       @RequestHeader(value = "Accept-Language",defaultValue = "uz")Language language){
     if (id!=null&&!id.isEmpty()){
        try {
            return attachService.loadImage(id,language);
         }catch (Exception e){

            return new byte[0];
        }

     }
     return null;
    }
    @GetMapping("/openGeneral/{id}")
    public byte[] openGeneral(@PathVariable String id,
                              @RequestHeader(value = "Accept-Language",defaultValue = "uz")
                              Language language){
        if (id!=null&&!id.isEmpty()){
            try {
                return attachService.openGeneral(id,language);
            }catch (Exception e){

                return new byte[0];
            }

        }
        return null;

    }
    @GetMapping("/download/{filename}")
    public ResponseEntity<?>download(@PathVariable String filename,// davomi bor================
                                     @RequestHeader(value = "Accept-Language",
                                             defaultValue = "uz")Language language){
        return ResponseEntity.ok(attachService.download(filename,language));
    }

    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<AttachDTO>>getPagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                            @RequestParam(value = "size", defaultValue = "2") Integer size){

        return ResponseEntity.ok(attachService.getPagination(page,size));
    }
    @DeleteMapping("/deleted/{id}")
    public ResponseEntity<String>deleted(@PathVariable String id){
        return ResponseEntity.ok(attachService.deleted(id));
    }
}
