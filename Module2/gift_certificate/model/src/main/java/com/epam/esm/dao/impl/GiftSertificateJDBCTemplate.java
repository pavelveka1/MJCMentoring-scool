package com.epam.esm.dao.impl;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.ModeOfSort;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateMapper;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class GiftSertificateJDBCTemplate implements GiftCertificateDAO {

    private static final String SELECT_ALL_CERTIFICATES = "SELECT * FROM gift_db.gift_certificates;";
    private static final String SELECT_CERTIFICATE_ID = "SELECT * FROM gift_db.gift_certificates where gift_certificates.id=?;";
    private static final String INSERT_CERTIFICATE = "INSERT INTO `gift_db`.`gift_certificates` (`name`, `description`, `price`, `duration`) VALUES (?, ?, ?, ?);";
    private static final String SELECT_TAGS_BY_CERTIFICATE_ID = "SELECT id, name FROM gift_db.gift_certificates_has_tags\n" +
            "join gift_db.tags\n" +
            "on gift_db.gift_certificates_has_tags.tags_id = gift_db.tags.id\n" +
            "where gift_db.gift_certificates_has_tags.gift_certificates_id=?;";
    private static final String CREATE_CERTIFICATE_HAS_TAG = "INSERT INTO `gift_db`.`gift_certificates_has_tags` (`gift_certificates_id`, `tags_id`) VALUES (?, ?);\n";
    private static final String UPDATE_GIFT_CERTIFICATE = "UPDATE `gift_db`.`gift_certificates` SET `name` = ?, `description` = ?, `price` = ?, `duration` = ? WHERE (`id` = ?);\n";
    private static final String DELETE_GIFT_CERTIFICATE_HAS_TAG = "DELETE FROM `gift_db`.`gift_certificates_has_tags` WHERE `gift_certificates_id` = ?";
    private static final String DELETE_GIFT_CERTIFICATE = "DELETE FROM gift_db.gift_certificates WHERE id = ?";
    // write sql queries

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GiftCertificateMapper giftCertificateMapper;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, giftCertificate.getName());
            preparedStatement.setString(2, giftCertificate.getDescription());
            preparedStatement.setInt(3, giftCertificate.getPrice());
            preparedStatement.setInt(4, giftCertificate.getDuration());
            return preparedStatement;
        }, keyHolder);

        GiftCertificate createdGiftCertificate = jdbcTemplate.query(SELECT_CERTIFICATE_ID, new Object[]{keyHolder.getKey().intValue()}, giftCertificateMapper).stream().findFirst().get();

        giftCertificate.setId(createdGiftCertificate.getId());
        giftCertificate.setCreateDate(createdGiftCertificate.getCreateDate());
        giftCertificate.setLastUpdateDate(createdGiftCertificate.getLastUpdateDate());

        createGiftCertificateHasTag(giftCertificate);
        return giftCertificate;
    }

    @Override
    public Optional<GiftCertificate> read(long id) {
        Optional<GiftCertificate> giftCertificate = jdbcTemplate.query(SELECT_CERTIFICATE_ID, new Object[]{id}, giftCertificateMapper).stream().findFirst();
        List<Tag> tags = jdbcTemplate.query(SELECT_TAGS_BY_CERTIFICATE_ID, new Object[]{id}, tagMapper);
        for (Tag tag : tags) {
            giftCertificate.get().getTags().add(tag);
        }
        return giftCertificate;
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getId());
        return giftCertificate;
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(DELETE_GIFT_CERTIFICATE, id);
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
        List<GiftCertificate> giftCertificateList = jdbcTemplate.query(SELECT_ALL_CERTIFICATES, giftCertificateMapper);
        for (GiftCertificate giftCertificate : giftCertificateList) {
            List<Tag> tags = jdbcTemplate.query(SELECT_TAGS_BY_CERTIFICATE_ID, new Object[]{giftCertificate.getId()}, tagMapper);
            for (Tag tag : tags) {
                giftCertificate.getTags().add(tag);
            }
        }
        return giftCertificateList;
    }

    @Override
    public void deleteGiftCertificateHasTag(long id) {
        jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_HAS_TAG, id);
    }

    private void createGiftCertificateHasTag(GiftCertificate giftCertificate) {
        List<Tag> tags = giftCertificate.getTags();
        tags.forEach(tag -> jdbcTemplate.update(CREATE_CERTIFICATE_HAS_TAG, giftCertificate.getId(), tag.getId()));
    }

}
