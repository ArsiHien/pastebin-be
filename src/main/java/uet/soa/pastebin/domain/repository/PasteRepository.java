package uet.soa.pastebin.domain.repository;

import uet.soa.pastebin.domain.model.Paste;

import java.util.Optional;

public interface PasteRepository {
    void save(Paste paste);

    Optional<Paste> findById(String id);

    Optional<Paste> findByUrl(String url);

    void delete(Paste paste);
}
