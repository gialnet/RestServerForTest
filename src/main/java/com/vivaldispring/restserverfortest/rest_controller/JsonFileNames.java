package com.vivaldispring.restserverfortest.rest_controller;


public class JsonFileNames {

    /**
     *
     * @param number
     * @return
     */
    public static String getNameByNumber(String number){

        String fileName = null;

        switch (number){
            case "00": fileName="expertv2.json";
                break;
            case "01": fileName="sygmav2.json";
                break;
            case "02": fileName="opsysv2.json";
                break;
            case "03": fileName="pdmv2.json";
                break;
            case "04": fileName="legacyv2.json";
                break;
            case "05": fileName="commonv2.json";
                break;
            case "06": fileName="corev2.json";
                break;
            case "07": fileName="eacv2.json";
                break;
            case "08": fileName="pocv2.json";
                break;
            case "09": fileName="sepv2.json";
                break;
            case "10": fileName="opsystr3v2.json";
                break;
            case "11": fileName="osbv2.json";
                break;
        }
        return fileName;
    }

    /**
     *
     * @param name
     * @return
     */
    public static String getNameByName(String name){

        String fileName = null;

        switch (name){
            case "expert": fileName="expertv2.json";
                break;
            case "sygma": fileName="sygmav2.json";
                break;
            case "opsys": fileName="opsysv2.json";
                break;
            case "pdm": fileName="pdmv2.json";
                break;
            case "legacy": fileName="legacyv2.json";
                break;
            case "common": fileName="commonv2.json";
                break;
            case "core": fileName="corev2.json";
                break;
            case "eac": fileName="eacv2.json";
                break;
            case "poc": fileName="pocv2.json";
                break;
            case "sep": fileName="sepv2.json";
                break;
            case "opsystr3": fileName="opsystr3v2.json";
                break;
            case "osb": fileName="osbv2.json";
                break;
        }
        return fileName;
    }
}
