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

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagJDBCTemplate tagJDBCTemplate;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public TagDto create(TagDto tagDto) throws DuplicateEntryServiceException {
        {
            Tag addedTag;
            try {
                addedTag = tagJDBCTemplate.create(modelMapper.map(tagDto, Tag.class));
            } catch (DuplicateEntryDAOException e) {
                throw new DuplicateEntryServiceException(e.getMessage(), e);
            }
            return modelMapper.map(addedTag, TagDto.class);
        }
    }

    @Override
    public TagDto read(long id) throws IdNotExistServiceException {
        Optional<Tag> readTag;
        try {
            readTag = tagJDBCTemplate.read(id);
        } catch (IdNotExistDAOException e) {
            throw new IdNotExistServiceException(e.getMessage());
        }
        return readTag.map(tag -> modelMapper.map(tag, TagDto.class))
                .orElseThrow(() -> new IdNotExistServiceException("There is no tag with ID = " + id + " in Database"));
    }

    @Override
    @Transactional
    public void delete(long id) throws IdNotExistServiceException {
        try {
            tagJDBCTemplate.read(id).orElseThrow(() -> new IdNotExistServiceException("There is no tag with ID = " + id + " in Database"));
            tagJDBCTemplate.deleteGiftCertificateHasTag(id);
            tagJDBCTemplate.delete(id);
        }catch (IdNotExistDAOException e){
            throw new IdNotExistServiceException(e.getMessage());
        }
    }

    @Override
    public List<TagDto> findAll() {
        List<Tag> tags = tagJDBCTemplate.findAll();
        return tags.stream().map(tag -> modelMapper.map(tag, TagDto.class)).collect(Collectors.toList());
    }

}
