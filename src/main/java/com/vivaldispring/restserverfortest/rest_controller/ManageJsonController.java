package com.vivaldispring.restserverfortest.rest_controller;

import com.vivaldispring.restserverfortest.data_json.DomainInfo;
import com.vivaldispring.restserverfortest.data_json.EnvironmentInfo;
import com.vivaldispring.restserverfortest.services.LoadJsonFilesToJavaClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
     * @param port new port number
     * @param bodyPatch RequestBodyPatch {
     *     String Domain;
     *     String Environment_folder;
     * }
     * @return ResponseEntity
     * @throws IOException not file found
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

    /**
     * Update server, port and folder by folder name in an determined environment
     * @param folder the environment folder to change for a new one
     * @param bodyPut RequestBodyPut {
     *     String Domain;
     *     String Admin_server_name;
     *     String Admin_server_port;
     *     String Environment_folder;
     * }
     * @return ResponseEntity
     * @throws IOException file not found
     */
    @PutMapping("/change/{folder}")
    public ResponseEntity<?> updateFolder(@PathVariable(value = "folder") String folder,
                                        @RequestBody RequestBodyPut bodyPut) throws IOException {

        response.clear();

        // check if bodyPatch exist
        String domain_code = bodyPut.getDomain();

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
                        .equals(folder))

                    return EnvironmentInfo.builder()
                            .Environment(i.getEnvironment())
                            .Environment_folder(bodyPut.getEnvironment_folder())
                            .Admin_server_name(bodyPut.getAdmin_server_name())
                            .Admin_server_port(bodyPut.getAdmin_server_port()).build();
                    return i;
                }).toList();

        domainInfo.setData(infoList);

        // save to json file

        response.put("bodyPatch: ", bodyPut);
        response.put("New folder: ", folder);
        response.put("New JSON file: ", domainInfo);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{domain}/{folder}")
    public ResponseEntity<?> deleteFolder(
            @PathVariable(value = "domain") String domain,
            @PathVariable(value = "folder") String folder,
            @RequestHeader HttpHeaders headers
            ) throws IOException {

        response.clear();

        List<String> auth = headers.get(HttpHeaders.AUTHORIZATION);

        String base64Credentials = auth.get(0).substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);

        response.put("user: ", values);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
