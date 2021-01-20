package com.epam.esm.dao.impl;


import com.epam.esm.testconfiguration.TestConfig;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateMapper;
import com.epam.esm.entity.TagMapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import java.util.List;
/*
@SpringJUnitConfig(classes = {TestConfig.class})
@DisplayName("Integration GiftCertificateJdbcTemplate Test")
@ActiveProfiles("test")
 */
@SpringJUnitConfig(TestConfig.class)
public class GiftCertificateDAOTest {

    private static final String SELECT_ALL_CERTIFICATES = "SELECT * FROM gift_db.gift_certificates;";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GiftCertificateMapper giftCertificateMapper;

    @Autowired
    private TagMapper tagMapper;


   @DisplayName("should return all gift certificates")
    @SqlGroup({
            @Sql(value = "classpath:test-data.sql",
                    config = @SqlConfig(encoding = "utf-8",
                            separator = ";",
                            commentPrefix = "--"),
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),

            @Sql(value = "classpath:clean-up.sql",
                    config = @SqlConfig(encoding = "utf-8",
                            separator = ";",
                            commentPrefix = "--"),
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })


    @Test
    public void findAllGiftCertificates() {
        List<GiftCertificate> result = jdbcTemplate.query(SELECT_ALL_CERTIFICATES, giftCertificateMapper);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(10, result.size());
    }
}
