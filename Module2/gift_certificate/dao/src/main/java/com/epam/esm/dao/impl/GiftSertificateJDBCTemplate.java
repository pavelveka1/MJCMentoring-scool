package com.epam.esm.dao.impl;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateMapper;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagMapper;
import com.epam.esm.exception.DuplicateEntryDAOException;
import com.epam.esm.exception.IdNotExistDAOException;
import com.epam.esm.exception.RequestParamDAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class GiftSertificateJDBCTemplate implements GiftCertificateDAO {

    private static final String SELECT_ALL_CERTIFICATES = "SELECT * FROM gift_db.gift_certificates order by id;";
    private static final String SELECT_ALL_CERTIFICATES_WITH_SORT = "SELECT * FROM gift_db.gift_certificates order by %s %s;";
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
    private static final String DEFAULT_SORT_ORDER = "asc";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GiftCertificateMapper giftCertificateMapper;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) throws DuplicateEntryDAOException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, giftCertificate.getName());
                preparedStatement.setString(2, giftCertificate.getDescription());
                preparedStatement.setInt(3, giftCertificate.getPrice());
                preparedStatement.setInt(4, giftCertificate.getDuration());
                return preparedStatement;
            }, keyHolder);
        } catch (Exception e) {
            throw new DuplicateEntryDAOException(e.getMessage());
        }

        GiftCertificate createdGiftCertificate = jdbcTemplate.query(SELECT_CERTIFICATE_ID, new Object[]{keyHolder.getKey().intValue()}, giftCertificateMapper).stream().findFirst().get();

        giftCertificate.setId(createdGiftCertificate.getId());
        giftCertificate.setCreateDate(createdGiftCertificate.getCreateDate());
        giftCertificate.setLastUpdateDate(createdGiftCertificate.getLastUpdateDate());

        createGiftCertificateHasTag(giftCertificate);
        return giftCertificate;
    }

    @Override
    public Optional<GiftCertificate> read(long id) throws IdNotExistDAOException {
        Optional<GiftCertificate> giftCertificate = jdbcTemplate.query(SELECT_CERTIFICATE_ID, new Object[]{id}, giftCertificateMapper).stream().findFirst();
        if (giftCertificate.isPresent()) {
            List<Tag> tags = jdbcTemplate.query(SELECT_TAGS_BY_CERTIFICATE_ID, new Object[]{id}, tagMapper);
            for (Tag tag : tags) {
                giftCertificate.get().getTags().add(tag);
            }
        } else {
            throw new IdNotExistDAOException("There is no gift certificate with ID = " + id + " in Database");
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
    public void delete(long id) throws IdNotExistDAOException {
        try {
            jdbcTemplate.update(DELETE_GIFT_CERTIFICATE, id);
        } catch (Exception e) {
            throw new IdNotExistDAOException("There is no gift certificate with ID = " + id + " in Database");
        }
    }

    @Override
    public List<GiftCertificate> findAll(String sortType, String orderType) throws RequestParamDAOException {
        List<GiftCertificate> giftCertificateList;
        try {
            if (sortType == null) {
                giftCertificateList = jdbcTemplate.query(SELECT_ALL_CERTIFICATES, giftCertificateMapper);
            } else {
                if (orderType == null) {
                    orderType = DEFAULT_SORT_ORDER;
                    giftCertificateList = jdbcTemplate.query(String.format(SELECT_ALL_CERTIFICATES_WITH_SORT, sortType, orderType), giftCertificateMapper);
                } else {
                    giftCertificateList = jdbcTemplate.query(String.format(SELECT_ALL_CERTIFICATES_WITH_SORT, sortType, orderType), giftCertificateMapper);
                }
            }
        } catch (Exception e) {
            throw new RequestParamDAOException(e.getMessage());
        }

        for (GiftCertificate giftCertificate : giftCertificateList) {
            List<Tag> tags = jdbcTemplate.query(SELECT_TAGS_BY_CERTIFICATE_ID, new Object[]{giftCertificate.getId()}, tagMapper);
            for (Tag tag : tags) {
                giftCertificate.getTags().add(tag);
            }
        }
        return giftCertificateList;
    }

    @Override
    public void deleteGiftCertificateHasTag(long id) throws IdNotExistDAOException {
        try {
            jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_HAS_TAG, id);
        } catch (Exception e) {
            throw new IdNotExistDAOException("There is no entry with ID = " + id + " in table gift_certificates_has_tags Database");
        }
    }

    private void createGiftCertificateHasTag(GiftCertificate giftCertificate) {
        List<Tag> tags = giftCertificate.getTags();
        tags.forEach(tag -> jdbcTemplate.update(CREATE_CERTIFICATE_HAS_TAG, giftCertificate.getId(), tag.getId()));
    }

}
