package com.pk.backend.nosql.controller;

import com.pk.backend.nosql.model.NosqlData;
import com.pk.backend.nosql.repo.es.EsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/es")
@RequiredArgsConstructor
@CrossOrigin
public class EsController {

    private final EsRepo esRepo;

    @GetMapping
    public Iterable<NosqlData> getEs() {
        return esRepo.findAll();
    }

    @PostMapping
    public NosqlData postEs(@RequestBody NosqlData nosqlData) {
        nosqlData.setId(UUID.randomUUID());
        return esRepo.save(nosqlData);
    }
}
