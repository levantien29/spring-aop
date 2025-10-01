package com.example.Spring_API_Auth.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
public class LogController {

    private static final String LOG_FILE = "logs/spring-api.log";

    // Lấy toàn bộ log (không khuyến khích nếu file lớn)
    @GetMapping("/api/logs/all")
    public ResponseEntity<String> getAllLogs() throws IOException {
        String content = Files.readString(Path.of(LOG_FILE));
        return ResponseEntity.ok(content);
    }

    // Lấy 100 dòng cuối
    @GetMapping("/api/logs/tail")
    public ResponseEntity<List<String>> getTailLogs() throws IOException {
        List<String> allLines = Files.readAllLines(Path.of(LOG_FILE));
        int size = allLines.size();
        int fromIndex = Math.max(0, size - 100);
        return ResponseEntity.ok(allLines.subList(fromIndex, size));
    }
}
