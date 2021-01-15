package com.epam.esm.controller;

import com.epam.esm.dao.ModeOfSort;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.google.protobuf.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gift_certificate")
public class GiftCertificateController {

    @Autowired
    private GiftCertificateService service;

    @GetMapping
    public List<GiftCertificate> readAllGiftCertificates() {
        return service.findAll(ModeOfSort.DESC);
    }

    @GetMapping("/{id}")
    public GiftCertificate readGiftCertificate(@PathVariable int id) {
        return service.read(id);
    }

    @PostMapping
    public GiftCertificate createGiftCertificate(@RequestBody GiftCertificate giftCertificate) {
        return service.create(giftCertificate);
    }

    // TO DO
    @PutMapping
    public void updateGiftCertificate(@RequestBody GiftCertificate giftCertificate) {
        service.update(giftCertificate);
    }

    // TO DO
    @DeleteMapping("/{id}")
    public void deleteGiftCertificate(@PathVariable int id) {
        service.delete(id);
    }

}