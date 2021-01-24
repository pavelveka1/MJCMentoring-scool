package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.GiftCertificateJDBCTemplate;
import com.epam.esm.dao.impl.TagJDBCTemplate;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateEntryDAOException;
import com.epam.esm.exception.IdNotExistDAOException;
import com.epam.esm.exception.RequestParamDAOException;
import com.epam.esm.service.configuration.ServiceConfiguration;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.DuplicateEntryServiceException;
import com.epam.esm.service.exception.IdNotExistServiceException;
import com.epam.esm.service.exception.RequestParamServiceException;
import com.epam.esm.service.exception.TagNameNotExistServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringJUnitConfig(classes = ServiceConfiguration.class)
@WebAppConfiguration

public class GiftCertificateServiceImplTest {

    private static GiftCertificateJDBCTemplate giftCertificateJDBCTemplate = Mockito.mock(GiftCertificateJDBCTemplate.class);
    private static TagJDBCTemplate tagJDBCTemplate = Mockito.mock(TagJDBCTemplate.class);
    private static ModelMapper modelMapper = Mockito.mock(ModelMapper.class);

    private static GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
    private static GiftCertificate giftCertificate = new GiftCertificate();
    private static Tag tag = new Tag();
    private static TagDto tagDto = new TagDto();
    private static List<GiftCertificate> giftCertificateList = new ArrayList<>();
    private static List<GiftCertificateDto> giftCertificateDtoList = new ArrayList<>();

    @BeforeEach
    public void init() {

        tag.setName("test tag");
        List<Tag> tags = new ArrayList<>();
        tagDto.setName(tag.getName());
        List<TagDto> tagDtoList = new ArrayList<>();

        TagDto tagDto1 = new TagDto();
        tagDto1.setName("Space");
        TagDto tagDto2 = new TagDto();
        tagDto2.setName("Aircraft");

        List<TagDto> tagsDto = new ArrayList<TagDto>();
        tagsDto.add(tagDto1);
        tagsDto.add(tagDto2);

        giftCertificateDto.setId(1);
        giftCertificateDto.setName("Test name");
        giftCertificateDto.setDescription("Test description");
        giftCertificateDto.setDuration(10);
        giftCertificateDto.setPrice(20);
        giftCertificateDto.setTags(tagsDto);
        giftCertificateDtoList.add(giftCertificateDto);

        giftCertificate.setId(giftCertificateDto.getId());
        giftCertificate.setName(giftCertificateDto.getName());
        giftCertificate.setDescription(giftCertificateDto.getDescription());
        giftCertificate.setDuration(giftCertificateDto.getDuration());
        giftCertificate.setPrice(giftCertificateDto.getPrice());
        giftCertificateList.add(giftCertificate);

        tagDto.setGiftCertificateList(giftCertificateDtoList);
        tag.setCertificates(giftCertificateList);
        giftCertificateDto.setTags(tagDtoList);
        giftCertificate.setTags(tags);
    }

    @DisplayName("should be returned created gift certificate")
    @Test
    public void createGiftCertificate() throws DuplicateEntryDAOException, TagNameNotExistServiceException, DuplicateEntryServiceException {
        Mockito.when(giftCertificateJDBCTemplate.create(giftCertificate)).thenReturn(giftCertificate);
        Mockito.when(modelMapper.map(giftCertificateDto, GiftCertificate.class)).thenReturn(giftCertificate);
        Mockito.when(modelMapper.map(giftCertificate, GiftCertificateDto.class)).thenReturn(giftCertificateDto);
        GiftSertificateServiceImpl giftSertificateService = new GiftSertificateServiceImpl(giftCertificateJDBCTemplate, tagJDBCTemplate, modelMapper);
        assertEquals(giftCertificateDto, giftSertificateService.create(giftCertificateDto));
    }

    @DisplayName("should be thrown duplicateEntryServiceException")
    @Test
    public void createGiftCertificateDuplicateEntryException() throws DuplicateEntryDAOException {
        Mockito.when(giftCertificateJDBCTemplate.create(giftCertificate)).thenThrow(DuplicateEntryDAOException.class);
        Mockito.when(modelMapper.map(giftCertificateDto, GiftCertificate.class)).thenReturn(giftCertificate);
        GiftSertificateServiceImpl giftSertificateService = new GiftSertificateServiceImpl(giftCertificateJDBCTemplate, tagJDBCTemplate, modelMapper);
        assertThrows(DuplicateEntryServiceException.class, () -> {
            giftSertificateService.create(giftCertificateDto);
        });
    }

