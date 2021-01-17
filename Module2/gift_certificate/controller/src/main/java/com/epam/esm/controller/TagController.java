package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DAOException;
import com.epam.esm.exceptionhandler.ErrorHandler;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ServiceException;
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

    @PostMapping("/tag")
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto createTag(@RequestBody TagDto tagDto) throws ServiceException, DAOException {
        return service.create(tagDto);
    }


    @GetMapping("/tag/{id}")
    public TagDto readTagById(@PathVariable long id) throws ServiceException {
        return service.read(id);
    }

    @DeleteMapping("/tag/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTagById(@PathVariable long id) throws ServiceException {
        service.delete(id);
    }

    @ExceptionHandler(value = ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorHandler handleIncorrectParameterValueException(ServiceException exception) {
        return new ErrorHandler(exception.getMessage(), 40);
    }
}
