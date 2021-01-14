package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    private TagService service;

    @GetMapping
    public List<Tag> readAllTags() {
        return service.findAll();
    }

    @PostMapping
    public Tag createTag(@RequestBody Tag tag) {
        return service.create(tag);
    }


    @GetMapping("/{id}")
    public Tag readTagById(@PathVariable int id) {
        return service.read(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTagById(@PathVariable int id) {
        service.delete(id);
    }
}
