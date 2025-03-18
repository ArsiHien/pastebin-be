package uet.soa.pastebin.infrastructure.persistence.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RedisPaste {
    String url;
    String content;
    String createdAt;
    String expirationPolicyType;
    String expirationDuration;
}
