package com.epam.esm.service;

import java.util.List;

import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.DuplicateEntryServiceException;
import com.epam.esm.service.exception.IdNotExistServiceException;

public interface TagService {

	TagDto create(TagDto tagDto) throws DuplicateEntryServiceException;
	TagDto read(long id) throws IdNotExistServiceException;
	void delete(long id) throws IdNotExistServiceException;
	List<TagDto> findAll();
}
