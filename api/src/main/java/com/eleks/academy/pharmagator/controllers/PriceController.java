package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.entities.Price;
import com.eleks.academy.pharmagator.entities.PriceId;
import com.eleks.academy.pharmagator.repositories.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/prices")
public class PriceController {
    private final PriceRepository priceRepository;

    @GetMapping
    public ResponseEntity<List<Price>> getAll() {
        return ResponseEntity.ok(priceRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Price> get(@PathVariable PriceId id) {
        return ResponseEntity.ok(priceRepository.getById(id));
    }

    @PostMapping
    public ResponseEntity<Price> create(@RequestBody Price price) {
        PriceId priceId = new PriceId();
        priceId.setMedicineId(price.getMedicineId());
        priceId.setPharmacyId(price.getPharmacyId());
        if (priceRepository.existsById(priceId)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(price);
        }
        priceRepository.save(price);
        return ResponseEntity.ok(price);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Price> update(@PathVariable PriceId id, @RequestBody Price price) {
        if (!priceRepository.existsById(id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(price);
        }
        priceRepository.save(price);
        return ResponseEntity.ok(price);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable PriceId id) {
        if (priceRepository.existsById(id)) {
            priceRepository.deleteById(id);
        }
    }
}