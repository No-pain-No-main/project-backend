package com.adanext.NoPainNoMain.persistence.Repositories;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.adanext.NoPainNoMain.domain.Administrator;
import com.adanext.NoPainNoMain.domain.repository.AdministratorRepository;
import com.adanext.NoPainNoMain.persistence.AdministratorEntity;
import com.adanext.NoPainNoMain.persistence.mappers.AdministratorMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class AdministratorRepositoryImpl implements AdministratorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Administrator save(Administrator administrator) {
        if (administrator == null) return null;

        AdministratorEntity entity = AdministratorMapper.toEntity(administrator);
        if (administrator.getId() == null) {
            entityManager.persist(entity);
            entityManager.flush();
            return AdministratorMapper.toDomain(entity);
        }

        AdministratorEntity merged = entityManager.merge(entity);
        return AdministratorMapper.toDomain(merged);
    }

    @Override
    public Optional<Administrator> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(AdministratorEntity.class, id))
            .map(AdministratorMapper::toDomain);
    }

    @Override
    public Optional<Administrator> findByEmail(String email) {
        // Actualmente la entidad Administrator no define un campo email.
        // Si agregas email al modelo de dominio y persistencia, aquí puede implementarse la consulta.
        return Optional.empty();
    }

    @Override
    public List<Administrator> findAll() {
        List<AdministratorEntity> entities = entityManager
            .createQuery("SELECT a FROM AdministratorEntity a", AdministratorEntity.class)
            .getResultList();

        return entities.stream()
            .map(AdministratorMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        AdministratorEntity entity = entityManager.find(AdministratorEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}