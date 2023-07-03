package com.goorm.profileboxapiadmin.service;

import com.goorm.profileboxcomm.entity.Member;
import com.goorm.profileboxcomm.repository.MemberRepository;
import com.goorm.profileboxcomm.dto.member.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    // test용 멤머 모두 출력
    public List<Member> testListAllMember(){
        return memberRepository.findAll();
    }

    public Member saveMember(MemberDTO dto) {
        dto.setMemberPassword(passwordEncoder.encode(dto.getMemberPassword()));
        Member entity = MemberDTO.toEntity(dto);
        validateDuplicateMember(entity);
        return memberRepository.save(entity);
    }

    public Member findLoginMember(MemberDTO dto) {
//        Member entity = MemberDTO.toEntity(dto);
        return memberRepository.findMemberByMemberEmailAndMemberPassword(dto.getMemberEmail(), dto.getMemberPassword());
    }

    public void deleteMember(Long id){
        memberRepository.deleteById(id);
    }

    private void validateDuplicateMember(Member entity){
        if(entity == null ){
            log.warn("entity is null!");
            throw new RuntimeException("Invalid arguments!");
        }
        String email = entity.getMemberEmail();
        if(memberRepository.existsByMemberEmail(email)){
            log.warn("Email already exist!");
            throw new RuntimeException("Email already exist!");
        }
    }

}
