package com.goorm.profileboxapiadmin.controller;

import com.goorm.profileboxapiadmin.service.MemberService;
import com.goorm.profileboxcomm.dto.member.MemberDTO;
import com.goorm.profileboxcomm.entity.Member;
import com.goorm.profileboxcomm.response.ApiResult;
import com.goorm.profileboxcomm.response.ApiResultType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/auth")
public class MemberController {

    @Autowired
    MemberService memberService;

    // test용 회원 리스트 출력
    @GetMapping("/test")
    public ApiResult<List<Member>> test() {
        List<Member> list = memberService.testListAllMember();
        return ApiResult.getResult(ApiResultType.SUCCESS, "테스트 - 회원 전체 리스트 출력", list);
    }

    // 회원 가입
    @PostMapping("/member/register")
    public ApiResult<MemberDTO> registerMemberController(@RequestBody MemberDTO dto) {
        MemberDTO registeredMemberDTO = memberService.saveMember(dto);
        return ApiResult.getResult(ApiResultType.SUCCESS, "회원가입", registeredMemberDTO);
    }

    // 회원 탈퇴
    @DeleteMapping("/member/withdrawal/{id}")
    public ApiResult withdrawalMemberController(@PathVariable("id") Long id) {
        memberService.deleteMember(id);
        return ApiResult.getResult(ApiResultType.SUCCESS, "회원탈퇴", null);
    }

    //로그인 컨트롤러 따로 필요 없음.
    /*
    @PostMapping("/member/login")
    public ApiResult<MemberDTO> loginMemberController(@RequestBody MemberDTO dto){
            Member loginMember = memberService.findLoginMember(dto);
            MemberDTO registeredMemberDTO = Member.toDTO(loginMember);
            return ApiResult.getResult(ApiResultType.SUCCESS, "로그인", registeredMemberDTO);

    }
        */

    // 회원 조회 (다른 BE API 서버에서도 호출하는 함수)
    @GetMapping("/member/{email}")
    public ApiResult<MemberDTO> getMemberInfoController(@PathVariable("email") String email){
        MemberDTO member = memberService.findLoginMemberByEmail(email);
        return ApiResult.getResult(ApiResultType.SUCCESS, "로그인", member);
    }
}
