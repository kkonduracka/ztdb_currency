package com.pk.backend.nosql.controller;

import com.pk.backend.nosql.model.NosqlData;
import com.pk.backend.nosql.repo.mongo.MongoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/mongo")
@RequiredArgsConstructor
@CrossOrigin
public class MongoController {

    private final MongoRepo mongoRepo;

    @GetMapping
    public List<NosqlData> getMongo() {
        return mongoRepo.findAll();
    }

    @PostMapping
    public NosqlData postMongo(@RequestBody NosqlData nosqlData) {
        nosqlData.setId(UUID.randomUUID());
        return mongoRepo.save(nosqlData);
    }

}
