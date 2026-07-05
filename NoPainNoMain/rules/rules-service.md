# Reglas — Capa de Servicios / Casos de Uso (`service/`)

## MUST (bloqueantes)

### S-M1 — Solo depende del dominio
**Regla:** Los servicios solo pueden importar de `com.adanext.NoPainNoMain.domain.*`. Está prohibido importar:
- `…persistence.*` directamente (clases JPA, Spring Data repos)
- `…controller.*`
- `org.springframework.web.*` o `org.springframework.http.*`

**Fix:** Si necesitas datos de la BD, declara un método en la interfaz del dominio (`domain/*Repository`) e inyéctala.

### S-M2 — Inyección por interfaz de dominio
**Regla:** Los servicios deben inyectar `AdministratorRepository` (del dominio), nunca `AdministratorJpaRepository` (de Spring Data).
```java
// ✅ Correcto
private final AdministratorRepository administratorRepository; // domain interface

// ❌ Incorrecto
private final AdministratorJpaRepository administratorJpaRepository; // Spring Data JPA
```

### S-M3 — Fail-Fast en validaciones
**Regla:** Las validaciones deben lanzar excepción lo antes posible. No acumular errores para validar al final del método.
```java
// ✅ Correcto
public void register(String email) {
    if (email == null || email.isBlank()) {
        throw new IllegalArgumentException("Email cannot be blank");
    }
    // ... resto de la lógica
}
```

### S-M4 — Sin lógica HTTP
**Regla:** Los servicios no deben construir ni retornar `ResponseEntity`, manejar códigos HTTP, ni referenciar `HttpServletRequest`. Eso es responsabilidad del controlador.

## SHOULD (recomendaciones)

### S-S1 — Complejidad controlada (Single Responsibility)
Si un método de servicio supera ~4 validaciones de negocio independientes, considera extraer esa lógica a una clase `*Helper` o `*Validator` en un subpaquete `service/helpers/`. El servicio orquesta; el helper valida.

**Señal de alerta:** más de 3 bloques `if` de validación consecutivos en un mismo método.

### S-S2 — Nombres de clase por caso de uso
Preferir nombres como `RegisterAdministratorService`, `QueryAppointmentService` en lugar de `AdministratorService` genérico. Expresa la intención.

### S-S3 — Manejo de Optional explícito
Al consumir `Optional<T>` de un repositorio, manejar el caso vacío explícitamente con `.orElseThrow()` y un mensaje de negocio claro. Evitar `.get()` sin guard.

```java
// ✅ Correcto
Administrator admin = administratorRepository.findById(id)
    .orElseThrow(() -> new IllegalArgumentException("Administrator not found: " + id));

// ❌ Incorrecto
Administrator admin = administratorRepository.findById(id).get();
```

### S-S4 — Sin anotaciones de persistencia
Los servicios no deben tener `@Transactional` de Spring si la lógica transaccional puede delegarse al adaptador de persistencia. Si es necesario, justificar con comentario.