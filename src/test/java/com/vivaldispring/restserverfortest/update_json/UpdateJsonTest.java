package com.vivaldispring.restserverfortest.update_json;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Array;

@SpringBootTest
public class UpdateJsonTest {

    /*
    GET: listado de recursos. Detalle de un solo recurso.
    POST: creación de un recurso.
    PUT: modificación total de un recurso.
    PATCH: modificación parcial de un recurso.
    DELETE: eliminación de un recurso. En muchas ocasiones es un soft delete,
    es decir, no se elimina definitivamente un recurso sino que únicamente es marcado como eliminado o desactivado.
     */

    String[] environmt = new String[]{"I", "D", "T", "A", "P", "T", "D"};
    String payload = """
            {
                "Domain": "00",
                "Environment_folder": "inta00",
                "Admin_server_name": "bamberg.cc.cec.eu.int",
                "Admin_server_port": 15020
            }
            """;
    @Test
    public void patchJsonTest(){

    }
}
