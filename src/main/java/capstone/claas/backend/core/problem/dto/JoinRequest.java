package capstone.claas.backend.core.problem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinRequest {
    private String title;
    private String description;
    private String answer;
}
