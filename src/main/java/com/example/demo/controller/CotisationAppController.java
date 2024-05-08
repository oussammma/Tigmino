package com.example.demo.controller;

import com.example.demo.model.CotisationApp;
import com.example.demo.model.TypeCotisation;
import com.example.demo.service.CotisationAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cotisations")
public class CotisationAppController {

    @Autowired
    private CotisationAppService cotisationAppService;

    @PostMapping("/add/{appartementId}")
    public ResponseEntity<CotisationApp> addCotisationApp(@RequestBody CotisationApp cotisationApp, @PathVariable Long appartementId) {
        CotisationApp createdCotisationApp = cotisationAppService.addCotisationApp(cotisationApp, appartementId);
        return new ResponseEntity<>(createdCotisationApp, HttpStatus.CREATED);
    }
    @GetMapping
    public TypeCotisation[] getTypesCotisations() {
        return TypeCotisation.values();
    }
}
