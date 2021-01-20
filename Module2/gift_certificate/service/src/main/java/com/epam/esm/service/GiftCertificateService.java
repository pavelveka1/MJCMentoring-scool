package com.epam.esm.service;

import java.util.List;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.exception.DuplicateEntryServiceException;
import com.epam.esm.service.exception.IdNotExistServiceException;
import com.epam.esm.service.exception.RequestParamServiceException;
import com.epam.esm.service.exception.TagNameNotExistServiceException;

public interface GiftCertificateService {
	
	GiftCertificateDto create(GiftCertificateDto giftCertificateDto) throws DuplicateEntryServiceException, TagNameNotExistServiceException;

	GiftCertificateDto read(long id) throws IdNotExistServiceException;

	GiftCertificateDto update( GiftCertificateDto giftCertificateDto) throws IdNotExistServiceException;

	void delete(long id) throws IdNotExistServiceException;

	List<GiftCertificateDto> findAll(String sortType, String orderType) throws RequestParamServiceException;

}
