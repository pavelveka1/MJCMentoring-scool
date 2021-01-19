package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificate implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer price;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer duration;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime createDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime lastUpdateDate;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Tag> tags = new ArrayList<>();

}
