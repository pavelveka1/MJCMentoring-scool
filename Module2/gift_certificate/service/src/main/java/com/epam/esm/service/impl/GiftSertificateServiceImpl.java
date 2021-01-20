package com.epam.esm.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.epam.esm.dao.impl.GiftSertificateJDBCTemplate;
import com.epam.esm.dao.impl.TagJDBCTemplate;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.*;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.DuplicateEntryServiceException;
import com.epam.esm.service.exception.IdNotExistServiceException;
import com.epam.esm.service.exception.RequestParamServiceException;
import com.epam.esm.service.exception.TagNameNotExistServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GiftSertificateServiceImpl implements GiftCertificateService {

    @Autowired
    private GiftSertificateJDBCTemplate giftCertificateJDBCTemplate;

    @Autowired
    private TagJDBCTemplate tagJDBCTemplate;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) throws DuplicateEntryServiceException, TagNameNotExistServiceException {
        GiftCertificate createdGiftCertificate;

        try {
            createAndSetTags(giftCertificateDto);
            createdGiftCertificate = giftCertificateJDBCTemplate.create(modelMapper.map(giftCertificateDto, GiftCertificate.class));
            return modelMapper.map(createdGiftCertificate, GiftCertificateDto.class);
        } catch (DuplicateEntryDAOException e) {
            throw new DuplicateEntryServiceException(e.getMessage());
        } catch (TagNameNotExistDAOException ex) {
            throw new TagNameNotExistServiceException(ex.getMessage());
        }

    }

    @Override
    @Transactional
    public GiftCertificateDto read(long id) throws IdNotExistServiceException {
        Optional<GiftCertificate> foundCertificate;
        try {
            foundCertificate = giftCertificateJDBCTemplate.read(id);
        } catch (IdNotExistDAOException e) {
            throw new IdNotExistServiceException(e.getMessage());
        }
        return foundCertificate
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .orElseThrow(() -> new IdNotExistServiceException("There is no gift certificate with ID = " + id + " in Database"));
    }

    @Override
    @Transactional
    public GiftCertificateDto update(GiftCertificateDto modifiedGiftCertificateDto) throws IdNotExistServiceException {
        GiftCertificate modifiedGiftCertificate = modelMapper.map(modifiedGiftCertificateDto, GiftCertificate.class);
        GiftCertificateDto readGiftCertificateDto = read(modifiedGiftCertificateDto.getId());
        if (readGiftCertificateDto == null) {
            throw new IdNotExistServiceException("There is no gift certificate with ID = " + modifiedGiftCertificateDto.getId() + " in Database");
        }
        GiftCertificate readGiftCertificate = modelMapper.map(readGiftCertificateDto, GiftCertificate.class);
        updateGiftCertificateFields(readGiftCertificate, modifiedGiftCertificate);
        GiftCertificate updateGiftCertificate = giftCertificateJDBCTemplate.update(readGiftCertificate);
        return modelMapper.map(updateGiftCertificate, GiftCertificateDto.class);
    }

    @Override
    @Transactional
    public void delete(long id) throws IdNotExistServiceException {
        try {
            giftCertificateJDBCTemplate.read(id).orElseThrow(() -> new IdNotExistServiceException("There is no gift certificate with ID = " + id + " in Database"));
            giftCertificateJDBCTemplate.deleteGiftCertificateHasTag(id);
            giftCertificateJDBCTemplate.delete(id);
        } catch (IdNotExistDAOException e) {
            throw new IdNotExistServiceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> findAll(String sortType, String orderType) throws RequestParamServiceException {
        List<GiftCertificate> giftCertificates;
        try {
            giftCertificates = giftCertificateJDBCTemplate.findAll(sortType, orderType);
        } catch (RequestParamDAOException e) {
            throw new RequestParamServiceException(e.getMessage());
        }
        return giftCertificates.stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }


    private void createAndSetTags(GiftCertificateDto giftCertificateDto) throws TagNameNotExistDAOException {
        List<TagDto> tags = new ArrayList<>();
        if (giftCertificateDto.getTags() != null) {
            TagDto add;
            for (TagDto tagDto : giftCertificateDto.getTags()) {
                Tag tag = modelMapper.map(tagDto, Tag.class);
                try {
                    add = modelMapper.map(tagJDBCTemplate.create(tag), TagDto.class);
                    tags.add(add);
                } catch ( DuplicateEntryDAOException tagExist) {
                    add = modelMapper.map(tagJDBCTemplate.readTagByName(tag.getName()), TagDto.class);
                    tags.add(add);
                }
            }
            giftCertificateDto.setTags(tags);
        }

    }

    private void updateGiftCertificateFields(GiftCertificate readGiftCertificateDto, GiftCertificate
            modifiedGiftCertificate) {
        if (modifiedGiftCertificate.getDuration() != null) {
            readGiftCertificateDto.setDuration(modifiedGiftCertificate.getDuration());
        }
        if (modifiedGiftCertificate.getDescription() != null) {
            readGiftCertificateDto.setDescription(modifiedGiftCertificate.getDescription());
        }
        if (modifiedGiftCertificate.getName() != null) {
            readGiftCertificateDto.setName(modifiedGiftCertificate.getName());
        }
        if (modifiedGiftCertificate.getPrice() != null) {
            readGiftCertificateDto.setPrice(modifiedGiftCertificate.getPrice());
        }
        readGiftCertificateDto.setLastUpdateDate(LocalDateTime.now(ZoneOffset.systemDefault()));
    }
}
