package com.epam.esm.dao.impl;

import java.util.List;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.ModeOfSort;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateMapper;
import com.epam.esm.entity.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GiftSertificateJDBCTemplate implements GiftCertificateDAO {

    // write sql queries

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GiftCertificateMapper giftCertificateMapper;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        return null;
    }

    @Override
    public GiftCertificate read(long id) {
        return null;
    }

    @Override
    public GiftCertificate update( GiftCertificate giftCertificate) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public List<GiftCertificate> findByTagName(String tagName, ModeOfSort modeOfSort) {
        return null;
    }

    @Override
    public List<GiftCertificate> findByPartOfName(String name, ModeOfSort modeOfSort) {
        return null;
    }

    @Override
    public List<GiftCertificate> findAll(ModeOfSort modeOfSort) {
        return null;
    }
}
