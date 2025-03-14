package uet.soa.pastebin.domain.model.paste;

import java.util.UUID;

public class URL {
    private final String value;

    private URL(String value) {
        this.value = value;
    }

    public static URL generate() {
        String randomStr = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5);
        return new URL(randomStr);
    }

    public static URL of(String value) {
        return new URL(value);
    }

    public boolean validate() {
        return true;
    }

    @Override
    public String toString() {
        return value;
    }
}
