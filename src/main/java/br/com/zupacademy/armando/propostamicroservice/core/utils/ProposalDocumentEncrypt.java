package br.com.zupacademy.armando.propostamicroservice.core.utils;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

public abstract class ProposalDocumentEncrypt {

    private final static int DEFAULT_SALT = 12;

    public static String genSecureHash(String secret) {
        TextEncryptor encryptor = Encryptors.queryableText("document", "5c0744940b5c369b");
        return encryptor.encrypt(secret);
    }

}
