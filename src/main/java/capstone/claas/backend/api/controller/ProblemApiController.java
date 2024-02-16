package capstone.claas.backend.api.controller;

import capstone.claas.backend.core.problem.Problem;
import capstone.claas.backend.core.problem.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "problem")
public class ProblemApiController {
    private final ProblemService problemService;

    @GetMapping("list")
    public ResponseEntity<?> getAllProblemList( ) {
        List<Problem> problems = problemService.getAllProblemList( );
        return ResponseEntity
                .ok()
                .body(problems);
    }
}
