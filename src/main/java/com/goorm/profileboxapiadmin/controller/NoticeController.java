package com.goorm.profileboxapiadmin.controller;

import com.goorm.profileboxapiadmin.service.NoticeService;
import com.goorm.profileboxcomm.dto.notice.NoticeDTO;
import com.goorm.profileboxcomm.entity.Notice;
import com.goorm.profileboxcomm.response.ApiResult;
import com.goorm.profileboxcomm.response.ApiResultType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/v1/notice")
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    // 공지 전체 조회
    @GetMapping("/list")
    public ApiResult<List<NoticeDTO>> getNoticeList(){
        List<Notice> list = noticeService.getAllNoitce();
        List<NoticeDTO> noticeDTOList = list.stream()
                .map(o -> Notice.toDTO(o))
                .collect(Collectors.toList());
        return ApiResult.getResult(ApiResultType.SUCCESS, "공지 리스트 조회", noticeDTOList);
    }

    // 특정 공지 열람
    @GetMapping("/list/{noticeId}")
    public ApiResult<NoticeDTO> openNotice(@PathVariable("noticeId") Long noticeId){
        Notice notice = noticeService.getSpecificNotice(noticeId);
        NoticeDTO noticeDTO = Notice.toDTO(notice);
        return ApiResult.getResult(ApiResultType.SUCCESS, "공지 조회", noticeDTO);
    }

    // 공지 등록
    @PostMapping("/admin/create")
    public ApiResult<NoticeDTO> createNotice(@RequestBody NoticeDTO dto) throws ParseException {
        Notice notice = noticeService.registerNotice(dto);
        NoticeDTO noticeDTO = Notice.toDTO(notice);
        return ApiResult.getResult(ApiResultType.SUCCESS, "공지 등록", noticeDTO);
    }

    // 수정할 공지 불러오기
    @GetMapping("/admin/modify/{noticeId}")
    public ApiResult<NoticeDTO> modifyNotice(@PathVariable("noticeId") Long noticeId){
        Notice notice = noticeService.getSpecificNotice(noticeId);
        NoticeDTO noticeDTO = Notice.toDTO(notice);
        return ApiResult.getResult(ApiResultType.SUCCESS, "공지 수정 조회", noticeDTO);

    }

    // 공지 내용 수정시키기
    @PutMapping("/admin/update")
    public ApiResult<NoticeDTO> updateNotice(@RequestBody NoticeDTO dto) throws ParseException{
        Notice notice = NoticeDTO.toEntity(dto);
        NoticeDTO noticeDTO = Notice.toDTO(noticeService.updateNotice(notice));
        return ApiResult.getResult(ApiResultType.SUCCESS, "공지 수정", noticeDTO);
    }

    // 공지 삭제
    @DeleteMapping("/admin/delete/{noticeId}")
    public ApiResult deleteNotice(@PathVariable("noticeId") Long noticeId){
        noticeService.deleteNotice(noticeId);
        return ApiResult.getResult(ApiResultType.SUCCESS, "공지 삭제", null);
    }
}
