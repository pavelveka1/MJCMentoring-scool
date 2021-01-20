package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class entity GiftCertificate
 *
 * @author Pavel Veka
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id of GiftCertificate
     */
    private long id;

    /**
     * name of GiftCertificate
     */
    private String name;

    /**
     * description of GiftCertificate
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String description;

    /**
     * price of GiftCertificate
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer price;

    /**
     * duratuin in days
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer duration;

    /**
     * date of creation of GiftCertificate
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime createDate;

    /**
     * last date of updating of GiftCertificate
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime lastUpdateDate;

    /**
     * list of tags linked with GiftCertificate
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Tag> tags = new ArrayList<>();

}
