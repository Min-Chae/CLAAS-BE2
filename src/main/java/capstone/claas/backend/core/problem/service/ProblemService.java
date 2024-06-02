package capstone.claas.backend.core.problem.service;

import capstone.claas.backend.core.problem.Problem;
import capstone.claas.backend.core.problem.dto.JoinRequest;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProblemService {
    public List<Problem> getAllProblemList( );
    public Problem join(JoinRequest joinRequest);
    public Problem findProblemByUuid(UUID uuid);

    public long findMaxProblemNumber( );

    public Map<String, String> getAddrAndPath(String path);
}
