package com.vivaldispring.restserverfortest.data_json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DomainInfo {

    private String DomainName;
    private String DomainNumber;
    private List<EnvironmentInfo> data;

}
