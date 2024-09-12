package org.example.springjwt.service;

import org.example.springjwt.dto.PaginationResultDTO;
import org.example.springjwt.dto.ProfileDTO;
import org.example.springjwt.dto.profile.FilterProfileDTO;
import org.example.springjwt.dto.profile.Profile;
import org.example.springjwt.dto.profile.UpdateProfileDTO;
import org.example.springjwt.entity.ProfileEntity;
import org.example.springjwt.enums.ProfileRole;
import org.example.springjwt.exp.AppBadException;
import org.example.springjwt.repository.CustomRepository;
import org.example.springjwt.repository.ProfileRepository;
import org.example.springjwt.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private CustomRepository customRepository;
    public ProfileEntity toDo(ProfileDTO dto){
        ProfileEntity entity=new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
        entity.setRole(dto.getRole());
        entity.setPhotoId(dto.getPhotoId());
        return entity;
    }
    public ProfileDTO doTo(ProfileEntity entity){
        ProfileDTO dto=new ProfileDTO();
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setPhone(entity.getPhone());
        dto.setRole(entity.getRole());
        dto.setPhotoId(entity.getPhotoId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdateDate());
        return dto;
    }
    public String created(ProfileDTO dto) {
        profileRepository.save(toDo(dto));
        return "created profile";
    }


    public String updateAdmin(UpdateProfileDTO dto,Integer id) {
        Optional<ProfileEntity> byId = profileRepository.findById(id);
        LocalDateTime updateDate=LocalDateTime.now();
        if (byId.isEmpty()){
            throw new AppBadException("not found profile");
        }
        ProfileEntity entity = byId.get();

        if (dto.getVisible()!=null){
         entity.setVisible(dto.getVisible());
         }
        if(dto.getName()!=null){
          entity.setName(dto.getName());
        }
        if (dto.getSurname()!=null){
            entity.setSurname(dto.getSurname());
        }
        if (dto.getPhone()!=null){
            entity.setPhone(dto.getPhone());
        }
        if (dto.getPhotoId()!=null){
            entity.setPhotoId(dto.getPhotoId());
        }
        if (dto.getRole()!=null&&!dto.getRole().equals(ProfileRole.ROLE_ADMIN)){
            entity.setRole(dto.getRole());
        }
        if (dto.getStatus()!=null){
            entity.setStatus(dto.getStatus());
        }
        if (dto.getPassword()!=null){
            entity.setPassword(MD5Util.encode(dto.getPassword()));
        }
        entity.setUpdateDate(updateDate);
        profileRepository.save(entity);
        return "update profile";
    }

    public PageImpl<ProfileDTO> getPagination(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<ProfileEntity> entityPage = profileRepository.findByNamePattern(pageable);
        List<ProfileEntity> content = entityPage.getContent();
        long totalElements = entityPage.getTotalElements();
        List<ProfileDTO>dtoList=new ArrayList<>();
        for (ProfileEntity entity:content){
           dtoList.add(doTo(entity));
        }
        return new PageImpl<>(dtoList,pageable,totalElements);
    }

    public String deleted(Integer id) {
        Integer byId = profileRepository.deleteAllById(id);
        if (byId.equals(1)){
            return "deleted profile";
        }
        throw new AppBadException("not found profile not deleted");
    }

    public String updatePhoto(String photoId, Integer id) {
        Integer byId = profileRepository.updateByIdAndPhoto(id, photoId);
        System.out.println(byId);
        if (byId.equals(1)){
            return "update photo Profile";
        }
       throw new AppBadException("not found profile update");
    }

    public PageImpl<Profile> filter(FilterProfileDTO filter, Pageable pageable) {
        PaginationResultDTO<ProfileEntity>resultFilter=customRepository.profileFilter(filter,pageable);

      List<Profile>profileList=new ArrayList<>();
      for (ProfileEntity entity:resultFilter.getList()){
          profileList.add(toProfile(entity));
      }
      return new PageImpl<>(profileList,pageable, resultFilter.getTotalSize());
    }
    public Profile toProfile(ProfileEntity entity){
        Profile dto = new Profile();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
