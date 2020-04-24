/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dcarlidev.testexamdevelopercertification;

import com.amazonaws.encryptionsdk.AwsCrypto;
import com.amazonaws.encryptionsdk.CryptoResult;
import com.amazonaws.encryptionsdk.kms.KmsMasterKey;
import com.amazonaws.encryptionsdk.kms.KmsMasterKeyProvider;
//import software.amazon.awssdk.core.SdkBytes;
//import software.amazon.awssdk.services.kms.KmsClient;
//import software.amazon.awssdk.services.kms.model.DecryptRequest;
//import software.amazon.awssdk.services.kms.model.DecryptResponse;
//import software.amazon.awssdk.services.kms.model.EncryptRequest;
//import software.amazon.awssdk.services.kms.model.EncryptResponse;
//import software.amazon.awssdk.services.kms.model.GenerateDataKeyRequest;
//import software.amazon.awssdk.services.kms.model.GenerateDataKeyResponse;

/**
 *
 * @author carlos
 */
public class Application {

    private final static String KEY_ARN = "arn:aws:kms:us-east-1:980729273837:key/97ee982d-e6de-4bb9-a22b-42541aeee279";

    public static void main(String... args) {
        String plaintext = "testing the encryption";

        /*
        Encrypt and decrypt using AWS KMS service directly.
         */
//        KmsClient client = KmsClient.create();
//        EncryptResponse response = client.encrypt(EncryptRequest.builder().
//                keyId(KEY_ARN).
//                plaintext(SdkBytes.fromUtf8String(plaintext)).build());
//        SdkBytes text_encrypted = response.ciphertextBlob();
//        System.out.println("Text Encrypted: " + Base64.getEncoder().encodeToString(text_encrypted.asByteArray()));
//
//        DecryptResponse decrypResponse = client.decrypt(DecryptRequest.builder().ciphertextBlob(text_encrypted).build());
//        SdkBytes text_decrypted = decrypResponse.plaintext();
//        System.out.println("TEXT DECRYPTED: " + new String(text_decrypted.asByteArray()));

        /*
        Encrypt and decrypt using AWS Encryption SDK
         */
        AwsCrypto crypto = new AwsCrypto();
        KmsMasterKeyProvider provider = KmsMasterKeyProvider.builder().withDefaultRegion("us-east-1").
                withKeysForEncryption(KEY_ARN).build();
        CryptoResult<byte[], KmsMasterKey> resultEncryp = crypto.encryptData(provider, plaintext.getBytes());
        final String ciphertext = new String(resultEncryp.getResult());

        System.out.println("Using Crypto SDK - Ciphertext: " + ciphertext);

        final CryptoResult<byte[], KmsMasterKey> result = crypto.decryptData(provider, resultEncryp.getResult());

        System.out.println("Using Crypto SDK - Ciphertext: " + new String(result.getResult()));

    }

}
