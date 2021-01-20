package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateEntryDAOException;
import com.epam.esm.exception.IdNotExistDAOException;
import com.epam.esm.exception.TagNameNotExistDAOException;

public interface TagDAO {
	
	Tag create(Tag tag) throws DuplicateEntryDAOException;
	Optional<Tag> read(long id) throws IdNotExistDAOException;
	void delete(long id) throws IdNotExistDAOException;
	List<Tag> findAll();
	Tag readTagByName(String tagName) throws TagNameNotExistDAOException;

}
