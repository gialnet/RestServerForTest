package com.vivaldispring.restserverfortest.rest_controller;

import com.vivaldispring.restserverfortest.data_json.DomainInfo;
import com.vivaldispring.restserverfortest.data_json.EnvironmentInfo;
import com.vivaldispring.restserverfortest.services.LoadJsonFilesToJavaClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ManageJsonController {

    private final LoadJsonFilesToJavaClass filesToJavaClass;
    Map<String, Object> response = new HashMap<>();

    List<EnvironmentInfo> infoList = new ArrayList<>();

    public ManageJsonController(LoadJsonFilesToJavaClass filesToJavaClass) {
        this.filesToJavaClass = filesToJavaClass;
    }

    /**
     * Domain update port number by folder name. /update/"new port number"
     *
     * @param port
     * @param bodyPatch RequestBodyPatch {
     *     String Domain;
     *     String Environment_folder;
     * }
     * @return ResponseEntity
     * @throws IOException
     */
    @PatchMapping("/update/{port}")
    public ResponseEntity<?> updatePort(@PathVariable(value = "port") String port,
                                        @RequestBody RequestBodyPatch bodyPatch) throws IOException {

        response.clear();

        // check if bodyPatch exist
        String domain_code = bodyPatch.getDomain();

        // select file in function of domain code
        DomainInfo domainInfo = filesToJavaClass.loadDomainInfo("expertv2.json");

        // send invalid resource if not exist
        if (domainInfo==null)
        {
            response.put("domain code not found: ", domain_code);
            return new ResponseEntity<>(response, HttpStatus.FAILED_DEPENDENCY);
        }

        // update json file with new port value
        infoList = domainInfo.getData().stream()
                .map(i-> { if( i.getEnvironment_folder()
                                .equals(bodyPatch.getEnvironment_folder()))

                                return EnvironmentInfo.builder()
                                        .Environment(i.getEnvironment())
                                        .Environment_folder(i.getEnvironment_folder())
                                        .Admin_server_name(i.getAdmin_server_name())
                                        .Admin_server_port(port).build();
                    return i;
                }).toList();

        domainInfo.setData(infoList);

        // save to json file

        response.put("bodyPatch: ", bodyPatch);
        response.put("New port: ", port);
        response.put("New JSON file: ", domainInfo);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
