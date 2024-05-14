package capstone.claas.backend.api.controller;

import capstone.claas.backend.core.member.service.MemberService;
import capstone.claas.backend.core.member.Member;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "member")
public class MemberApiController {

    @Autowired
    private MemberService memberService;

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody Member member) {
        try {
            memberService.registerMember(member.getMemberId(), member.getPassword(), member.getNickname());
            return new ResponseEntity<>("회원가입이 성공적으로 완료되었습니다.", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody Member member) {
        Member authenticatedMember = memberService.authenticateMember(member.getMemberId(), member.getPassword());
        
        if (authenticatedMember != null) {
            return new ResponseEntity<>("로그인 성공!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("로그인 실패!", HttpStatus.UNAUTHORIZED);
        }
    }
}
