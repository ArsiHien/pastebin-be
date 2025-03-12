package uet.soa.pastebin.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "pastes")
public class JpaPaste {
    @Id
    String id;
    @Lob
    String content;
    String url;
    LocalDateTime createdAt;

    int viewCount;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "expiration_policy_id")
    JpaExpirationPolicy expirationPolicy;
}
