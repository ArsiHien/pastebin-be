package uet.soa.pastebin.domain.repository;

import uet.soa.pastebin.domain.model.paste.Paste;

import java.util.List;
import java.util.Optional;

public interface PasteRepository {
    void save(Paste paste);

    Optional<Paste> findById(String id);

    Optional<Paste> findByUrl(String url);

    List<Paste> findExpiredPastes();

    void update(Paste paste);

    void delete(Paste paste);


}
