package com.goorm.profileboxapiadmin.controller;

import com.goorm.profileboxapiadmin.service.NoticeService;
import com.goorm.profileboxcomm.dto.notice.NoticeDTO;
import com.goorm.profileboxcomm.entity.Notice;
import com.goorm.profileboxcomm.exception.ApiException;
import com.goorm.profileboxcomm.exception.ExceptionEnum;
import com.goorm.profileboxcomm.response.ApiResult;
import com.goorm.profileboxcomm.response.ApiResultType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/notice")
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    // 공지 전체 조회
    @GetMapping("/list")
    public ApiResult getNoticeList(){
        try {
            List<Notice> list = noticeService.getAllNoitce();
            return ApiResult.getResult(ApiResultType.SUCCESS, "공지 리스트 조회", list);
        }catch (Exception e){
            throw new ApiException(ExceptionEnum.RUNTIME_EXCEPTION);
        }
    }

    // 특정 공지 열람
    @GetMapping("/list/{noticeId}")
    public ApiResult openNotice(@PathVariable("noticeId") Long noticeId){
        try{
            Notice notice = noticeService.getSpecificNotice(noticeId);
            NoticeDTO noticeDTO = Notice.toDTO(notice);
            return ApiResult.getResult(ApiResultType.SUCCESS, "공지 조회", noticeDTO);
        }catch (Exception e){
            throw new ApiException(ExceptionEnum.RUNTIME_EXCEPTION);
        }
    }

    // 공지 등록
    @PostMapping("/admin/create")
    public ApiResult createNotice(@RequestBody NoticeDTO dto){
        try{
            System.out.println("입력된 공지  "+ dto);
            Notice entity = NoticeDTO.toEntity(dto);
            noticeService.registerNotice(entity, dto.getMember_id());
            return ApiResult.getResult(ApiResultType.SUCCESS, "공지 등록", null);
        }catch (Exception e){
            throw new ApiException(ExceptionEnum.RUNTIME_EXCEPTION);
        }
    }

    // 수정할 공지 불러오기
    @GetMapping("/admin/modify/{noticeId}")
    public ApiResult modifyNotice(@PathVariable("noticeId") Long noticeId){
        try{
            Notice notice = noticeService.getSpecificNotice(noticeId);
            NoticeDTO noticeDTO = Notice.toDTO(notice);
            return ApiResult.getResult(ApiResultType.SUCCESS, "공지 수정 조회", noticeDTO);
        }catch (Exception e){
            throw new ApiException(ExceptionEnum.RUNTIME_EXCEPTION);
        }
    }

    // 공지 내용 수정시키기
    @PutMapping("/admin/update")
    public ApiResult updateNotice(@RequestBody NoticeDTO dto){
        try{
            Notice notice = NoticeDTO.toEntity(dto);
            NoticeDTO noticeDTO = Notice.toDTO(noticeService.updateNotice(notice));
            return ApiResult.getResult(ApiResultType.SUCCESS, "공지 수정", noticeDTO);
        }catch (Exception e){
            throw new ApiException(ExceptionEnum.RUNTIME_EXCEPTION);
        }
    }

    // 공지 삭제
    @DeleteMapping("/admin/delete/{noticeId}")
    public ApiResult deleteNotice(@PathVariable("noticeId") Long noticeId){
        try{
            noticeService.deleteNotice(noticeId);
            return ApiResult.getResult(ApiResultType.SUCCESS, "공지 삭제", null);
        }catch (Exception e){
            throw new ApiException(ExceptionEnum.RUNTIME_EXCEPTION);
        }
    }

}
