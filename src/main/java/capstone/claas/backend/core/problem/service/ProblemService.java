package capstone.claas.backend.core.problem.service;

import capstone.claas.backend.core.problem.Problem;
import capstone.claas.backend.core.problem.dto.JoinRequest;

import java.util.List;

public interface ProblemService {
    public List<Problem> getAllProblemList( );
    public Problem join(JoinRequest joinRequest);
}
