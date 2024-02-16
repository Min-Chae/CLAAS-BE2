package capstone.claas.backend.api.controller;

import capstone.claas.backend.core.problem.Problem;
import capstone.claas.backend.core.problem.dto.JoinRequest;
import capstone.claas.backend.core.problem.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
                new JoinRequest("Calculate Fibonacci Sequence", "Write a function to calculate the nth Fibonacci number.", "Use recursion or dynamic programming approach."),
                new JoinRequest("Find Prime Numbers", "Create an algorithm to efficiently find prime numbers up to n.", "Implement the Sieve of Eratosthenes."),
                new JoinRequest("Sort an Array", "Develop a method to sort an array of integers in ascending order.", "Use a sorting algorithm like quicksort or mergesort."),
                new JoinRequest("Reverse a String", "Write a function that reverses a string.", "Iterate over the string in reverse order and concatenate characters."),
                new JoinRequest("Check Palindrome", "Create a method to check if a given string is a palindrome.", "Compare characters from both ends moving towards the center."),
                new JoinRequest("Matrix Multiplication", "Implement a function for multiplying two matrices.", "Follow the standard algorithm for matrix multiplication."),
                new JoinRequest("Binary Search", "Write a binary search algorithm for a sorted array of integers.", "Use a divide-and-conquer strategy to find the target element."),
                new JoinRequest("Detect Cycle in Linked List", "Design an algorithm to detect a cycle in a linked list.", "Use Floyd's Tortoise and Hare algorithm."),
                new JoinRequest("Maximum Subarray Sum", "Find the contiguous subarray within an array which has the largest sum.", "Implement Kadane’s algorithm."),
                new JoinRequest("Balanced Parentheses", "Check if the given string has balanced parentheses.", "Use a stack to track opening and closing brackets.")
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

    @GetMapping("find")
    public ResponseEntity<?> findProblem(@RequestParam UUID uuid) {
        Problem problem = problemService.findProblemByUuid(uuid);

        return ResponseEntity.ok()
                .body(problem);
    }
}
