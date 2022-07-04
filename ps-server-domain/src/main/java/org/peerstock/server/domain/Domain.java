package org.peerstock.server.domain;

import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "lynx_domain")
@EntityListeners({AuditingEntityListener.class})
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Column(name = "domain", unique = true, nullable = false)
    private String domain;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private User user;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class Builder {
        public Domain build() {
            Domain domain = new Domain();
            domain.setDomain(this.domain);
            domain.setId(this.id);
            domain.setUser(this.user);
            return domain;
        }

        private UUID id;

        private String domain;

        private User user;

        public Builder setId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder setDomain(String domain) {
            this.domain = domain;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }
    }

    // FIXME: Костыль при работе с ModelMapper
    @Override
    public String toString() {
        return domain;
    }
}
