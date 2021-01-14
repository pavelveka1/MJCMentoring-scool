package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private List<GiftCertificate> sertificates = new ArrayList<GiftCertificate>();

    public Tag(long id, String name) {
        this.id = id;
        this.name = name;
    }

}
