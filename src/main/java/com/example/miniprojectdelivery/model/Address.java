package com.example.miniprojectdelivery.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private String address; //지번 주소

    private String street;  //도로명 주소

    public Address(String address, String street) {
        this.address = address;
        this.street = street;
    }
}
