package com.example.demo.controller;

import com.example.demo.model.Tache;
import com.example.demo.model.UserMob;
import com.example.demo.service.TacheService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TacheController {

    private final TacheService tacheService;
    private final UserService userService;

    @Autowired
    public TacheController(TacheService tacheService, UserService userService) {
        this.tacheService = tacheService;
        this.userService = userService;
    }

    @GetMapping("/getAllTasks")
    public ResponseEntity<List<Tache>> getAllTasks() {
        List<Tache> tasks = tacheService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping("/addTask/{userId}")
    public ResponseEntity<Tache> addTask(@RequestBody Tache request, @PathVariable("userId") int userId) {
        UserMob userMob = userService.getUserById(userId);
        if (userMob == null) {
            return ResponseEntity.notFound().build();
        }
        request.setMobileUser(userMob);
        return ResponseEntity.ok(tacheService.addTache(request));
    }
}
