package uet.soa.pastebin.infrastructure.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uet.soa.pastebin.application.dto.*;
import uet.soa.pastebin.application.usecase.CreatePasteUseCase;
import uet.soa.pastebin.application.usecase.RetrievePasteUseCase;

@RestController
@RequestMapping("/api/pastes")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class PasteController {
    private final CreatePasteUseCase createPasteUseCase;
    private final RetrievePasteUseCase retrievePasteUseCase;

    @PostMapping
    public ResponseEntity<CreatePasteResponse> createPaste(@RequestBody CreatePasteRequest request) {
        CreatePasteResponse response = createPasteUseCase.execute(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{url}/policy")
    public ResponseEntity<String> previewContent(@PathVariable String url) {
        return ResponseEntity.ok(retrievePasteUseCase.getPolicy(url));
    }

    @GetMapping("/{url}/content")
    public ResponseEntity<RetrievePasteResponse> getPaste(@PathVariable String url) {
        RetrievePasteResponse response = retrievePasteUseCase.getPaste(url);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{url}/stats")
    public ResponseEntity<StatsResponse> getStats(@PathVariable String url) {
        StatsResponse response = retrievePasteUseCase.getStats(url);
        return ResponseEntity.ok(response);
    }
}
