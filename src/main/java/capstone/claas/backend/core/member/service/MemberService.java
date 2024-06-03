package capstone.claas.backend.core.member.service;

import capstone.claas.backend.core.member.Member;
import capstone.claas.backend.core.member.repository.MemberRepository;
import capstone.claas.backend.core.member.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public Member registerMember(String memberId, String password, String nickname) {
        Member existingMember = memberRepository.findByMemberId(memberId);
        if (existingMember != null) {
            throw new RuntimeException("이미 존재하는 회원 ID입니다.");
        }

        Member member = new Member();
        member.setMemberId(memberId);
        member.setPassword(password);
        member.setNickname(nickname);
        member.setScore(0);

        return memberRepository.save(member);
    }

    public String authenticateMember(String memberId, String password) {
        Member member = memberRepository.findByMemberId(memberId);
        
        if (member != null && member.getPassword().equals(password)) {
            return jwtTokenProvider.generateToken(memberId);
        } else {
            return null;
        }
    }

    public String getNicknameByMemberId(String memberId) {
        Member member = memberRepository.findByMemberId(memberId);
        if (member != null) {
            return member.getNickname();
        } else {
            return null;
        }
    }

    public boolean updateScoreByMemberId(String memberId, int score) {
        Member member = memberRepository.findByMemberId(memberId);
        
        if (member != null) {
            int currentScore = member.getScore();
            int updatedScore = currentScore + score; // 점수를 10점 증가시킴
            member.setScore(updatedScore);
            memberRepository.save(member); // 업데이트된 멤버 정보를 저장
            return true;
        } else {
            return false;
        }
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAllOrderByScoreDesc();
    }
}