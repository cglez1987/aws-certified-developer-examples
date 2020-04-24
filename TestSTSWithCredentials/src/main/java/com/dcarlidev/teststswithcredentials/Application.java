/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dcarlidev.teststswithcredentials;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleResult;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;
import com.amazonaws.services.securitytoken.model.GetSessionTokenResult;

/**
 *
 * @author carlos
 */
public class Application {

    public static void main(String... args) {
        AwsClientBuilder.EndpointConfiguration config = new AwsClientBuilder.EndpointConfiguration("sts.amazonaws.com", "us-east-1");
        AWSSecurityTokenService stsClient = AWSSecurityTokenServiceClientBuilder.standard().withEndpointConfiguration(config).build();
        GetSessionTokenRequest sessionRequest = new GetSessionTokenRequest();
        sessionRequest.setDurationSeconds(5000);
        GetSessionTokenResult resultToken = stsClient.getSessionToken(sessionRequest);
        Credentials credentials = resultToken.getCredentials();
        System.out.println("AccessKeyId: " + credentials.getAccessKeyId());
        System.out.println("SecretAccessKey: " + credentials.getSecretAccessKey());
        System.out.println("SessionToken: " + credentials.getSessionToken());
//        
        AssumeRoleRequest req = new AssumeRoleRequest().withRoleArn("arn:aws:iam::" + "aws-account" + ":role/S3fullAccessFromEC2").withRoleSessionName("testAssumeRole");
        AssumeRoleResult response = stsClient.assumeRole(req);
        Credentials credentials2 = response.getCredentials();
        System.out.println("AccessKeyId: " + credentials2.getAccessKeyId());
        System.out.println("SecretAccessKey: " + credentials2.getSecretAccessKey());
        System.out.println("SessionToken: " + credentials2.getSessionToken());
        BasicSessionCredentials sessionCredentials = new BasicSessionCredentials(
                credentials2.getAccessKeyId(), credentials2.getSecretAccessKey(), credentials2.getSessionToken());
        AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(sessionCredentials)).build();
        s3.listBuckets().forEach(bucket -> System.out.println("Bucket name: " + bucket.getName()));

    }

}
