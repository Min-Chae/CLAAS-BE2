package capstone.claas.backend.api.controller;

import capstone.claas.backend.core.problem.Problem;
import capstone.claas.backend.core.problem.constant.Level;
import capstone.claas.backend.core.problem.dto.GradeRequest;
import capstone.claas.backend.core.problem.dto.JoinRequest;
import capstone.claas.backend.core.problem.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

    @GetMapping("create_dummy")
    public ResponseEntity<?> createDummyData( ) {
        List<JoinRequest> req = new ArrayList<>(List.of(
                new JoinRequest("Calculate Fibonacci Sequence", "Write a function to calculate the nth Fibonacci number.", "Use recursion or dynamic programming approach.", List.of("src/main/resources/static/a.png"), Level.HARD),
                new JoinRequest("Find Prime Numbers", "Create an algorithm to efficiently find prime numbers up to n.", "Implement the Sieve of Eratosthenes.", List.of("src/main/resources/static/a.png"), Level.HARD),
                new JoinRequest("Sort an Array", "Develop a method to sort an array of integers in ascending order.", "Use a sorting algorithm like quicksort or mergesort.", List.of("src/main/resources/static/a.png"), Level.HARD),
                new JoinRequest("Reverse a String", "Write a function that reverses a string.", "Iterate over the string in reverse order and concatenate characters.", List.of("src/main/resources/static/a.png"), Level.MEDIUM),
                new JoinRequest("Check Palindrome", "Create a method to check if a given string is a palindrome.", "Compare characters from both ends moving towards the center.", List.of("src/main/resources/static/a.png"), Level.HARD),
                new JoinRequest("Matrix Multiplication", "Implement a function for multiplying two matrices.", "Follow the standard algorithm for matrix multiplication.", List.of("src/main/resources/static/a.png"), Level.HARD),
                new JoinRequest("Binary Search", "Write a binary search algorithm for a sorted array of integers.", "Use a divide-and-conquer strategy to find the target element.", List.of("src/main/resources/static/a.png"), Level.LOW),
                new JoinRequest("Detect Cycle in Linked List", "Design an algorithm to detect a cycle in a linked list.", "Use Floyd's Tortoise and Hare algorithm.", List.of("src/main/resources/static/a.png"), Level.HARD),
                new JoinRequest("Maximum Subarray Sum", "Find the contiguous subarray within an array which has the largest sum.", "Implement Kadane’s algorithm.", List.of("src/main/resources/static/a.png"), Level.HARD),
                new JoinRequest("Balanced Parentheses", "Check if the given string has balanced parentheses.", "Use a stack to track opening and closing brackets.", List.of("src/main/resources/static/a.png"), Level.HARD)
        ));


        for (JoinRequest joinRequest : req) {
            problemService.join(joinRequest);
        }

        return ResponseEntity
                .ok()
                .body("[INFO] successfully enrolled");
    }

    @PostMapping("enroll")
    public ResponseEntity<?> enrollProblem(JoinRequest joinRequest) {
        Problem problem = problemService.join(joinRequest);

        return ResponseEntity.ok()
                .body(problem);
    }

    @GetMapping("download")
    public ResponseEntity<?> findProblem(@RequestParam UUID uuid) throws IOException {
        Problem problem = problemService.findProblemByUuid(uuid);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream( );
        ZipOutputStream zipOut = new ZipOutputStream(byteArrayOutputStream);

        for (String filePath : problem.getFilePaths( )) {
            Path path = Paths.get(filePath).toAbsolutePath().normalize();
            if (Files.exists(path)) {
                zipOut.putNextEntry(new ZipEntry(path.getFileName().toString()));
                Files.copy(path, zipOut);
                zipOut.closeEntry();
            }
        }

        zipOut.finish();
        zipOut.close();

        byte[] zipBytes = byteArrayOutputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders( );
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=multiple_files.zip");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(zipBytes.length)
                .contentType(MediaType.parseMediaType("application/zip"))
                .body(new InputStreamResource(new ByteArrayInputStream(zipBytes)));
    }

    @GetMapping("find")
    public ResponseEntity<?> downloadProblemFiles(@RequestParam UUID uuid) {
        Problem problem = problemService.findProblemByUuid(uuid);

        return ResponseEntity.ok()
                .body(problem);
    }

    @PostMapping("grade")
    public ResponseEntity<?> gradeProblem(@RequestBody GradeRequest req) {
        Problem problem = problemService.findProblemByUuid(req.getUuid());
        boolean ans = req.getAns().equals(problem.getAnswer());

        return ResponseEntity.ok()
                .body(ans);
    }
}
