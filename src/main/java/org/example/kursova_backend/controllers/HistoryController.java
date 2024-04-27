package org.example.kursova_backend.controllers;

import lombok.RequiredArgsConstructor;
import org.example.kursova_backend.dtos.operation.OperationDTO;
import org.example.kursova_backend.services.HistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller()
@CrossOrigin(origins = "http://localhost:3006")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/history/{boardId}")
    public ResponseEntity<List<OperationDTO>> findAll(@PathVariable("boardId") String boardId) {
        Long longBoardId = Long.parseLong(boardId);
        List<OperationDTO> historyItems = historyService.findAll(longBoardId);
        return ResponseEntity.ok(historyItems);

    }
}

