package com.olamireDev.prophiusapi.payload.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class EditUserDTO {

    private String userName;
    private String password;
    private String profilePicture;

}
