package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.repositories.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/medicines")
public class MedicineController {
    private final MedicineRepository medicineRepository;

    @GetMapping
    public ResponseEntity<List<Medicine>> getAll() {
        return ResponseEntity.ok(medicineRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicine> get(@PathVariable Long id) {
        return ResponseEntity.ok(medicineRepository.getById(id));
    }

    @PostMapping
    public ResponseEntity<Medicine> create(@RequestBody Medicine medicine) {
        if (medicineRepository.existsById(medicine.getId())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(medicine);
        }
        medicineRepository.save(medicine);
        return ResponseEntity.ok(medicine);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicine> update(@PathVariable Long id, @RequestBody Medicine medicine) {
        if (!medicineRepository.existsById(id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(medicine);
        }
        medicineRepository.save(medicine);
        return ResponseEntity.ok(medicine);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        if (medicineRepository.existsById(id)) {
            medicineRepository.deleteById(id);
        }
    }
}