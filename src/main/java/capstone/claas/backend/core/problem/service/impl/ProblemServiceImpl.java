package capstone.claas.backend.core.problem.service.impl;

import capstone.claas.backend.core.problem.Problem;
import capstone.claas.backend.core.problem.dto.JoinRequest;
import capstone.claas.backend.core.problem.repository.ProblemRepository;
import capstone.claas.backend.core.problem.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {
    private final ProblemRepository problemRepository;
    @Override
    public List<Problem> getAllProblemList( ) {
        return problemRepository
                .findAllList();
    }

    @Override
    public Problem join(JoinRequest joinRequest) {
        Problem problem = Problem.builder()
                .uuid(UUID.randomUUID())
                .title(joinRequest.getTitle())
                .des(joinRequest.getDescription())
                .ans(joinRequest.getAnswer())
                .filePaths(joinRequest.getFilePath())
                .level(joinRequest.getLevel())
                .num(findMaxProblemNumber( ))
                .build();

        problemRepository.save(problem);
        return problem;
    }

    @Override
    public Problem findProblemByUuid(UUID uuid) {
        return problemRepository.findOneByUuid(uuid)
                .orElseThrow(() -> new NoSuchElementException());
    }

    @Override
    public long findMaxProblemNumber() {
        Problem problem = problemRepository.findMaxNumProblem( )
                .orElse(null);

        if (problem == null)    return 1;
        return problem.getNumber() + 1;
    }
}
