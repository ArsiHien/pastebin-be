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
    @Column(name = "url", nullable = false, unique = true)
    String url;

    @Lob
    String content;
    LocalDateTime createdAt;
    int viewCount;
    boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expiration_policy_id")
    JpaExpirationPolicy expirationPolicy;
}
