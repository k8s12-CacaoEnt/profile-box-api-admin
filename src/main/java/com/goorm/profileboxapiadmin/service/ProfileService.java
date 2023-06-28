package com.goorm.profileboxapiadmin.service;

import com.goorm.profileboxcomm.entity.Profile;
import com.goorm.profileboxcomm.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    @Autowired
    ProfileRepository profileRepository;

    public List<Profile> getAllProfiles(){
        return profileRepository.findAll();
    }

    public Profile getSpecificProfile(Long profileId){
        return profileRepository.findProfileByProfileId(profileId);
    }

}
