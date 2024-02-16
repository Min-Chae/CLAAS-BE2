package capstone.claas.backend.core.problem.service.impl;

import capstone.claas.backend.core.problem.Problem;
import capstone.claas.backend.core.problem.repository.ProblemRepository;
import capstone.claas.backend.core.problem.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {
    private final ProblemRepository problemRepository;
    @Override
    public List<Problem> getAllProblemList( ) {
        return problemRepository
                .findAllList();
    }
}
