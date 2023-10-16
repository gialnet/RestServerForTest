package com.vivaldispring.restserverfortest.rest_controller;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RequestBodyPatch {
    String Domain;
    String Environment_folder;
}
