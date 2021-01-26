package com.epam.esm.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.epam.esm.dao.impl.TagJDBCTemplate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateEntryDAOException;
import com.epam.esm.exception.IdNotExistDAOException;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.DuplicateEntryServiceException;
import com.epam.esm.service.exception.IdNotExistServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class TagServiceImpl.
 * Contains methods for work with Tag class
 */
@Service
public class TagServiceImpl implements TagService {

    /**
     * TagJDBCTemplate is used for operations with Tag
     */
    @Autowired
    private TagJDBCTemplate tagJDBCTemplate;

    /**
     * ModelMapper is used for convertation TagDto to Tag
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Empty constructor
     */
    public TagServiceImpl() {

    }

    /**
     * Constcuctor with all parameters
     *
     * @param tagJDBCTemplate for operations with Tag
     * @param modelMapper     for convertion object
     */
    public TagServiceImpl(TagJDBCTemplate tagJDBCTemplate, ModelMapper modelMapper) {
        this.tagJDBCTemplate = tagJDBCTemplate;
        this.modelMapper = modelMapper;
    }

    /**
     * Create new tag in DB
     *
     * @param tagDto it contains data of Tag will be created
     * @return created TagDto
     * @throws DuplicateEntryServiceException if this Tag already exists in the DB
     */
    @Override
    @Transactional
    public TagDto create(TagDto tagDto) throws DuplicateEntryServiceException {
        Tag addedTag;
        try {
            addedTag = tagJDBCTemplate.create(modelMapper.map(tagDto, Tag.class));
        } catch (DuplicateEntryDAOException e) {
            throw new DuplicateEntryServiceException(e.getMessage(), e);
        }
        return modelMapper.map(addedTag, TagDto.class);
    }

    /**
     * Read one Tag from DB by id
     *
     * @param id id of Tag
     * @return Optional<Tag>
     * @throws IdNotExistServiceException if records with such id not exist in DB
     */
    @Override
    public TagDto read(long id) throws IdNotExistServiceException {
        Tag readTag;
        try {
            readTag = tagJDBCTemplate.read(id);
        } catch (IdNotExistDAOException e) {
            throw new IdNotExistServiceException(e.getMessage());
        }
        return modelMapper.map(readTag, TagDto.class);
    }

    /**
     * Delete Tag from DB by id
     *
     * @param id id of Tag
     * @throws IdNotExistServiceException if records with such id not exist in DB
     */
    @Override
    @Transactional
    public void delete(long id) throws IdNotExistServiceException {
        try {
            tagJDBCTemplate.delete(id);
        } catch (IdNotExistDAOException e) {
            throw new IdNotExistServiceException(e.getMessage());
        }
    }


    /**
     * Find all Tags
     *
     * @return list of TagDto
     */
    @Override
    public List<TagDto> findAll() {
        List<Tag> tags = tagJDBCTemplate.findAll();
        return tags.stream().map(tag -> modelMapper.map(tag, TagDto.class)).collect(Collectors.toList());
    }

}
