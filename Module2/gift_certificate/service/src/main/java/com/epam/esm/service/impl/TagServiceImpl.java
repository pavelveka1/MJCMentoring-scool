package com.epam.esm.service.impl;

import java.util.List;

import com.epam.esm.dao.impl.TagJDBCTemplate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagJDBCTemplate tagJDBCTemplate;

    @Override
    @Transactional
    public Tag create(Tag tag) {
        // TODO Auto-generated method stub
        return tagJDBCTemplate.create(tag);
    }

    @Override
    @Transactional
    public Tag read(long id) {
        // TODO Auto-generated method stub
        return tagJDBCTemplate.read(id);
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        // TODO Auto-generated method stub
        return tagJDBCTemplate.delete(id);
    }

    @Override
    @Transactional
    public List<Tag> findAll() {
        // TODO Auto-generated method stub
        return tagJDBCTemplate.findAll();
    }

}
