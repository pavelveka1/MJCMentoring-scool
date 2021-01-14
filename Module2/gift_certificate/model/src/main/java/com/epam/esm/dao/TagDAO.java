package com.epam.esm.dao;

import java.util.List;

import com.epam.esm.entity.Tag;

public interface TagDAO {
	
	Tag create(Tag tag);
	Tag read(long id);
	boolean delete(long id);
	List<Tag> findAll();

}
