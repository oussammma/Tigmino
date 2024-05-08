package com.example.demo.controller;


import com.example.demo.model.Depense;
import com.example.demo.model.DepenseRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.example.demo.repository.BlocRepository;
import com.example.demo.repository.DepenseRepository;
import com.example.demo.service.DepenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class DepenseController {

    @Autowired
    private DepenseService depenseService;

    @Autowired
    private BlocRepository blocRepository;

    @Autowired
    private DepenseRepository depenseRepository;

    @GetMapping("/getAllDepenses")
    public List<Depense> getAllDepensesWithBlocsAndResidences() {
        return depenseService.getAllDepensesWithBlocsAndResidences();
    }
    @GetMapping("/getAllDepensesForResidence")
    public List<Depense> getAllDepensesForResidence(@RequestParam Long residenceId) {
        return depenseService.getAllDepensesForResidence(residenceId);
    }
    public static class DepenseNotFoundException extends RuntimeException {
        public DepenseNotFoundException(String message) {
            super(message);
        }
    }




    @PostMapping("/addDepense")
    public ResponseEntity<Depense> addDepense(@RequestBody DepenseRequest depenseRequest) {
        Depense depense = depenseService.saveDepense(depenseRequest.getRequest(), depenseRequest.getBlocIds());
        return ResponseEntity.ok(depense);
    }
    @PutMapping("/{depenseId}/complete")
    public ResponseEntity<Depense> markDepenseCompleted(@PathVariable Integer depenseId, @RequestBody Map<String, String> requestBody) {
        String comment = requestBody.get("comment");
        Depense depense = depenseService.markDepenseCompleted(depenseId, comment);
        return ResponseEntity.ok(depense);
    }

    @PutMapping("/{depenseId}/cancel")
    public ResponseEntity<Depense> markDepenseCancelled(@PathVariable Integer depenseId, @RequestBody Map<String, String> requestBody) {
        String comment = requestBody.get("comment");
        Depense depense = depenseService.markDepenseCancelled(depenseId, comment);
        return ResponseEntity.ok(depense);
    }


}
