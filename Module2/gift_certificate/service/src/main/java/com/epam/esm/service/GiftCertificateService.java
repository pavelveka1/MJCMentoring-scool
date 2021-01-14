package com.epam.esm.service;

import java.util.List;

import com.epam.esm.dao.ModeOfSort;
import com.epam.esm.entity.GiftCertificate;

public interface GiftCertificateService {
	
	GiftCertificate create(GiftCertificate giftCertificate);

	GiftCertificate read(long id);

	GiftCertificate update( GiftCertificate giftCertificate);

	boolean delete(long id);

	List<GiftCertificate> findByTagName(String tagName, ModeOfSort modeOfSort);

	List<GiftCertificate> findByPartOfName(String name, ModeOfSort modeOfSort);

	List<GiftCertificate> findAll(ModeOfSort modeOfSort);

}
