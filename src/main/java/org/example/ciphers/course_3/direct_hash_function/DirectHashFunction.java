package org.example.ciphers.course_3.direct_hash_function;

import java.util.Objects;

public class DirectHashFunction {

    public Integer encrypt(String plainText) {
        return plainText.hashCode();
    }

    public Boolean validate(String plainText, Integer hash) {
        return Objects.equals(plainText.hashCode(), hash);
    }

}
