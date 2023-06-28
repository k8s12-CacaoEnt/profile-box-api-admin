package com.goorm.profileboxapiadmin.controller;

import com.goorm.profileboxapiadmin.service.ProfileService;
import com.goorm.profileboxcomm.dto.profile.ProfileDTO;
import com.goorm.profileboxcomm.entity.Profile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("v1/profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @GetMapping("/list")
    public ResponseEntity<?> getProfileList(){
        try{
            List<Profile> list = profileService.getAllProfiles();
            return ResponseEntity.ok().body(list);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/list/{profileId}")
    public ResponseEntity<?> openProfile(@PathVariable("profileId") Long profileId){
        try{
            Profile profile = profileService.getSpecificProfile(profileId);
            ProfileDTO profileDTO = Profile.toDTO(profile);
            return ResponseEntity.ok().body(profileDTO);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
