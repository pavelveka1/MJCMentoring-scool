package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<GiftCertificate> certificates = new ArrayList<>();

    public Tag(long id, String name) {
        this.id = id;
        this.name = name;
    }

}
