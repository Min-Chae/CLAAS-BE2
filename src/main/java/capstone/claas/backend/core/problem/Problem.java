package capstone.claas.backend.core.problem;

import capstone.claas.backend.core.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Getter
@Table(name = "tb_problem")
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Entity
public class Problem extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;
    private String title;
    private String description;
    private String answer;

    @Builder
    public Problem(UUID uuid, String title, String des, String ans) {
        this.uuid = uuid;
        this.title = title;
        this.description = des;
        this.answer = ans;
    }
}
