package com.epam.esm.dao.impl;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateMapper;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagMapper;
import com.epam.esm.exception.DuplicateEntryDAOException;
import com.epam.esm.exception.IdNotExistDAOException;
import com.epam.esm.exception.TagNameNotExistDAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class TagJDBCTemplate implements TagDAO {

    private static final String GET_TAG_BY_NAME = "SELECT * FROM gift_db.tags where gift_db.tags.name=?";
    private static final String DELETE_GIFT_CERTIFICATE_HAS_TAG = "DELETE FROM `gift_db`.`gift_certificates_has_tags` WHERE `gift_certificates_id` = ?";
    private static final String GET_TAG_BY_ID = "SELECT * FROM gift_db.tags where gift_db.tags.id=?";
    private static final String GET_ALL_TAGS = "SELECT * FROM gift_db.tags";
    private static final String CREATE_TAG = "INSERT INTO `gift_db`.`tags` (`name`) VALUES (?);";
    private static final String DELETE_TAG = "DELETE FROM `gift_db`.`tags` WHERE (`id` = ?);";
    private static final String GET_CERTIFICATES_BY_TAG_ID = "SELECT \n" +
            "gift_db.gift_certificates.id,\n" +
            "gift_db.gift_certificates.name,\n" +
            "gift_db.gift_certificates.description,\n" +
            "gift_db.gift_certificates.price,\n" +
            "gift_db.gift_certificates.duration,\n" +
            "gift_db.gift_certificates.create_date,\n" +
            "gift_db.gift_certificates.last_update_date \n" +
            "FROM gift_db.gift_certificates_has_tags\n" +
            "join gift_db.gift_certificates\n" +
            "on gift_db.gift_certificates_has_tags.gift_certificates_id = gift_db.gift_certificates.id\n" +
            "where gift_db.gift_certificates_has_tags.tags_id = ?;";

    private static final Integer PARAMETER_INDEX_TAG_NAME = 1;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GiftCertificateMapper giftCertificateMapper;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public Tag create(Tag tag) throws DuplicateEntryDAOException {
        if (isExist(tag.getName())) {
            throw new DuplicateEntryDAOException("A tag with name = " + tag.getName() + " already exists");
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TAG, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(PARAMETER_INDEX_TAG_NAME, tag.getName());
            return preparedStatement;
        }, keyHolder);
        tag.setId(keyHolder.getKey().longValue());
        return tag;
    }

    @Override
    public Optional<Tag> read(long id) throws IdNotExistDAOException {
        Optional<Tag> tag = jdbcTemplate.query(GET_TAG_BY_ID, new Object[]{id}, tagMapper).stream().findFirst();
        if (tag.isPresent()) {
            List<GiftCertificate> giftCertificates = jdbcTemplate.query(GET_CERTIFICATES_BY_TAG_ID, new Object[]{id}, giftCertificateMapper);
            for (GiftCertificate giftCertificate : giftCertificates) {
                tag.get().getCertificates().add(giftCertificate);
            }
        } else {
            throw new IdNotExistDAOException("Tag with id = " + id + " not found");
        }

        return tag;
    }

    //*****************************************************************
    @Override
    public void delete(long id) throws IdNotExistDAOException {
        try {
            jdbcTemplate.update(DELETE_TAG, id);
        } catch (Exception e) {
            throw new IdNotExistDAOException("Tag with id = " + id + " is not found");
        }

    }

    @Override
    public List<Tag> findAll() {
        List<Tag> tags = jdbcTemplate.query(GET_ALL_TAGS, tagMapper);
        for (Tag tag : tags) {
            List<GiftCertificate> giftCertificates = jdbcTemplate.query(GET_CERTIFICATES_BY_TAG_ID, new Object[]{tag.getId()}, giftCertificateMapper);
            for (GiftCertificate giftCertificate : giftCertificates) {
                tag.getCertificates().add(giftCertificate);
            }
        }
        return tags;
    }


    @Override
    public Tag readTagByName(String tagName) throws TagNameNotExistDAOException {
        Optional<Tag> tag = jdbcTemplate.query(GET_TAG_BY_NAME, new Object[]{tagName}, tagMapper).stream().findAny();
        if (!tag.isPresent()) {
            throw new TagNameNotExistDAOException("Tag with name = " + tagName + " is not exist");
        }
        return tag.get();
    }

    public void deleteGiftCertificateHasTag(long id) throws IdNotExistDAOException {
        try {
            jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_HAS_TAG, id);
        } catch (Exception e) {
            throw new IdNotExistDAOException("Tag with id = " + id + " is not found");
        }

    }

    private boolean isExist(String tagName) {
        return jdbcTemplate.query(GET_TAG_BY_NAME, new Object[]{tagName}, tagMapper).stream().findAny().isPresent();
    }
}