package uet.soa.pastebin.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "expiration_policies")
public class JpaExpirationPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    PolicyType policyType;
    String duration;

    public enum PolicyType {
        TIMED, NEVER, BURN_AFTER_READ
    }
}
