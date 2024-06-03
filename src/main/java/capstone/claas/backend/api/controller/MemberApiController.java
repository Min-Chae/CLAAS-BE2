package capstone.claas.backend.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import capstone.claas.backend.core.member.service.MemberService;
import capstone.claas.backend.core.member.Member;
import capstone.claas.backend.core.member.repository.MemberRepository;
import capstone.claas.backend.core.member.dto.ScoreRequest;
import capstone.claas.backend.core.member.security.JwtTokenProvider;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Controller
@RequestMapping(value = "member")
public class MemberApiController {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private static final Logger logger = LoggerFactory.getLogger(MemberApiController.class);

    @PostMapping("nickname")
    public ResponseEntity<String> getNickname(@RequestBody String token) {

        String Id = jwtTokenProvider.getMemberIdFromToken(token);
        logger.info("Member ID: {}", Id);
        System.out.println("Member ID: " + Id);

        String nickname = memberService.getNicknameByMemberId(Id);

        if (nickname != null) {
            return ResponseEntity.ok(nickname);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("닉네임을 찾을 수 없습니다.");
        }
    }

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
    public ResponseEntity<?> login(@RequestBody Member member) {
        String token = memberService.authenticateMember(member.getMemberId(), member.getPassword());

        if (token != null) {
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패!");
        }
    }

    @PostMapping("score")
    public ResponseEntity<Integer> score(@RequestBody ScoreRequest request) {
    String Id = jwtTokenProvider.getMemberIdFromToken(request.getToken());

    switch (request.getLevel()) {
        case "HARD":
            memberService.updateScoreByMemberId(Id, 30);
            return new ResponseEntity<>(30, HttpStatus.OK);
        case "MEDIUM":
            memberService.updateScoreByMemberId(Id, 20);
            return new ResponseEntity<>(20, HttpStatus.OK);
        case "LOW":
            memberService.updateScoreByMemberId(Id, 10);
            return new ResponseEntity<>(10, HttpStatus.OK);
        default:
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("rank")
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/create_dummy")
    public ResponseEntity<String> createDummyData() {
        List<Member> dummyMembers = List.of(
            new Member("user1", "password1", "nickname1", 10),
            new Member("user2", "password2", "nickname2", 20),
            new Member("user3", "password3", "nickname3", 30),
            new Member("user4", "password4", "nickname4", 40),
            new Member("user5", "password5", "nickname5", 50)
        );

        for (Member member : dummyMembers) {
            memberRepository.save(member);
        }

        return ResponseEntity.ok().body("[INFO] successfully enrolled");
    }
    
}
