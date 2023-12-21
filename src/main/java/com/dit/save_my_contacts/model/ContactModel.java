package com.dit.save_my_contacts.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactModel {
    private int id;
    private String firstname;
    private String lastname;
    private String countryName;
    private int countryCode;
    private long phoneNumber;
}
