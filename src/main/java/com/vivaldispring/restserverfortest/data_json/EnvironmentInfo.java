package com.vivaldispring.restserverfortest.data_json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnvironmentInfo {
    private String Environment;
    private String Environment_folder;
    private String Admin_server_name;
    private String Admin_server_port;
}
