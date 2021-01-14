package com.epam.esm.dao.impl;

import java.util.List;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificateMapper;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TagJDBCTemplate implements TagDAO{

	// write sql queries

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private GiftCertificateMapper giftCertificateMapper;

	@Autowired
	private TagMapper tagMapper;

	@Override
	public Tag create(Tag tag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tag read(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Tag> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
