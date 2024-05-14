package capstone.claas.backend.core.member.service;

import capstone.claas.backend.core.member.Member;
import capstone.claas.backend.core.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member registerMember(String memberId, String password, String nickname) {
       
        Member member = new Member();
        member.setMemberId(memberId);
        member.setPassword(password);
        member.setNickname(nickname);

        if (memberRepository.findByMemberId(memberId) != null) {
            throw new RuntimeException("이미 존재하는 회원 ID입니다.");
        }

        return memberRepository.save(member);
    }

    public Member authenticateMember(String id, String password) {
        Member member = memberRepository.findByMemberId(id);
        if (member != null && member.getPassword().equals(password)) {
            return member;
        } else {
            return null;
        }
    }
}