package com.vivaldispring.restserverfortest.services;

import com.google.gson.Gson;
import com.vivaldispring.restserverfortest.data_json.DomainInfo;
import com.vivaldispring.restserverfortest.data_json.EnvironmentInfo;
import com.vivaldispring.restserverfortest.repositories.DomainRepo;
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

@Service
public class LoadJsonFilesToJavaClass {

    private final ResourceLoader resourceLoader;
    private final DomainRepo domainRepo;
    private Gson gson = new Gson();
    private DomainInfo  domainInfo;

    public LoadJsonFilesToJavaClass(ResourceLoader resourceLoader, DomainRepo domainRepo) {
        this.resourceLoader = resourceLoader;
        this.domainRepo = domainRepo;
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
