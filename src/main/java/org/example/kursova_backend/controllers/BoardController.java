package org.example.kursova_backend.controllers;

import lombok.RequiredArgsConstructor;
import org.example.kursova_backend.dtos.board.CreateBoardDto;
import org.example.kursova_backend.dtos.board.UpdateBoardDto;
import org.example.kursova_backend.services.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://localhost:3006")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    @PostMapping("/board")
    public ResponseEntity<?> create(@RequestBody CreateBoardDto createBoardDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.create(createBoardDto));
    }

    @GetMapping("/board")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(boardService.findAll());
    }


    @GetMapping("/board/{id}")
    public ResponseEntity<?> findOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(boardService.findOne(id));
    }

    @PatchMapping("/board/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody UpdateBoardDto updateBoardDto) {
        boardService.update(id, updateBoardDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/board/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") Long id) {
        boardService.remove(id);
        return ResponseEntity.ok().body(id);
    }
}
