package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateEntryDAOException;
import com.epam.esm.exception.IdNotExistDAOException;
import com.epam.esm.exception.TagNameNotExistDAOException;

/**
 * Interface TagDAO.
 * Contains methods for work with Tag class
 */
public interface TagDAO {

    /**
     * Create new tag in DB
     * @param tag
     * @return created Tag
     * @throws DuplicateEntryDAOException if this Tag already exists in the DB
     */
	Tag create(Tag tag) throws DuplicateEntryDAOException;

    /**
     * Read one Tag from DB by id
     * @param id
     * @return Optional<Tag>
     * @throws IdNotExistDAOException if records with such id not exist in DB
     */
	Optional<Tag> read(long id) throws IdNotExistDAOException;

    /**
     * Delete Tag from DB by id
     * @param id
     * @throws IdNotExistDAOException if records with such id not exist in DB
     */
	void delete(long id) throws IdNotExistDAOException;

    /**
     * Find all Tags
     * @return list of Tags
     */
	List<Tag> findAll();

    /**
     * Read tag from DB by name
     * @param tagName
     * @return Tag
     * @throws TagNameNotExistDAOException if Tag with such name doesn't exist in DB
     */
	Tag readTagByName(String tagName) throws TagNameNotExistDAOException;

}
