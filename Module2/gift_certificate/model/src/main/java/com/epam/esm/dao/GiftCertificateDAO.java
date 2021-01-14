package com.epam.esm.dao;

import java.util.List;

import com.epam.esm.entity.GiftCertificate;

public interface GiftCertificateDAO {
	GiftCertificate create(GiftCertificate giftCertificate);

	GiftCertificate read(long id);

	GiftCertificate update( GiftCertificate giftCertificate);

	boolean delete(long id);

	List<GiftCertificate> findByTagName(String tagName, ModeOfSort modeOfSort);

	List<GiftCertificate> findByPartOfName(String name, ModeOfSort modeOfSort);

	List<GiftCertificate> findAll(ModeOfSort modeOfSort);

}
