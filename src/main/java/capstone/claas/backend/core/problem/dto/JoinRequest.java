package capstone.claas.backend.core.problem.dto;

import capstone.claas.backend.core.problem.constant.Level;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JoinRequest {
    private String title;
    private String description;
    private String answer;
    private List<String> filePath;
    private Level level;
    private String envPath;
}
