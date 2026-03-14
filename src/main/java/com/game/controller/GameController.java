package com.game;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "*")
public class GameController {

    private static final List<String> QUESTIONS = Arrays.asList(
        "What is 2 + 2?",
        "What is the capital of France?",
        "What color is the sky?"
    );

    private static final List<String> ANSWERS = Arrays.asList(
        "4",
        "Paris",
        "Blue"
    );

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "service", "game-service");
    }

    @GetMapping("/question/{id}")
    public Map<String, Object> getQuestion(@PathVariable int id) {
        if (id < 0 || id >= QUESTIONS.size()) {
            return Map.of("error", "Question not found");
        }
        return Map.of(
            "id", id,
            "question", QUESTIONS.get(id),
            "total", QUESTIONS.size()
        );
    }

    @PostMapping("/answer/{id}")
    public Map<String, Object> checkAnswer(
        @PathVariable int id,
        @RequestBody Map<String, String> body
    ) {
        if (id < 0 || id >= ANSWERS.size()) {
            return Map.of("error", "Question not found");
        }
        String userAnswer = body.getOrDefault("answer", "");
        boolean correct = ANSWERS.get(id)
            .equalsIgnoreCase(userAnswer.trim());
        return Map.of(
            "correct", correct,
            "correctAnswer", ANSWERS.get(id)
        );
    }

    @GetMapping("/questions")
    public Map<String, Object> getAllQuestions() {
        List<Map<String, Object>> questions = new ArrayList<>();
        for (int i = 0; i < QUESTIONS.size(); i++) {
            questions.add(Map.of(
                "id", i,
                "question", QUESTIONS.get(i)
            ));
        }
        return Map.of("questions", questions, "total", questions.size());
    }
}
