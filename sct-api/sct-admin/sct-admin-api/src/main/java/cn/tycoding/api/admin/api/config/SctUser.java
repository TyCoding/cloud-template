package cn.tycoding.api.admin.api.config;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 用于封装UserDetails的User扩展信息
 *
 * @author tycoding
 * @date 2019-05-24
 */
public class SctUser extends User {

    /**
     * 用户ID
     */
    @Getter
    private Long id;

    public SctUser(Long id, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
    }
}
