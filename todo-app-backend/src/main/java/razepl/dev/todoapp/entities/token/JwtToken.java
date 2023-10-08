package razepl.dev.todoapp.entities.token;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import razepl.dev.todoapp.entities.user.User;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Tokens")
public class JwtToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long tokenId;

    @NotNull
    @Column(unique = true)
    private String token;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private boolean isExpired;

    private boolean isRevoked;
}
