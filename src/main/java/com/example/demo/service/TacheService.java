package com.example.demo.service;


import com.example.demo.model.Tache;
import com.example.demo.repository.TacheRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TacheService {
    private final TacheRepository repository;

    public TacheService(TacheRepository repository) {
        this.repository = repository;
    }
    public List<Tache> getAllTasks() {
        return repository.findAll();

    }

    public Tache addTache(Tache request) {
        Tache tache = new Tache();
        tache.setSubject(request.getSubject());
        tache.setEndTime(request.getEndTime());
        tache.setStartTime(request.getStartTime());
        tache.setDescription(request.getDescription());
        tache.setIsAllDay(request.isIsAllDay());
        tache.setLocation(request.getLocation());
        tache.setGroupId(request.getGroupId());
        tache.setRecurrenceRule(request.getRecurrenceRule());
        tache.setMobileUser(request.getMobileUser());
        tache = repository.save(tache);
        return tache;
    }

}