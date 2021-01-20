package com.epam.esm.controller;

import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.DuplicateEntryServiceException;
import com.epam.esm.service.exception.IdNotExistServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TagController {

    @Autowired
    private TagService service;

    @GetMapping("/tags")
    public List<TagDto> readAllTags() {
        return service.findAll();
    }

    @PostMapping("/tags")
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto createTag(@RequestBody TagDto tagDto) throws DuplicateEntryServiceException {
        return service.create(tagDto);
    }


    @GetMapping("/tags/{id}")
    public TagDto readTagById(@PathVariable long id) throws IdNotExistServiceException {
        return service.read(id);
    }

    @DeleteMapping("/tags/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTagById(@PathVariable long id) throws IdNotExistServiceException {
        service.delete(id);
    }

}
