package com.epam.esm.service;

import java.util.List;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.exception.DuplicateEntryServiceException;
import com.epam.esm.service.exception.IdNotExistServiceException;
import com.epam.esm.service.exception.RequestParamServiceException;
import com.epam.esm.service.exception.TagNameNotExistServiceException;

/**
 * Interface GiftCertificateService.
 * Contains methods for work with GiftCertificateDto
 */
public interface GiftCertificateService {

    /**
     * Create GiftCertificate in DB
     *
     * @param giftCertificateDto
     * @return created GiftCertificate as GiftCertificateDto
     * @throws DuplicateEntryServiceException if this GiftCertificate already exists in the DB
     */
    GiftCertificateDto create(GiftCertificateDto giftCertificateDto) throws DuplicateEntryServiceException, TagNameNotExistServiceException;

    /**
     * Read GiftCertificateDto from DB by id
     *
     * @param id
     * @return GiftCertificateDto
     * @throws IdNotExistServiceException if records with such id not exist in DB
     */
    GiftCertificateDto read(long id) throws IdNotExistServiceException;

    /**
     * Update GiftCertificate as GiftCertificateDto
     *
     * @param giftCertificateDto
     * @return updated GiftCertificateDto
     */
    GiftCertificateDto update(GiftCertificateDto giftCertificateDto) throws IdNotExistServiceException;

    /**
     * Delete GiftCertificate from DB by id
     *
     * @param id
     * @throws IdNotExistServiceException if record with such id not exist in DB
     */
    void delete(long id) throws IdNotExistServiceException;

    /**
     * Find all giftCertificates with condition determined by parameters
     *
     * @param sortType  name of field of table in DB
     * @param orderType ASC or DESC
     * @return list og GiftCertificates
     * @throws RequestParamServiceException if parameters don't right
     */
    List<GiftCertificateDto> findAll(String sortType, String orderType) throws RequestParamServiceException;

}
