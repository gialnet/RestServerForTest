package com.vivaldispring.restserverfortest.rest_controller;

public enum DomainCodes {

    EXPERT("00"),
    SYGMA("01"),
    OPSYS("03");

    String code;

    DomainCodes(String code){
        this.code=code;
    }
}
