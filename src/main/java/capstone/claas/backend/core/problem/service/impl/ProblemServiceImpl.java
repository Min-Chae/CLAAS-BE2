package capstone.claas.backend.core.problem.service.impl;

import capstone.claas.backend.core.problem.Problem;
import capstone.claas.backend.core.problem.dto.JoinRequest;
import capstone.claas.backend.core.problem.repository.ProblemRepository;
import capstone.claas.backend.core.problem.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

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
                .envPath(joinRequest.getEnvPath())
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

    @Override
    public Map<String, String> getAddrAndPath(String path) {
        ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", path);
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();
            return readOutput(process.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Map<String, String> readOutput(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String output = "";
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                output = line;
            }
            Map<String, String> res = new HashMap<>();
            res.put("host", output.split(" ")[0]);
            res.put("port", output.split(" ")[1]);
            return res;
        }
    }
}
