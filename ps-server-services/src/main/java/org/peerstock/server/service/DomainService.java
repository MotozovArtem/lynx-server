package org.peerstock.server.service;

import org.peerstock.server.domain.Domain;
import org.peerstock.server.repository.DomainRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DomainService {

    public static final Logger log = LoggerFactory.getLogger(DomainService.class);

    private final DomainRepository domainRepository;

    @Autowired
    public DomainService(DomainRepository domainRepository) {
        this.domainRepository = domainRepository;
    }

    public UUID saveDomain(Domain domain) {
        Domain findDomain = domainRepository.findDomainByDomain(domain.getDomain());

        if (findDomain != null) {
            log.debug("Find existing domain with same domain name {}. Returning existing domain ID", domain.getDomain());
            return findDomain.getId();
        }
        log.debug("Creating new domain {} for user {}", domain.getDomain(), domain.getUser().getLogin());
        Domain savedDomain = domainRepository.save(domain);
        return savedDomain.getId();
    }
}
