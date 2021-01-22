package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * class entity Tag
 *
 * @author Pavel Veka
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id of Tag
     */
    private long id;

    /**
     * name of Tag
     */
    @NotNull
    @Size(min = 2, max = 45)
    private String name;

    /**
     * list of GiftCertificates linked with Tag
     */
    @NotEmpty
    private List<GiftCertificate> certificates = new ArrayList<>();

    /**
     * Constructor with two parameter
     *
     * @param id
     * @param name
     */
    public Tag(long id, String name) {
        this.id = id;
        this.name = name;
    }

}
