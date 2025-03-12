package uet.soa.pastebin.domain.model.paste;

public class Content {
    private final String value;

    public Content(String value) {
        this.value = value;
    }

    public static Content of(String content) {
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null");
        }
        return new Content(content);
    }

    public String reveal() {
        return value;
    }
}
