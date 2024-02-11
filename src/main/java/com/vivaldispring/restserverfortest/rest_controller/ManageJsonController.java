package com.vivaldispring.restserverfortest.rest_controller;

import com.google.gson.Gson;
import com.vivaldispring.restserverfortest.data_json.DomainInfo;
import com.vivaldispring.restserverfortest.data_json.EnvironmentInfo;
import com.vivaldispring.restserverfortest.data_json.elasticDefaultUsers;
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

    AuthUserPass authUserPass = new AuthUserPass();
    public ManageJsonController(LoadJsonFilesToJavaClass filesToJavaClass) {
        this.filesToJavaClass = filesToJavaClass;
    }

    @PatchMapping("/elastic/update/{user}/{key}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "user") String user,
                                        @PathVariable(value = "key") String key) throws IOException {

        response.clear();

        // check if bodyPatch exist

        // select file in function of domain code
        elasticDefaultUsers defaultUsers = filesToJavaClass.loadDefaultUsers();

        // send invalid resource if not exist
        if (defaultUsers==null)
        {
            response.put("Unable to load json file elastic default user: ", "elastic_users.json");
            return new ResponseEntity<>(response, HttpStatus.FAILED_DEPENDENCY);
        }

        String jsonString = "{\"data\":{\"apm_system\":\"HFh9dpRVDob7lO7Tweru\",\"logstash_system\":\"ftdEvu2PFLeRatbK7H7I\",\"beats_system\":\"MXPYstz0fTRyCuv4XmoR\",\"remote_monitoring_user\":\"qbL4ZGHzNdb8VUNbx1mp\",\"kibana_system\":\"2A8N3LNHcTXw86OfK3cG\",\"elastic\":\"YxTGmigGkQ377qnNlv2G\",\"kibana\":\"2A8N3LNHcTXw86OfK3cG\"}}";
        Gson gson = new Gson();
        Map<String, Map<String, String>> map = gson.fromJson(jsonString, Map.class);
        Map<String, String> data = map.get("data");
        System.out.println(data);

        // save to json file

        //response.put("bodyPatch: ", bodyPatch);
        //response.put("New port: ", port);
        //response.put("New JSON file: ", domainInfo);

        return new ResponseEntity<>(response, HttpStatus.OK);
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
        if (domain_code==null){

            response.put("info: ", "You must provide domain and folder in the body request");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }


        // select file in function of domain code
        DomainInfo domainInfo = filesToJavaClass.loadDomainInfo(
                JsonFileNames.getNameByNumber(DomainCodes.EXPERT.code));

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

        if (domain_code==null){

            response.put("info: ", "You must provide domain, environment_folder, admin_server_port and admin_server_name in the body request");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        // select file in function of domain code
        DomainInfo domainInfo = filesToJavaClass.loadDomainInfo(
                JsonFileNames.getNameByNumber(DomainCodes.EXPERT.code));

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

    /**
     * Delete one server in the json file.
     *
     * @param domain domain code
     * @param folder folder name to be deleted
     * @param headers information about user and password
     * @return status of the operation
     * @throws IOException file not found
     */
    @DeleteMapping("/delete/{domain}/{folder}")
    public ResponseEntity<?> deleteFolder(
            @PathVariable(value = "domain") String domain,
            @PathVariable(value = "folder") String folder,
            @RequestHeader HttpHeaders headers
    ) throws IOException {

        response.clear();

        if (headers.get(HttpHeaders.AUTHORIZATION)!=null)
        {
            decodeAuth(headers.get(HttpHeaders.AUTHORIZATION).get(0));
        }
        else {

            response.put("Auth.: ", "You must provide an user and password valid");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }


        // select file in function of domain code
        DomainInfo domainInfo = filesToJavaClass.loadDomainInfo(
                JsonFileNames.getNameByNumber(domain));

        // send invalid resource if not exist
        if (domainInfo==null)
        {
            response.put("domain code not found: ", domain);
            return new ResponseEntity<>(response, HttpStatus.FAILED_DEPENDENCY);
        }

        // update json file with new port value
        infoList = domainInfo.getData().stream()
                .filter(i-> !i.getEnvironment_folder().equals(folder))
                .toList();

        domainInfo.setData(infoList);
        response.put("New JSON file: ", domainInfo);
        response.put("Auth.: ", authUserPass);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Decode user and password for basic authorization
     * @param auth HttpHeaders.AUTHORIZATION
     */
    private void decodeAuth(String auth){

        if (auth==null){

            authUserPass.setUser("");
            authUserPass.setPassword("");
            return;
        }
        String base64Credentials = auth.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);

        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        authUserPass.setUser(values[0]);
        authUserPass.setPassword(values[1]);
    }
}
