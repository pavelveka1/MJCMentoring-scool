package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.DuplicateEntryDAOException;
import com.epam.esm.exception.IdNotExistDAOException;
import com.epam.esm.exception.RequestParamDAOException;

public interface GiftCertificateDAO {
    GiftCertificate create(GiftCertificate giftCertificate) throws DuplicateEntryDAOException;

    Optional<GiftCertificate> read(long id) throws IdNotExistDAOException;

    GiftCertificate update(GiftCertificate giftCertificate);

    void delete(long id) throws IdNotExistDAOException;

    List<GiftCertificate> findAll(String sortType, String orderType) throws RequestParamDAOException;

    void deleteGiftCertificateHasTag(long id) throws IdNotExistDAOException;

}
