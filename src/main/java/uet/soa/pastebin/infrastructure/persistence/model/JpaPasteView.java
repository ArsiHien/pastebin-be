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
@Table(name = "paste_views")
public class JpaPasteView {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false)
    LocalDateTime viewTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paste_id", insertable = false, updatable = false)
    JpaPaste paste;
}