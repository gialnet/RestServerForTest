package com.vivaldispring.restserverfortest.services;

import com.google.gson.Gson;
import com.vivaldispring.restserverfortest.data.AppUser;
import com.vivaldispring.restserverfortest.repositories.KVRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class ServicesUsers {

    private Gson gson = new Gson();
    private final KVRepository<String, Object> repository;
    //private final LuceneWriteRepository luceneWriteRepository;
    private Optional<Object> user;
    private AppUser appUser;

    public ServicesUsers(KVRepository<String, Object> repository) {
        this.repository = repository;
    }

    /**
     *  Save IdUser usually email address antonio@gmial.com
     *  the value the object AppUser in JSON
     *
     *  Create two fields for customer sequence and invoice sequence
     *  sequence.customer.antonio@gmial.com    => 0
     *  sequence.invoice.antonio@gmial.com     => 0
     *
     *  Create an UUID version 4 for each user this number it is use for Lucene Index
     *  to create a different folder to contain the index
     *  /lucene8/customers/880d6dd3-9e6e-4c6a-8791-8c0d682e6d19
     *  /lucene8/invoices/880d6dd3-9e6e-4c6a-8791-8c0d682e6d19
     *
     * @param appUser
     */
    public boolean SaveUser(AppUser appUser) throws IOException {

        // Create two fields for customer sequence and invoice sequence
        repository.save("sequence.customer." + appUser.getIdUser().toLowerCase(), 0);
        repository.save("sequence.invoice." +  appUser.getIdUser().toLowerCase(), 0);

        // Write Lucene Index only for internal purpose use
        // luceneWriteRepository.WriteAppUserDocument(appUser);

        // save IdUser usually email address antonio@gmial.com
        return repository.save(appUser.getIdUser().toLowerCase(), gson.toJson(appUser));

    }

    /**
     *
     * @param KeyToFind
     * @return
     */
    public Optional<Object> FindKey(String KeyToFind){

        return repository.find(KeyToFind);

    }

    /**
     * Search for an user
     * @param IdUser
     */
    public Optional<Object> FindUser(String IdUser){

        return repository.find(IdUser.toLowerCase());

    }

    public AppUser getAppUser(){
        return this.appUser;
    }
    /**
     *
     * @param user
     * @param password
     * @return
     */
    public boolean UserAuthByUserAndPassword(String user, String password){

        var userdata = FindUser(user);
        if (userdata.isEmpty()){

            log.info("User doesn't exist");
            return false;
        }
        else {
            String value = (String) userdata.get();
            appUser=gson.fromJson(value, AppUser.class);
            if (appUser.getIdUser().equals(user)) {
                if (appUser.getPassword().equals(password)) {
                    log.info("User and password match");
                    return true;
                }
                else {
                    log.info("Password doesn't match match");
                    return false;
                }
            }
            else {
                log.info("User doesn't match match");
                return false;
            }

        }
    }

    /**
     * Exist true send an alert
     * 0 Ok
     * 1 email empty
     * 2 password empty
     * 3 fails RocksDB save
     * 5 user already exist
     *
     * @param appUser
     * @return int
     */
    public int RegisterUserIfNotAlreadyExist(AppUser appUser) throws IOException {

        // Not allow empty email user
        if (appUser.getIdUser().isEmpty())
            return 1;

        // Not allow empty password
        if (appUser.getPassword().isEmpty())
            return 2;

        user = FindUser(appUser.getIdUser());

        if (user.isEmpty()){

            if(SaveUser(appUser))
            {
                return 0;
            }
            else {
                log.info("SaveUser function fails RocksDB for '{}'", appUser);
                return 3;
            }

        }
        else {
            log.info("RegisterUserExist RocksDB said user already exist '{}'", appUser.getIdUser());
            return 5;
        }
    }
}
