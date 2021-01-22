package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
    @NotNull
    @Size(min = 2, max = 45)
    private String name;

    /**
     * description of GiftCertificate
     */
    @NotNull
    @Size(min = 2, max = 300)
    private String description;

    /**
     * price of GiftCertificate
     */
    @NotNull
    @Min(1)
    private Integer price;

    /**
     * duratuin in days
     */
    @NotNull
    @Min(1)
    private Integer duration;

    /**
     * date of creation of GiftCertificate
     */
    @NotNull
    private LocalDateTime createDate;

    /**
     * last date of updating of GiftCertificate
     */
    @NotNull
    private LocalDateTime lastUpdateDate;

    /**
     * list of tags linked with GiftCertificate
     */
    @NotEmpty
    private List<Tag> tags = new ArrayList<>();

}
