package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

import com.epam.esm.entity.GiftCertificate;

public interface GiftCertificateDAO {
	GiftCertificate create(GiftCertificate giftCertificate);

	Optional<GiftCertificate> read(long id);

	GiftCertificate update( GiftCertificate giftCertificate);

	void delete(long id);

	List<GiftCertificate> findByTagName(String tagName, ModeOfSort modeOfSort);

	List<GiftCertificate> findByPartOfName(String name, ModeOfSort modeOfSort);

	List<GiftCertificate> findAll(ModeOfSort modeOfSort);

	void deleteGiftCertificateHasTag(long id);

}
