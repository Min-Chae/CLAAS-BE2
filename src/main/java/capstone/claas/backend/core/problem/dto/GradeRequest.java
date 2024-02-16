package capstone.claas.backend.core.problem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class GradeRequest {
    private UUID uuid;
    private String ans;
}
