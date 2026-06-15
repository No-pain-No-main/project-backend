# Reglas — Capa de Persistencia (`persistence/`)

Esta capa tiene tres subcapas con reglas propias:
- `persistence/entities/` — Entidades JPA
- `persistence/repositories/` — Interfaces Spring Data
- `persistence/impl/` — Adaptadores (implementan interfaces del dominio)

---

## Entidades JPA (`persistence/entities/`)

### P-M1 — Anotaciones JPA obligatorias y correctas
**Regla:** Toda clase en `entities/` debe tener `@Entity` y `@Table`. Debe tener exactamente un campo anotado con `@Id` y `@GeneratedValue`.

### P-M2 — Separación de modelo JPA y modelo de dominio
**Regla:** Las entidades JPA (`*JpaEntity` o `*Entity`) no deben implementar interfaces del dominio ni extender clases de dominio. Son modelos de persistencia aislados.

### P-SHOULD-1 — Convención de nombres
Nombrar con sufijo `Entity` o `JpaEntity` para distinguir del modelo de dominio (ej. `AdministratorEntity` vs `Administrator`).

---

## Repositorios Spring Data (`persistence/repositories/`)

### PR-M1 — Solo extienden JpaRepository
**Regla:** Las interfaces aquí deben extender `JpaRepository<EntityType, IdType>`. No deben implementar las interfaces del dominio directamente.

### PR-SHOULD-1 — Métodos de consulta expresivos
Preferir métodos derivados de Spring Data (`findByEmailAndActive`) sobre `@Query` con JPQL cuando la consulta es simple. Documentar con `@Query` solo si la consulta es compleja.

---

## Adaptadores (`persistence/impl/`)

### PA-M1 — Implementan la interfaz del dominio
**Regla:** Cada clase en `impl/` debe implementar exactamente una interfaz de `domain/*Repository`. Si no implementa ninguna, no pertenece a esta subcapa.

```java
// ✅ Correcto
@Component
public class AdministratorRepositoryImpl implements AdministratorRepository {
    private final AdministratorJpaRepository jpaRepository;
    // ...
}
```

### PA-M2 — Mapeo explícito entre entidad JPA y entidad de dominio
**Regla:** El adaptador es el único lugar donde ocurre el mapeo. No debe retornar entidades JPA hacia arriba (dominio/servicio). Debe convertir antes de retornar.

```java
// ✅ Correcto
@Override
public Administrator save(Administrator administrator) {
    AdministratorEntity entity = toEntity(administrator);
    AdministratorEntity saved = jpaRepository.save(entity);
    return toDomain(saved);
}

// ❌ Incorrecto — retorna entidad JPA al dominio
@Override
public AdministratorEntity save(Administrator administrator) { ... }
```

### PA-M3 — No contiene lógica de negocio
**Regla:** Los adaptadores solo mapean y delegan. Si hay un `if` de negocio (ej. "si el email ya existe, lanzar excepción de negocio"), esa lógica pertenece al servicio.

### PA-SHOULD-1 — Métodos de mapeo privados y nombrados
Los métodos `toEntity()` y `toDomain()` deben ser `private`, estáticos si no usan estado de instancia, y claramente nombrados.