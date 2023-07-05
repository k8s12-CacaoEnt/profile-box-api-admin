package com.goorm.profileboxapiadmin.service;

import com.goorm.profileboxcomm.dto.member.MemberDTO;
import com.goorm.profileboxcomm.entity.Member;
import com.goorm.profileboxcomm.exception.ApiException;
import com.goorm.profileboxcomm.exception.ExceptionEnum;
import com.goorm.profileboxcomm.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @Transactional
    public List<Member> testListAllMember(){
        return memberRepository.findAll();
    }

    @Transactional
    public MemberDTO saveMember(MemberDTO dto) {
        dto.setMemberPassword(passwordEncoder.encode(dto.getMemberPassword()));
        Member entity = MemberDTO.toEntity(dto);
        MemberDTO memberDTO = Member.toDTO(memberRepository.save(entity));
//        validateDuplicateMember(entity);
        return memberDTO;
    }

    @Transactional
    public MemberDTO findLoginMemberByEmail(String email) {
        Member member =  memberRepository.findMemberByMemberEmail(email)
                .orElseThrow(() -> new ApiException(ExceptionEnum.LOGIN_FAILED));

        MemberDTO memberDTO = Member.toDTO(member);
        return memberDTO;
    }

    @Transactional
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
