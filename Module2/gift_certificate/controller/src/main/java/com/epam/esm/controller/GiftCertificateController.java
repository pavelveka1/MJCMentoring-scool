package com.epam.esm.controller;

import com.epam.esm.dao.ModeOfSort;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exceptionhandler.ErrorHandler;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.google.protobuf.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GiftCertificateController {

    @Autowired
    private GiftCertificateService service;

    @GetMapping("/gift_certificates")
    public List<GiftCertificateDto> readAll() {
        return service.findAll(ModeOfSort.ASC);
    }

    @GetMapping("/gift_certificates/{id}")
    public GiftCertificateDto read(@PathVariable int id) throws com.epam.esm.service.exception.ServiceException {
        return  service.read(id);
    }

    @PostMapping("/gift_certificates")
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto create(@RequestBody GiftCertificateDto giftCertificateDto) throws com.epam.esm.service.exception.ServiceException {
        return  service.create(giftCertificateDto);
    }

    @PutMapping("/gift_certificates")
    public GiftCertificateDto update(@RequestBody GiftCertificateDto giftCertificateDto) throws com.epam.esm.service.exception.ServiceException {
        return  service.update(giftCertificateDto);
    }

    @DeleteMapping("/gift_certificates/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) throws com.epam.esm.service.exception.ServiceException {
        service.delete(id);
    }



    @ExceptionHandler(value = ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorHandler handleIncorrectParameterValueException(ServiceException exception) {
        return new ErrorHandler(exception.getMessage(), 40);
    }
}