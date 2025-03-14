package uet.soa.pastebin.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "records")
public class JpaRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false)
    LocalDateTime viewTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paste_url")
    @OnDelete(action = OnDeleteAction.CASCADE)
    JpaPaste paste;
}