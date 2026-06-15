# Reglas — Capa de Dominio (`domain/`)

## MUST (bloqueantes)

### D-M1 — Sin dependencias de frameworks externos
**Regla:** Ninguna clase en `domain/` puede importar `org.springframework.*`, `jakarta.persistence.*`, `jakarta.validation.*` ni ninguna librería externa (Jackson, Hibernate, etc.).
**Solo permitido:** paquetes `java.*` y `com.adanext.NoPainNoMain.domain.*`.
**Fix:** Mover la lógica que requiere framework a la capa de persistencia o servicio. Usar interfaces del dominio como contrato.

### D-M2 — Las interfaces de repositorio son contratos puros
**Regla:** Las interfaces `*Repository` en `domain/` deben declarar solo métodos de negocio (`save`, `findById`, `findByEmail`, etc.). No deben extender `JpaRepository`, `CrudRepository` ni ninguna interfaz de Spring Data.
**Fix:**
```java
// ✅ Correcto en domain/
public interface AdministratorRepository {
    Administrator save(Administrator administrator);
    Optional<Administrator> findById(Long id);
}

// ❌ Incorrecto en domain/
public interface AdministratorRepository extends JpaRepository<AdministratorEntity, Long> { }
```

### D-M3 — Entidades de dominio son POJOs puros
**Regla:** Las clases de entidad en `domain/` (ej. `Administrator.java`) no pueden tener anotaciones JPA (`@Entity`, `@Table`, `@Column`, `@Id`, `@GeneratedValue`). Son POJOs con getters/setters o records.
**Fix:** Si necesitas persistir, crea una clase separada `AdministratorJpaEntity` en `persistence/entities/`.

### D-M4 — Excepciones de negocio son nativas
**Regla:** Las excepciones definidas en `domain/` deben extender `RuntimeException`, `IllegalArgumentException` o `IllegalStateException`. No deben usar `ResponseStatusException` ni nada de Spring.

## SHOULD (recomendaciones)

### D-S1 — Nombres expresivos de dominio
Los métodos de las interfaces de repositorio deben usar lenguaje de negocio, no técnico. Preferir `findByEmail` sobre `selectByEmailColumn`.

### D-S2 — Validaciones en el constructor
Las reglas de negocio invariantes (ej. "el email no puede ser nulo") deben validarse en el constructor de la entidad de dominio con `IllegalArgumentException`. No delegar esto a Bean Validation de Jakarta.