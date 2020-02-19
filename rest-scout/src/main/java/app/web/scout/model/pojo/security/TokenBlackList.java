package app.web.scout.model.pojo.security;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
public class TokenBlackList {

    @Id
    @Getter @Setter private String jti;
    @Getter @Setter private Integer userId;
    @Getter @Setter private Long expires;
    @Getter @Setter private boolean isBlackListed;

    public TokenBlackList(Integer userId, String jti, Long expires) {
        this.jti = jti;
        this.userId = userId;
        this.expires = expires;
    }

}
