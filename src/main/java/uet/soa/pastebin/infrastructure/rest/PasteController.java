package uet.soa.pastebin.infrastructure.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uet.soa.pastebin.application.dto.CreatePasteRequest;
import uet.soa.pastebin.application.dto.CreatePasteResponse;
import uet.soa.pastebin.application.dto.RetrievePasteResponse;
import uet.soa.pastebin.application.usecase.CreatePasteUseCase;
import uet.soa.pastebin.application.usecase.RetrievePasteUseCase;

@RestController
@RequestMapping("/api/pastes")
@RequiredArgsConstructor
public class PasteController {
    private final CreatePasteUseCase createPasteUseCase;
    private final RetrievePasteUseCase retrievePasteUseCase;

    @PostMapping
    public ResponseEntity<CreatePasteResponse> createPaste(@RequestBody CreatePasteRequest request) {
        CreatePasteResponse response = createPasteUseCase.execute(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{url}")
    public ResponseEntity<RetrievePasteResponse> getPaste(@PathVariable String url) {
        RetrievePasteResponse response = retrievePasteUseCase.execute(url);
        return ResponseEntity.ok(response);
    }
}
