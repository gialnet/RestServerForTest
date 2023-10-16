package com.vivaldispring.restserverfortest.rest_controller;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RequestBodyPut {
    String Domain;
    String Admin_server_name;
    String Admin_server_port;
    String Environment_folder;
}
