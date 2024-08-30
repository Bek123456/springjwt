package org.example.springjwt.service;

import org.example.springjwt.dto.AttachDTO;
import org.example.springjwt.dto.CategoryDTO;
import org.example.springjwt.entity.AttachEntity;
import org.example.springjwt.entity.CategoryEntity;
import org.example.springjwt.enums.Language;
import org.example.springjwt.exp.AppBadException;
import org.example.springjwt.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AttachService {
    @Autowired
    private AttachRepository attachRepository;
    @Value("http://localhost:8080")
    private String serverUrl;

    public String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; // 2024/4/23
    }
    private String getExtension(String originalFilename) {
        int lastIndex = originalFilename.lastIndexOf(".");
        return originalFilename.substring(lastIndex + 1);
    }

    public AttachDTO toDTO(AttachEntity entity) {
        AttachDTO dto = new AttachDTO();
        dto.setId(entity.getId());
        dto.setPath(serverUrl + "/attach/open_general/" + entity.getId() + "." + entity.getExtension());
        return dto;
    }
    public AttachDTO save(MultipartFile file) {
        try {
            String pathFolder=getYmDString();//2022/05/11
            File folder=new File("uploads/"+pathFolder);
            if (!folder.exists()){
                folder.mkdirs();
            }
            String key= UUID.randomUUID().toString();// // dasdasd-dasdasda-asdasda-asdasd
            String extension=getExtension(file.getOriginalFilename());// // mp3/jpg/npg/mp4
            byte[] bytes = file.getBytes();
            Path path = Paths.get("uploads/" + pathFolder + "/" + key + "." + extension);
            Files.write(path, bytes);
            AttachEntity entity = new AttachEntity();
            entity.setSize(file.getSize());
            entity.setExtension(extension);
            entity.setOriginalName(file.getOriginalFilename());
            entity.setCreatedDate(LocalDateTime.now());
            entity.setId(key);
            entity.setPath(pathFolder);

            attachRepository.save(entity);

            return toDTO(entity);
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }


    public byte[] loadImage(String id, Language language) {
       return getBytes(id,language);
    }
    private byte[] getBytes(String attachId, Language language) {
        String id=attachId.substring(0,attachId.lastIndexOf("."));
        AttachEntity entity=get(id,language);
        byte[]data;
       try {
           Path file = Paths.get("uploads/" + entity.getPath() + "/" + attachId);
           data = Files.readAllBytes(file);
           return data;
       }catch (IOException e){
           e.printStackTrace();
       }
       return new byte[0];
    }
   private AttachEntity get(String id,Language language){
       Optional<AttachEntity> byId = attachRepository.findById(id);
       if (byId.isEmpty()){
           throw new AppBadException("NOT FOUND"+language);
       }
       return byId.get();

   }

    public byte[] openGeneral(String id, Language language) {
       return getBytes(id,language);
    }

    public ResponseEntity download(String filename, Language language) {
        String id = filename.substring(0, filename.lastIndexOf("."));
        AttachEntity entity = get(id,language);
        try {
            Path file = Paths.get("uploads/" + entity.getPath() + "/" + entity.getId() + "." + entity.getExtension());
            Resource resource = new UrlResource(file.toUri());
             if (resource.exists()|| resource.isReadable()){
                 return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                         "attachment; filename=\"" + entity.getOriginalName() + "\"").
                         body(resource);
             }
             else {
                 throw new RuntimeException("cloud not red");
             }
        }catch (MalformedURLException e){
             throw new AppBadException(e.getMessage());
        }

    }


    public PageImpl<AttachDTO> getPagination(Integer page, Integer size) {

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<AttachEntity> entityPage = attachRepository.findByPage(pageable);
        List<AttachEntity> content = entityPage.getContent();
        List<AttachDTO>dtoList=new ArrayList<>();
        long totalElements = entityPage.getTotalElements();
        for (AttachEntity entity:content){
            dtoList.add(toDTO(entity));
        }
        return new PageImpl<>(dtoList,pageable,totalElements);
    }

    public String deleted(String id) {
        AttachEntity attachEntity = get(id, Language.uz);
        new File(String.valueOf(Path.of("uploads/" + attachEntity.getPath() + "/" + attachEntity.getId() + "." + attachEntity.getExtension()))).deleteOnExit();
        Integer byId = attachRepository.deleteAllById(id);
        if (byId==1){
            return "deleted attach";
        }
        throw new AppBadException("deleted not found");
    }
}
