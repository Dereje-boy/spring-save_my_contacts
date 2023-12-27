package com.dit.save_my_contacts.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserModel {
    private int userID;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String avatar;
    private MultipartFile multipartFile;
}
