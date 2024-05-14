package capstone.claas.backend.core.member;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Table(name = "tb_member")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Entity
public class Member {
    @Id
    @Column(name="memberId")
    private String memberId;

    @Column(name="password")
    private String password;

    @Column(name="nickname")
    private String nickname;
    
}

