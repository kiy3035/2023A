package teamgreen.abc.config;

import teamgreen.abc.domain.User1;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class PrincipalDetails implements UserDetails {

    private User1 user1;

    public PrincipalDetails(User1 user) {
        this.user1 = user;
    }

    @Override
    public String getPassword() {
        return user1.getPassword();
    }

    @Override
    public String getUsername() {return user1.getUserid();    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // 해당 User의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(() -> "ROLE_" + user1.getUser_role().toString());
        return collectors;
    }

}