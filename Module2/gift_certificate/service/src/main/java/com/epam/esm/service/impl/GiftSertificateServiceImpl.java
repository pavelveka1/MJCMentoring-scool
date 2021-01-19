package com.epam.esm.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.epam.esm.dao.ModeOfSort;
import com.epam.esm.dao.impl.GiftSertificateJDBCTemplate;
import com.epam.esm.dao.impl.TagJDBCTemplate;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DAOException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ServiceException;
import lombok.SneakyThrows;
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
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        GiftCertificate createdGiftCertificate;
        createAndSetTags(giftCertificateDto);
        createdGiftCertificate = giftCertificateJDBCTemplate.create(modelMapper.map(giftCertificateDto, GiftCertificate.class));
        return modelMapper.map(createdGiftCertificate, GiftCertificateDto.class);
    }

    @Override
    @Transactional
    public GiftCertificateDto read(long id) throws ServiceException {
        Optional<GiftCertificate> foundCertificate = giftCertificateJDBCTemplate.read(id);
        return foundCertificate
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .orElseThrow(() -> new ServiceException("There is no gift certificate with ID = " + id + " in Database"));
    }

    @Override
    @Transactional
    public GiftCertificateDto update(GiftCertificateDto modifiedGiftCertificateDto) throws ServiceException {
        GiftCertificate modifiedGiftCertificate = modelMapper.map(modifiedGiftCertificateDto, GiftCertificate.class);
        GiftCertificateDto readGiftCertificateDto = read(modifiedGiftCertificateDto.getId());
        if (readGiftCertificateDto == null) {
            throw new ServiceException("There is no gift certificate with ID = " + modifiedGiftCertificateDto.getId() + " in Database");
        }
        GiftCertificate readGiftCertificate = modelMapper.map(readGiftCertificateDto, GiftCertificate.class);
        updateGiftCertificateFields(readGiftCertificate, modifiedGiftCertificate);
        GiftCertificate updateGiftCertificate = giftCertificateJDBCTemplate.update(readGiftCertificate);
        return modelMapper.map(updateGiftCertificate, GiftCertificateDto.class);
    }

    @Override
    @Transactional
    public void delete(long id) throws ServiceException {
        giftCertificateJDBCTemplate.read(id).orElseThrow(() -> new ServiceException("There is no gift certificate with ID = " + id + " in Database"));
        giftCertificateJDBCTemplate.deleteGiftCertificateHasTag(id);
        giftCertificateJDBCTemplate.delete(id);
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> findByTagName(String tagName, ModeOfSort modeOfSort) {
        //  return giftCertificateJDBCTemplate.findByTagName(tagName, modeOfSort);
        return null;
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> findByPartOfName(String name, ModeOfSort modeOfSort) {
        //return giftCertificateJDBCTemplate.findByPartOfName(name, modeOfSort);
        return null;
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> findAll(ModeOfSort modeOfSort) {
        List<GiftCertificate> giftCertificates = giftCertificateJDBCTemplate.findAll(modeOfSort);
        return giftCertificates.stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }


    private void createAndSetTags(GiftCertificateDto giftCertificateDto) {
        List<TagDto> tags = new ArrayList<>();
        if (giftCertificateDto.getTags() != null) {
            TagDto add;
            for (TagDto tagDto : giftCertificateDto.getTags()) {
                Tag tag = modelMapper.map(tagDto, Tag.class);
                try {
                    add = modelMapper.map(tagJDBCTemplate.create(tag), TagDto.class);
                    tags.add(add);
                } catch (DAOException tagExist) {
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
