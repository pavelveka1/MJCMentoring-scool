package com.epam.esm.service;

import java.util.List;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DAOException;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ServiceException;

public interface TagService {

	TagDto create(TagDto tagDto) throws DAOException, ServiceException;
	TagDto read(long id) throws ServiceException;
	void delete(long id) throws ServiceException;
	List<TagDto> findAll();
}
