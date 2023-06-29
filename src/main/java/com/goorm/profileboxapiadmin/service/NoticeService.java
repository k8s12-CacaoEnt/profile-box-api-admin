package com.goorm.profileboxapiadmin.service;
import com.goorm.profileboxcomm.entity.Member;
import com.goorm.profileboxcomm.entity.Notice;
import com.goorm.profileboxcomm.exception.ExceptionEnum;
import com.goorm.profileboxcomm.repository.MemberRepository;
import com.goorm.profileboxcomm.repository.NoticeRepository;
import com.goorm.profileboxcomm.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        return noticeRepository.findByNoticeId(noticeId);
    }

    public void registerNotice(Notice entity, Long memberId){
        validate();
        Member member = memberRepository.findMemberByMemberId(memberId)
                .orElseThrow(() -> new ApiException(ExceptionEnum.MEMBER_NOT_FOUND));
        entity.setMember(member);
        noticeRepository.save(entity);
    }

    public Notice updateNotice(Notice entity){
        validate();
        final Notice origin = noticeRepository.findByNoticeId(entity.getNoticeId());
        origin.setNoticeTitle(entity.getNoticeTitle());
        origin.getNoticeContent();
        origin.setFilmoType(entity.getFilmoType());
        origin.setFilmoName(entity.getFilmoName());
        origin.setFilmoRole(entity.getFilmoRole());
        origin.setFilmingStartPeriod(entity.getFilmingStartPeriod());
        origin.setFilmingEndPeriod(entity.getFilmingEndPeriod());
        //createDt, modifyDt는 Entity PrePersist, PreUpdate 에 의해 일괄 처리
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
