package com.epam.esm.service;

import java.util.List;

import com.epam.esm.entity.Tag;

public interface TagService {

	Tag create(Tag tag);
	Tag read(long id);
	boolean delete(long id);
	List<Tag> findAll();
}
