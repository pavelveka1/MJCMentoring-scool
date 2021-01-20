package com.epam.esm.controller;

import com.epam.esm.exception.TagNameNotExistDAOException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.exception.DuplicateEntryServiceException;
import com.epam.esm.service.exception.RequestParamServiceException;
import com.epam.esm.service.exception.TagNameNotExistServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.epam.esm.service.exception.IdNotExistServiceException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GiftCertificateController {

    @Autowired
    private GiftCertificateService service;

    @GetMapping("/gift_certificates")
    public List<GiftCertificateDto> readAll(@RequestParam(required = false) String sortType, @RequestParam(required = false) String orderType) throws RequestParamServiceException {
        return service.findAll(sortType, orderType);
    }

    @GetMapping("/gift_certificates/{id}")
    public GiftCertificateDto read(@PathVariable int id) throws IdNotExistServiceException {
        return service.read(id);
    }

    @PostMapping("/gift_certificates")
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto create(@RequestBody GiftCertificateDto giftCertificateDto) throws DuplicateEntryServiceException, TagNameNotExistServiceException {
        return service.create(giftCertificateDto);
    }


    @PutMapping("/gift_certificates")
    public GiftCertificateDto update(@RequestBody GiftCertificateDto giftCertificateDto) throws IdNotExistServiceException {
        return service.update(giftCertificateDto);
    }

    @DeleteMapping("/gift_certificates/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) throws IdNotExistServiceException {
        service.delete(id);
    }
}