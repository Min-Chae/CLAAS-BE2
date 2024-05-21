package capstone.claas.backend.core.member.repository;

import capstone.claas.backend.core.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    Member findByMemberId(String memberId);

    @Query("SELECT m FROM Member m ORDER BY m.score ASC")
    List<Member> findAllOrderByScoreAsc();
}