    @DisplayName("should be renurned giftCertificateDto")
    @Test
    public void readGiftCertificateById() throws IdNotExistDAOException, IdNotExistServiceException {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateList.stream().findFirst();
        Mockito.when(giftCertificateJDBCTemplate.read(2)).thenReturn(giftCertificateOptional);
        Mockito.when(modelMapper.map(giftCertificate, GiftCertificateDto.class)).thenReturn(giftCertificateDto);
        GiftSertificateServiceImpl giftCertificateService = new GiftSertificateServiceImpl(giftCertificateJDBCTemplate, tagJDBCTemplate, modelMapper);
        assertEquals(giftCertificateDto, giftCertificateService.read(2));
    }

    @DisplayName("should be thrown IdNotExistServiceException")
    @Test
    public void readGiftCertificateByNotExistId() throws IdNotExistDAOException {
        Mockito.when(giftCertificateJDBCTemplate.read(6)).thenThrow(IdNotExistDAOException.class);
        GiftSertificateServiceImpl giftSertificateService = new GiftSertificateServiceImpl(giftCertificateJDBCTemplate, tagJDBCTemplate, modelMapper);
        assertThrows(IdNotExistServiceException.class, () -> {
            giftSertificateService.read(1);
        });
    }

    @DisplayName("should be returned updated giftCertificateDto")
    @Test
    public void updateGiftCertificate() throws IdNotExistServiceException, IdNotExistDAOException {
        GiftCertificateDto giftCertificateDto2 = new GiftCertificateDto();
        giftCertificateDto2.setName("giftCertificateDto 2");
        giftCertificateDto2.setDescription("description 2 giftCertificateDto");
        giftCertificateDto2.setPrice(10);
        giftCertificateDto2.setDuration(30);
        giftCertificateDto2.setId(4);
        Mockito.when(modelMapper.map(giftCertificateDto2, GiftCertificate.class)).thenReturn(giftCertificate);
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateList.stream().findFirst();
        Mockito.when(giftCertificateJDBCTemplate.read(4)).thenReturn(giftCertificateOptional);
        Mockito.when(modelMapper.map(giftCertificate, GiftCertificateDto.class)).thenReturn(giftCertificateDto2);
        Mockito.when(modelMapper.map(giftCertificateDto2, GiftCertificate.class)).thenReturn(giftCertificate);
        Mockito.when(giftCertificateJDBCTemplate.update(giftCertificate)).thenReturn(giftCertificate);
        GiftSertificateServiceImpl giftSertificateService = new GiftSertificateServiceImpl(giftCertificateJDBCTemplate, tagJDBCTemplate, modelMapper);
        assertEquals(giftCertificateDto2, giftSertificateService.update(giftCertificateDto2));
    }

    @DisplayName("should be thrown IdNotExistServiceException")
    @Test
    public void updateGiftCertificateIdNotExist() throws IdNotExistDAOException {
        Mockito.when(modelMapper.map(giftCertificateDto, GiftCertificate.class)).thenReturn(giftCertificate);
        Mockito.when(giftCertificateJDBCTemplate.read(8)).thenThrow(IdNotExistDAOException.class);
        GiftSertificateServiceImpl giftSertificateService = new GiftSertificateServiceImpl(giftCertificateJDBCTemplate, tagJDBCTemplate, modelMapper);
        assertThrows(IdNotExistServiceException.class, () -> {
            giftSertificateService.update(giftCertificateDto);
        });
    }

    @DisplayName("should be returned list of giftCertificateDto")
    @Test
    public void findAllGiftCertificates() throws RequestParamDAOException, RequestParamServiceException {
        Mockito.when(giftCertificateJDBCTemplate.findAll("name", "DESC")).thenReturn(giftCertificateList);
        Mockito.when(modelMapper.map(giftCertificate, GiftCertificateDto.class)).thenReturn(giftCertificateDto);
        GiftSertificateServiceImpl giftSertificateService = new GiftSertificateServiceImpl(giftCertificateJDBCTemplate, tagJDBCTemplate, modelMapper);
        assertEquals(giftCertificateDtoList, giftSertificateService.findAll("name", "DESC"));
    }

    @DisplayName("should be thrown RequestParamServiceException")
    @Test
    public void findAllGiftCertificatesRequestParamsAreNotValid() throws RequestParamDAOException {
        Mockito.when(giftCertificateJDBCTemplate.findAll(null, null)).thenThrow(RequestParamDAOException.class);
        GiftSertificateServiceImpl giftSertificateService = new GiftSertificateServiceImpl(giftCertificateJDBCTemplate, tagJDBCTemplate, modelMapper);
        assertThrows(RequestParamServiceException.class, () -> {
            giftSertificateService.findAll(null, null);
        });
    }
}
