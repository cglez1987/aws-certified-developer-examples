/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dcarlidev.testsmparameterstore;

import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParametersByPathRequest;
import software.amazon.awssdk.services.ssm.model.GetParametersByPathResponse;

/**
 *
 * @author carlos
 */
public class Application {

    public static void main(String... args) {
        SsmClient client = SsmClient.create();
        GetParametersByPathResponse response = client.getParametersByPath(GetParametersByPathRequest.builder().path("/prod/").withDecryption(true).recursive(true).build());
        response.parameters().forEach(param -> {
            System.out.println(param.name() + ": " + param.value());
        });
    }

}
