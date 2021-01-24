package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagJDBCTemplate;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateEntryDAOException;
import com.epam.esm.exception.IdNotExistDAOException;
import com.epam.esm.service.configuration.ServiceConfiguration;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.DuplicateEntryServiceException;
import com.epam.esm.service.exception.IdNotExistServiceException;
import com.epam.esm.service.exception.TagNameNotExistServiceException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = ServiceConfiguration.class)
@WebAppConfiguration
public class TagServiceImplTest {

    private static TagJDBCTemplate tagJDBCTemplate = Mockito.mock(TagJDBCTemplate.class);
    private static ModelMapper modelMapper = Mockito.mock(ModelMapper.class);

    private static GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
    private static GiftCertificate giftCertificate = new GiftCertificate();
    private static Tag tag = new Tag();
    private static TagDto tagDto = new TagDto();
    private static List<GiftCertificate> giftCertificateList = new ArrayList<>();
    private static List<GiftCertificateDto> giftCertificateDtoList = new ArrayList<>();
    private static List<Tag> tagList = new ArrayList<>();
    private static List<TagDto> tagDtoList = new ArrayList<>();

    @BeforeAll
    public static void init() {
        tag.setName("test tag");
        tagDto.setName(tag.getName());

        tagDtoList.add(tagDto);
        tagList.add(tag);

        giftCertificateDto.setId(1);
        giftCertificateDto.setName("Test name");
        giftCertificateDto.setDescription("Test description");
        giftCertificateDto.setDuration(10);
        giftCertificateDto.setPrice(20);
        giftCertificateDto.setTags(tagDtoList);
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
        giftCertificate.setTags(tagList);
    }

    @DisplayName("should be returned created Tag")
    @Test
    public void createTag() throws DuplicateEntryDAOException, DuplicateEntryServiceException {
        Mockito.when(modelMapper.map(tagDto, Tag.class)).thenReturn(tag);
        Mockito.when(modelMapper.map(tag, TagDto.class)).thenReturn(tagDto);
        Mockito.when(tagJDBCTemplate.create(tag)).thenReturn(tag);
        TagServiceImpl tagService = new TagServiceImpl(tagJDBCTemplate, modelMapper);
        assertEquals(tagDto, tagService.create(tagDto));
    }

    @DisplayName("should be returned not null")
    @Test
    public void createTagNotNull() throws DuplicateEntryDAOException, DuplicateEntryServiceException {
        Mockito.when(modelMapper.map(tagDto, Tag.class)).thenReturn(tag);
        Mockito.when(modelMapper.map(tag, TagDto.class)).thenReturn(tagDto);
        Mockito.when(tagJDBCTemplate.create(tag)).thenReturn(tag);
        TagServiceImpl tagService = new TagServiceImpl(tagJDBCTemplate, modelMapper);
        assertNotNull(tagService.create(tagDto));
    }

    @DisplayName("should be thrown DuplicateEntryServiceException")
    @Test
    public void createTagDuplicateEntryServiceException() throws DuplicateEntryDAOException {
        Mockito.when(modelMapper.map(tagDto, Tag.class)).thenReturn(tag);
        Mockito.when(tagJDBCTemplate.create(tag)).thenThrow(DuplicateEntryDAOException.class);
        TagServiceImpl tagService = new TagServiceImpl(tagJDBCTemplate, modelMapper);
        assertThrows(DuplicateEntryServiceException.class, () -> {
            tagService.create(tagDto);
        });
    }


    @DisplayName("should be returned tagDto by id")
    @Test
    public void readTagById() throws IdNotExistDAOException, IdNotExistServiceException {
        Optional<Tag> tagOptional = tagList.stream().findFirst();
        Mockito.when(modelMapper.map(tag, TagDto.class)).thenReturn(tagDto);
        Mockito.when(tagJDBCTemplate.read(5)).thenReturn(tagOptional);
        TagServiceImpl tagService = new TagServiceImpl(tagJDBCTemplate, modelMapper);
        assertEquals(tagDto, tagService.read(5));
    }

    @DisplayName("should be thrown idNotExistServiceException")
    @Test
    public void readTagByNotExistId() throws IdNotExistDAOException {
        Optional<Tag> tagOptional = tagList.stream().findFirst();
        Mockito.when(modelMapper.map(tag, TagDto.class)).thenReturn(tagDto);
        Mockito.when(tagJDBCTemplate.read(1)).thenThrow(IdNotExistDAOException.class);
        TagServiceImpl tagService = new TagServiceImpl(tagJDBCTemplate, modelMapper);
        assertThrows(IdNotExistServiceException.class, () -> {
            tagService.read(1);
        });
    }


    @DisplayName("should be returned list of TagDto")
    @Test
    public void findAllTags() {
        Mockito.when(modelMapper.map(tag, TagDto.class)).thenReturn(tagDto);
        Mockito.when(tagJDBCTemplate.findAll()).thenReturn(tagList);
        TagServiceImpl tagService = new TagServiceImpl(tagJDBCTemplate, modelMapper);
        assertEquals(tagDtoList, tagService.findAll());
    }

    @DisplayName("should be returned not null")
    @Test
    public void findAllTagsResultNotNull() {
        Mockito.when(modelMapper.map(tag, TagDto.class)).thenReturn(tagDto);
        Mockito.when(tagJDBCTemplate.findAll()).thenReturn(tagList);
        TagServiceImpl tagService = new TagServiceImpl(tagJDBCTemplate, modelMapper);
        assertNotNull(tagService.findAll());
    }

}
