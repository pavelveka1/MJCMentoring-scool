package com.epam.esm.service;

import java.util.List;

import com.epam.esm.dao.ModeOfSort;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.exception.ServiceException;

public interface GiftCertificateService {
	
	GiftCertificateDto create(GiftCertificateDto giftCertificateDto) throws ServiceException;

	GiftCertificateDto read(long id) throws ServiceException;

	GiftCertificateDto update( GiftCertificateDto giftCertificateDto) throws ServiceException;

	void delete(long id) throws ServiceException;

	List<GiftCertificateDto> findByTagName(String tagName, ModeOfSort modeOfSort);

	List<GiftCertificateDto> findByPartOfName(String name, ModeOfSort modeOfSort);

	List<GiftCertificateDto> findAll(ModeOfSort modeOfSort);

}
