package com.olamireDev.prophiusapi.payload.response;

import com.sun.istack.Nullable;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class CreatedUserDTO {

    private Long id;

    private String userName;

    @Nullable
    private String token;

}
