package uet.soa.pastebin.domain.event;

import lombok.Getter;

@Getter
public class PasteAccessedEvent {
    private String pasteUrl;

    public PasteAccessedEvent(String pasteUrl) {
        this.pasteUrl = pasteUrl;
    }

}
