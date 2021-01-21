package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.DuplicateEntryDAOException;
import com.epam.esm.exception.IdNotExistDAOException;
import com.epam.esm.exception.RequestParamDAOException;

/**
 * Interface GiftCertificateDAO.
 * Contains methods for work with GiftCertificate class
 */
public interface GiftCertificateDAO {

    /**
     * Create GiftCertificate in DB
     *
     * @param giftCertificate
     * @return created GiftCertificate
     * @throws DuplicateEntryDAOException if this GiftCertificate already exists in the DB
     */
    GiftCertificate create(GiftCertificate giftCertificate) throws DuplicateEntryDAOException;

    /**
     * Read GiftCertificate from DB by id
     *
     * @param id
     * @return Optional<GiftCertificate>
     * @throws IdNotExistDAOException if records with such id not exist in DB
     */
    Optional<GiftCertificate> read(long id) throws IdNotExistDAOException;

    /**
     * Update GiftCertificate
     *
     * @param giftCertificate
     * @return updated GiftCertificate
     */
    GiftCertificate update(GiftCertificate giftCertificate);

    /**
     * Delete GiftCertificate from DB by id
     *
     * @param id
     * @throws IdNotExistDAOException if record with such id not exist in DB
     */
    void delete(long id) throws IdNotExistDAOException;

    /**
     * Find all giftCertificates with condition determined by parameters
     *
     * @param sortType  name of field of table in DB
     * @param orderType ASC or DESC
     * @return list og GiftCertificates
     * @throws RequestParamDAOException if parameters don't right
     */
    List<GiftCertificate> findAll(String sortType, String orderType) throws RequestParamDAOException;

    /**
     * Delete records from link table
     *
     * @param id
     * @throws IdNotExistDAOException if record with such id not exist in DB
     */
    void deleteGiftCertificateHasTag(long id) throws IdNotExistDAOException;

}
