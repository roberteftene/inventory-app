package com.roberteftene.dc.invetory_service.domain.models;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Address {
    private String street;
    private String city;
    private String zipCode;
}
