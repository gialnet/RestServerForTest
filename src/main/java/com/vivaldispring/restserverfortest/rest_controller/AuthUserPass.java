package com.vivaldispring.restserverfortest.rest_controller;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class AuthUserPass {

    String user;
    String password;
}
