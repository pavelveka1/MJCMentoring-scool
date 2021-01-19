package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DAOException;

public interface TagDAO {
	
	Tag create(Tag tag) throws DAOException;
	Optional<Tag> read(long id);
	void delete(long id);
	List<Tag> findAll();
	Tag readTagByName(String tagName);

}
