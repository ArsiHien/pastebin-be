package uet.soa.pastebin.domain.model;

import java.util.UUID;

public class URL {
    private String value;

    private URL(String value) {
        this.value = value;
    }

    public static URL generate() {
        String randomStr = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5);
        return new URL(randomStr);
    }

    public boolean validate() {
        return true;
    }

    @Override
    public String toString() {
        return value;
    }
}
