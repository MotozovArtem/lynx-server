package org.peerstock.server.domain;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "ps_user")
@EntityListeners({AuditingEntityListener.class})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", targetEntity = Domain.class)
    private Set<Domain> domain;

    @Column(name = "last_time_active")
    private LocalDateTime lastTimeActive;

    @Column(name = "online")
    private Boolean online;

    @Column(name = "creation_time")
    @CreatedDate
    private LocalDateTime creationTime;

    @Version
    @Column(name = "ts")
    private Long ts;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Domain> getDomain() {
        return domain;
    }

    public void setDomain(Set<Domain> domain) {
        this.domain = domain;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public LocalDateTime getLastTimeActive() {
        return lastTimeActive;
    }

    public void setLastTimeActive(LocalDateTime lastTimeActive) {
        this.lastTimeActive = lastTimeActive;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @PrePersist
    protected void onCreate() {
        if (ts == null) {
            ts = 0L;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        ts++;
    }

    public static class Builder {

        public User build() {
            User user = new User();
            user.setId(id);
            user.setLogin(login);
            user.setName(name);
            user.setPassword(password);
            user.setOnline(online);
            user.setLastTimeActive(lastTimeActive);
            user.setCreationTime(creationTime);
            user.setTs(ts);
            return user;
        }

        private UUID id;

        private String login;

        private String name;

        private String password;

        private Boolean online;

        private LocalDateTime lastTimeActive;

        private LocalDateTime creationTime;

        private Long ts;

        public Builder setId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder setLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setOnline(Boolean online) {
            this.online = online;
            return this;
        }

        public Builder setLastTimeActive(LocalDateTime lastTimeActive) {
            this.lastTimeActive = lastTimeActive;
            return this;
        }

        public Builder setCreationTime(LocalDateTime creationTime) {
            this.creationTime = creationTime;
            return this;
        }

        public Builder setTs(Long ts) {
            this.ts = ts;
            return this;
        }
    }
}
