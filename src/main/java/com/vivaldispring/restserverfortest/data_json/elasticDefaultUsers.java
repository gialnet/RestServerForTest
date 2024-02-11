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
public class elasticDefaultUsers {

    private String apmSystem;
    private String logstashSystem;
    private String beatsSystem;
    private String remoteMonitoringUser;
    private String kibanaSystem;
    private String elastic;
    private String kibana;
}
