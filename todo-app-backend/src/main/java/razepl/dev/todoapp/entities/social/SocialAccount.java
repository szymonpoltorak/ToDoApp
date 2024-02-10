package razepl.dev.todoapp.entities.social;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import razepl.dev.todoapp.entities.user.User;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long socialAccountId;

    @Enumerated(value = EnumType.STRING)
    private SocialPlatform socialPlatform;

    @NotBlank
    private String socialName;

    @NotBlank
    private String socialLink;

    @ManyToOne
    private User user;
}
