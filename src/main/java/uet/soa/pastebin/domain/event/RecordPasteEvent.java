package uet.soa.pastebin.domain.event;

import lombok.Getter;
import uet.soa.pastebin.domain.model.paste.Paste;

@Getter
public class RecordPasteEvent {
    private Paste paste;

    public RecordPasteEvent(Paste paste) {
        this.paste = paste;
    }

}
