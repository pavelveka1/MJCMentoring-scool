package com.epam.esm.controller;

import com.epam.esm.exceptionhandler.ValidationException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.exception.DuplicateEntryServiceException;
import com.epam.esm.service.exception.RequestParamServiceException;
import com.epam.esm.service.exception.TagNameNotExistServiceException;
import com.epam.esm.validator.GiftCertificateDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.epam.esm.service.exception.IdNotExistServiceException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * @Class GiftCertificateController - Rest controller for process of request to GiftCertificates
 */
@RestController
@RequestMapping("/api")
public class GiftCertificateController {

    /**
     * GiftCertificateService is used for work with GiftCertificateDto
     */
    @Autowired
    private GiftCertificateService service;

    @Autowired
    private Validator giftCertificateDtoValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(giftCertificateDtoValidator);
    }

    /**
     * @param sortType  - it is name of field in table gitf_certificates of DB
     * @param orderType ASC or DESC
     * @return List<GiftCertificateDto>
     * @throws RequestParamServiceException if params don't correlate with name of field in DB
     * @method readAll - read all GiftCertificates fro DB
     */
    @GetMapping("/gift_certificates")
    public List<GiftCertificateDto> readAll(@RequestParam(required = false) String sortType, @RequestParam(required = false) String orderType) throws RequestParamServiceException {
        return service.findAll(sortType, orderType);
    }

    /**
     * @param id
     * @return GiftCertificateDto
     * @throws IdNotExistServiceException if GiftCertificate with such id doesn't exist in DB
     * @method read - read one GiftCertificate from DB by passed id
     */
    @GetMapping("/gift_certificates/{id}")
    public GiftCertificateDto read(@PathVariable @Min(5) int id) throws IdNotExistServiceException {
        return service.read(id);
    }

    /**
     * @param giftCertificateDto
     * @return created GiftCertificate as GiftCertificateDto
     * @throws DuplicateEntryServiceException  if such giftCertificate alredy exist in DB
     * @throws TagNameNotExistServiceException if Tag with such name is not found
     * @method create - creates new GiftCertificate in DB
     */
    @PostMapping("/gift_certificates")
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto create( @Valid @RequestBody GiftCertificateDto giftCertificateDto, BindingResult bindingResult) throws DuplicateEntryServiceException, TagNameNotExistServiceException, ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("GiftCertificateDto is not valid for create operation");
        }
        return service.create(giftCertificateDto);
    }

    /**
     * @param giftCertificateDto
     * @return updated GiftCertificate as GiftCertificateDto
     * @throws IdNotExistServiceException if GiftCertificate with such id doesn't exist in DB
     * @method update - updates GiftCertificate
     */
    @PutMapping("/gift_certificates")
    public GiftCertificateDto update( @Valid @RequestBody GiftCertificateDto giftCertificateDto, BindingResult bindingResult) throws IdNotExistServiceException, ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("GiftCertificateDto is not valid for update operation!");
        }
        return service.update(giftCertificateDto);
    }

    /**
     * @param id
     * @throws IdNotExistServiceException if GiftCertificate with such id doesn't exist in DB
     * @method delete - delete GiftCertificate from DB by id
     */
    @DeleteMapping("/gift_certificates/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) throws IdNotExistServiceException {
        service.delete(id);
    }
}