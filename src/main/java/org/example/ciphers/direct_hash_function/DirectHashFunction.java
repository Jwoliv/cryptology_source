package org.example.ciphers.direct_hash_function;

import java.util.Objects;

public class DirectHashFunction {

    public Integer encrypt(String plainText) {
        return plainText.hashCode();
    }

    public Boolean validate(String plainText, Integer hash) {
        return Objects.nonNull(plainText) && plainText.hashCode() == hash;
    }

}
