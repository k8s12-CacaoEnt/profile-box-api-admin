package com.goorm.profileboxapiadmin.service;
import com.goorm.profileboxcomm.entity.Member;
import com.goorm.profileboxcomm.entity.Notice;
import com.goorm.profileboxcomm.exception.ExceptionEnum;
import com.goorm.profileboxcomm.repository.MemberRepository;
import com.goorm.profileboxcomm.repository.NoticeRepository;
import com.goorm.profileboxcomm.exception.ApiException;
import com.goorm.profileboxcomm.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;

    public List<Notice> getAllNoitce(){
        return noticeRepository.findAll();
    }

    public Notice getSpecificNotice(Long noticeId){
        Notice notice = noticeRepository.findByNoticeId(noticeId)
                .orElseThrow(() -> new ApiException(ExceptionEnum.NOTICE_NOT_FOUND));
        return notice;
    }

    public Notice registerNotice(Notice entity){
        validate();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails)auth.getPrincipal();
        Member member = principalDetails.getMemberEntity();
        System.out.println("공지 등록하는 member: " + member);
        entity.setMember(member);
        return noticeRepository.save(entity);
    }

    public Notice updateNotice(Notice entity, Long noticeId){
        validate();
        Notice origin = noticeRepository.findByNoticeId(noticeId)
                .orElseThrow(() -> new ApiException(ExceptionEnum.NOTICE_NOT_FOUND));
        //createDt, modifyDt는 Entity PrePersist, PreUpdate 에 의해 일괄 처리

        if()

        return getSpecificNotice(entity.getNoticeId());
    }

    public List<Notice> deleteNotice(Long noticeId){
        validate();
        try{
            noticeRepository.deleteByNoticeId(noticeId);
            return getAllNoitce();
        }catch (Exception e){
            throw new RuntimeException("delete err: " + e.getMessage());
        }
    }

    private void validate(){
        // 구현해야함
    }
}
