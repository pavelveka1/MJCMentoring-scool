package com.epam.esm.dao.impl;

import com.epam.esm.configuration.DBConfig;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateMapper;
import com.epam.esm.entity.TagMapper;
import com.epam.esm.configuration.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/*
@SpringJUnitConfig(classes = {TestConfig.class})
@DisplayName("Integration GiftCertificateJdbcTemplate Test")
@ActiveProfiles("test")
 */
@SpringJUnitConfig(classes = TestConfig.class)
@Sql(value = "classpath:test-schema.sql")
@SqlMergeMode(value = SqlMergeMode.MergeMode.MERGE)
@WebAppConfiguration
@ActiveProfiles("test")
public class GiftCertificateDAOTest {

    private static final String SELECT_ALL_CERTIFICATES = "SELECT * FROM gift_db.gift_certificates;";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GiftCertificateMapper giftCertificateMapper;

    @Autowired
    private TagMapper tagMapper;


    @DisplayName("should return all gift certificates")
    @Test
    @SqlGroup({
            @Sql(value = "classpath:test-data.sql",
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),

            @Sql(value = "classpath:clean-up.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    } )
    public void findAllGiftCertificates() {
        List<GiftCertificate> result = jdbcTemplate.query(SELECT_ALL_CERTIFICATES, giftCertificateMapper);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(10, result.size());
    }
}
