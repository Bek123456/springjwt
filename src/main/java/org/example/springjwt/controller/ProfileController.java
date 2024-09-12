package org.example.springjwt.controller;

import org.example.springjwt.dto.ProfileDTO;
import org.example.springjwt.dto.profile.FilterProfileDTO;
import org.example.springjwt.dto.profile.Profile;
import org.example.springjwt.dto.profile.UpdateProfileDTO;
import org.example.springjwt.enums.ProfileRole;
import org.example.springjwt.exp.AppBadException;
import org.example.springjwt.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {
//    1. Create profile (ADMIN) (can create MODERATOR,PUBLISHER))
//     (name,surname,email,phone,password,status,role)
//    2. Update Profile (ADMIN)
//    3. Update Profile Detail (ANY) (Profile updates own details)
//    4. Profile List (ADMIN) (Pagination)
//    5. Delete Profile By Id (ADMIN)
//    6. Update Photo (ANY) (Murojat qilgan odamni rasmini upda qilish)   (remove old image)
//    photo_id
//    7. Filter (name,surname,phone,role,created_date_from,created_date_to)
    @Autowired
    private ProfileService profileService;
    @PostMapping("/created")
    public ResponseEntity<String>created(@RequestBody ProfileDTO dto){
        if (dto.getRole().equals(ProfileRole.ROLE_ADMIN)){
            throw new AppBadException("not created admin");
        }
        return ResponseEntity.ok(profileService.created(dto));
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<String>updateAdmin(@RequestBody UpdateProfileDTO dto,
                                             @PathVariable Integer id){
        return ResponseEntity.ok(profileService.updateAdmin(dto,id));
    }
    @PutMapping("/editany/{id}")
    public ResponseEntity<String>updateAny(@RequestBody UpdateProfileDTO dto,
                                             @PathVariable Integer id){
        return ResponseEntity.ok(profileService.updateAdmin(dto,id));
    }
    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<ProfileDTO>>getPagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                             @RequestParam(value = "size", defaultValue = "2") Integer size){
        return ResponseEntity.ok(profileService.getPagination(page,size));
    }
    @DeleteMapping("/deleted/{id}")
    public ResponseEntity<String> deleted(@PathVariable Integer id){
        return ResponseEntity.ok(profileService.deleted(id));
    }
    @PutMapping("/updatePhoto")
    public ResponseEntity<String>updatePhoto(@RequestParam(value = "photoId") String photoId,
                                             @RequestParam(value = "id")Integer id){
        return ResponseEntity.ok(profileService.updatePhoto(photoId,id));
    }
    @GetMapping("/filter")
    public ResponseEntity<PageImpl<Profile>>filter(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                   @RequestParam(value = "size", defaultValue = "2") Integer size,
                                                   @RequestBody FilterProfileDTO filter) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createdDate");;
        return ResponseEntity.ok(profileService.filter(filter, pageable));
    }




}
