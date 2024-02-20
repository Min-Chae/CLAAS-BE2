package capstone.claas.backend.core.problem;

import capstone.claas.backend.core.BaseEntity;
import capstone.claas.backend.core.problem.constant.Level;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
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
    private long number;
    private String title;
    private String description;
    private String answer;
    private Level level;
    @ElementCollection
    @CollectionTable(name = "problem_file_paths", joinColumns = @JoinColumn(name = "problem_id"))
    @Column(name = "file_path")
    private List<String> filePaths;

    @Builder
    public Problem(UUID uuid, String title, String des, String ans, List<String> filePaths, Level level, long num) {
        this.uuid = uuid;
        this.title = title;
        this.description = des;
        this.answer = ans;
        this.filePaths = filePaths;
        this.level = level;
        this.number = num;
    }
}
