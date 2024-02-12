package com.vivaldispring.restserverfortest.services;

import com.google.gson.Gson;
import com.vivaldispring.restserverfortest.data_json.DomainInfo;
import com.vivaldispring.restserverfortest.data_json.EnvironmentInfo;
import com.vivaldispring.restserverfortest.data_json.elasticDefaultUsers;
import org.apache.commons.io.IOUtils;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LoadJsonFilesToJavaClass {

    private final ResourceLoader resourceLoader;
    private Gson gson = new Gson();
    private DomainInfo  domainInfo;

    private elasticDefaultUsers defaultUsers;

    public LoadJsonFilesToJavaClass(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    /**
     * Load json file from the file system and put into List of java class domain info
     *
     * @param domainFileName
     * @return
     * @throws IOException
     */
    @RegisterReflectionForBinding({DomainInfo.class, EnvironmentInfo.class})
    public DomainInfo loadDomainInfo(String domainFileName) throws IOException {

        Resource resource = resourceLoader.getResource("classpath:/static/json/".concat(domainFileName));
        InputStream jsonfile = resource.getInputStream();
        String stringFile2 = IOUtils.toString(jsonfile, StandardCharsets.UTF_8);

        domainInfo = gson.fromJson(stringFile2, DomainInfo.class);

        return domainInfo;
    }

    /**
     * load json file elastic default users
     * @return
     * @throws IOException
     */
    public elasticDefaultUsers loadDefaultUsers() throws IOException {

        Resource resource = resourceLoader.getResource("classpath:/static/json/elastic_users.json");
        InputStream jsonfile = resource.getInputStream();
        String stringFile2 = IOUtils.toString(jsonfile, StandardCharsets.UTF_8);

        defaultUsers = gson.fromJson(stringFile2, elasticDefaultUsers.class);

        return defaultUsers;

    }

    public void toMap(){
        String jsonString = "{\"data\":{\"apm_system\":\"HFh9dpRVDob7lO7Tweru\",\"logstash_system\":\"ftdEvu2PFLeRatbK7H7I\",\"beats_system\":\"MXPYstz0fTRyCuv4XmoR\",\"remote_monitoring_user\":\"qbL4ZGHzNdb8VUNbx1mp\",\"kibana_system\":\"2A8N3LNHcTXw86OfK3cG\",\"elastic\":\"YxTGmigGkQ377qnNlv2G\",\"kibana\":\"2A8N3LNHcTXw86OfK3cG\"}}";
        Gson gson = new Gson();
        Map<String, Map<String, String>> map = gson.fromJson(jsonString, Map.class);
        Map<String, String> data = map.get("data");
        System.out.println(data);
    }
    /**
     *
     * @param domainFileName
     * @return
     * @throws IOException
     */
    public List<EnvironmentInfo> loadDomain(String domainFileName) throws IOException {

        List<EnvironmentInfo> environmentInfos = new ArrayList<>();

        domainInfo = loadDomainInfo(domainFileName);

        domainInfo.getData().stream().forEach(Items -> {environmentInfos.add(Items);});

        return environmentInfos;
    }
}
