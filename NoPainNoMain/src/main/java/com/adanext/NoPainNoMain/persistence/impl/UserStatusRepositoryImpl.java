package com.adanext.NoPainNoMain.persistence.impl;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.adanext.NoPainNoMain.domain.types.UserStatus;
import com.adanext.NoPainNoMain.mapper.types.UserStatusMapper;
import com.adanext.NoPainNoMain.persistence.types.UserStatusEntity;
import com.adanext.NoPainNoMain.persistence.repositories.UserStatusJpaRepository;
import com.adanext.NoPainNoMain.domain.repository.UserStatusRepository;


@Repository
public class UserStatusRepositoryImpl implements UserStatusRepository {
    
    private final UserStatusJpaRepository repository;

    public UserStatusRepositoryImpl(UserStatusJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserStatus save(UserStatus userStatus) {
        if (userStatus == null) return null;

        UserStatusEntity entity = UserStatusMapper.toEntity(userStatus);
        UserStatusEntity saved = repository.save(entity);
        return UserStatusMapper.toDomain(saved);
    }

    @Override
    public Optional<UserStatus> findById(Integer id) {
        return repository.findById(id)
            .map(UserStatusMapper::toDomain);
    }

    @Override
    public List<UserStatus> findAll() {
        return repository.findAll().stream()
            .map(UserStatusMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
