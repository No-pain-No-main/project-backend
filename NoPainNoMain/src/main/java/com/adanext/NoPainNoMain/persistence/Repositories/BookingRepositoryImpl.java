package com.adanext.NoPainNoMain.persistence.Repositories;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.repository.BookingRepository;
import com.adanext.NoPainNoMain.persistence.BookingEntity;
import com.adanext.NoPainNoMain.persistence.mappers.BookingMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class BookingRepositoryImpl implements BookingRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Booking save(Booking booking) {
        if (booking == null) return null;

        BookingEntity entity = BookingMapper.toEntity(booking);
        if (booking.getId() == null) {
            entityManager.persist(entity);
            entityManager.flush();
            return BookingMapper.toDomain(entity);
        }

        BookingEntity merged = entityManager.merge(entity);
        return BookingMapper.toDomain(merged);
    }

    @Override
    public Optional<Booking> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(BookingEntity.class, id))
            .map(BookingMapper::toDomain);
    }

    @Override
    public List<Booking> findAll() {
        List<BookingEntity> entities = entityManager
            .createQuery("SELECT b FROM BookingEntity b", BookingEntity.class)
            .getResultList();

        return entities.stream()
            .map(BookingMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Booking> findByMachineId(Integer machineId) {
        TypedQuery<BookingEntity> query = entityManager.createQuery(
            "SELECT b FROM BookingEntity b WHERE b.machine.id = :machineId", BookingEntity.class);
        query.setParameter("machineId", machineId);

        return query.getResultList().stream()
            .map(BookingMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        BookingEntity entity = entityManager.find(BookingEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}