package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pharmacies")
public class PharmacyController {
    private final PharmacyRepository pharmacyRepository;

    @GetMapping
    public ResponseEntity<List<Pharmacy>> getAll() {
        return ResponseEntity.ok(pharmacyRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pharmacy> get(@PathVariable Long id) {
        return ResponseEntity.ok(pharmacyRepository.getById(id));
    }

    @PostMapping
    public ResponseEntity<Pharmacy> create(@RequestBody Pharmacy pharmacy) {
        if (pharmacyRepository.existsById(pharmacy.getId())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(pharmacy);
        }
        pharmacyRepository.save(pharmacy);
        return ResponseEntity.ok(pharmacy);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pharmacy> update(@PathVariable Long id, @RequestBody Pharmacy pharmacy) {
        if (!pharmacyRepository.existsById(id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(pharmacy);
        }
        pharmacyRepository.save(pharmacy);
        return ResponseEntity.ok(pharmacy);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        if (pharmacyRepository.existsById(id)) {
            pharmacyRepository.deleteById(id);
        }
    }
}
