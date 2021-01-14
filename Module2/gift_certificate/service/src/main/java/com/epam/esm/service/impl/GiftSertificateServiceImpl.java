package com.epam.esm.service.impl;

import java.util.List;

import com.epam.esm.dao.ModeOfSort;
import com.epam.esm.dao.impl.GiftSertificateJDBCTemplate;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GiftSertificateServiceImpl implements GiftCertificateService {

    @Autowired
    private GiftSertificateJDBCTemplate giftSertificateJDBCTemplate;

    @Override
    @Transactional
    public GiftCertificate create(GiftCertificate giftCertificate) {
        return giftSertificateJDBCTemplate.create(giftCertificate);
    }

    @Override
    @Transactional
    public GiftCertificate read(long id) {
        return giftSertificateJDBCTemplate.read(id);
    }

    @Override
    @Transactional
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return giftSertificateJDBCTemplate.update(giftCertificate);
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        return giftSertificateJDBCTemplate.delete(id);
    }

    @Override
    @Transactional
    public List<GiftCertificate> findByTagName(String tagName, ModeOfSort modeOfSort) {
        return giftSertificateJDBCTemplate.findByTagName(tagName, modeOfSort);
    }

    @Override
    @Transactional
    public List<GiftCertificate> findByPartOfName(String name, ModeOfSort modeOfSort) {
        return giftSertificateJDBCTemplate.findByPartOfName(name, modeOfSort);
    }

    @Override
    @Transactional
    public List<GiftCertificate> findAll(ModeOfSort modeOfSort) {
        return giftSertificateJDBCTemplate.findAll(modeOfSort);
    }
}
