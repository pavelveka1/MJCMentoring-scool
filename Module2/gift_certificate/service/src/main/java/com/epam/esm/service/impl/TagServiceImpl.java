package com.epam.esm.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.epam.esm.dao.impl.TagJDBCTemplate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DAOException;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagJDBCTemplate tagJDBCTemplate;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public TagDto create(TagDto tagDto) throws ServiceException {
        {
            Tag addedTag;
            try {
                addedTag =  tagJDBCTemplate.create(modelMapper.map(tagDto, Tag.class));
            } catch (DAOException e) {
                throw new ServiceException(e.getMessage(), e);
            }
            return modelMapper.map(addedTag, TagDto.class);
        }
    }

        @Override
        public TagDto read ( long id) throws ServiceException {
            Optional<Tag> readTag = tagJDBCTemplate.read(id);
            return readTag.map(tag -> modelMapper.map(tag, TagDto.class))
                    .orElseThrow(() -> new ServiceException("There is no tag with ID = " + id + " in Database"));
        }

        @Override
        @Transactional
        public void delete ( long id) throws ServiceException {
            tagJDBCTemplate.read(id).orElseThrow(() -> new ServiceException("There is no tag with ID = " + id + " in Database"));
            tagJDBCTemplate.deleteGiftCertificateHasTag(id);
            tagJDBCTemplate.delete(id);
        }

        @Override
        public List<TagDto> findAll () {
            List<Tag> tags = tagJDBCTemplate.findAll();
            return tags.stream().map(tag -> modelMapper.map(tag, TagDto.class)).collect(Collectors.toList());
        }

    }
