# Reglas — Capa de Controladores (`controller/`)

## MUST (bloqueantes)

### C-M1 — Retornar ResponseEntity
**Regla:** Todos los métodos de endpoint deben retornar `ResponseEntity<T>`. No retornar el objeto directamente sin envolverlo, ya que impide el control explícito de códigos HTTP.

```java
// ✅ Correcto
@GetMapping("/{id}")
public ResponseEntity<Administrator> findById(@PathVariable Long id) {
    return ResponseEntity.ok(administratorRepository.findById(id));
}

// ❌ Incorrecto
@GetMapping("/{id}")
public Administrator findById(@PathVariable Long id) { ... }
```

### C-M2 — No inyectar repositorios de Spring Data
**Regla:** El controlador NO debe inyectar `*JpaRepository` ni ninguna clase de `persistence/`. Solo puede inyectar:
- Interfaces de `domain/*Repository`
- Clases de `service/`

```java
// ✅ Correcto
private final AdministratorRepository administratorRepository; // domain interface

// ❌ Incorrecto — acceso directo a JPA desde el controller
private final AdministratorJpaRepository jpaRepository;
```

### C-M3 — Sin lógica de negocio en el controlador
**Regla:** El controlador no debe tomar decisiones de negocio (ej. validar si un email existe, calcular precios, aplicar reglas de estado). Su único trabajo es: recibir request → llamar servicio/dominio → retornar ResponseEntity con el código correcto.

### C-M4 — Manejo de excepciones de dominio
**Regla:** Las excepciones de negocio (`IllegalArgumentException`, `IllegalStateException` del dominio) deben mapearse a códigos HTTP apropiados. Usar `@ExceptionHandler` o un `@ControllerAdvice` global. No capturar y swallow (ignorar) excepciones silenciosamente.

```java
// ✅ Correcto — manejo explícito
@ExceptionHandler(IllegalArgumentException.class)
public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
    return ResponseEntity.badRequest().body(ex.getMessage());
}

// ❌ Incorrecto — exception swallowed
try {
    service.register(dto);
} catch (Exception e) {
    return ResponseEntity.ok("done"); // oculta el error
}
```

## SHOULD (recomendaciones)

### C-S1 — Anotaciones REST explícitas
Preferir `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping` sobre `@RequestMapping(method = RequestMethod.GET)`. Más legible.

### C-S2 — DTOs para entrada y salida
No exponer directamente las entidades de dominio como cuerpo del request/response si contienen información sensible o campos que no deben ser públicos. Usar DTOs o records de request/response.

### C-S3 — Códigos HTTP semánticamente correctos
- Creación exitosa → `201 Created` (no `200 OK`)
- Recurso no encontrado → `404 Not Found`
- Validación fallida → `400 Bad Request`
- Servidor error → `500` (manejado por `@ControllerAdvice`)

### C-S4 — Inyección por constructor (no @Autowired en campo)
```java
// ✅ Correcto — constructor injection
public AdministratorController(AdministratorRepository administratorRepository) {
    this.administratorRepository = administratorRepository;
}

// ❌ Evitar — field injection dificulta el testing
@Autowired
private AdministratorRepository administratorRepository;
```

## Nota sobre formato

No auditar estilo, naming ni indentación — el linter (Checkstyle/Spotless) ya lo cubre. Enfocarse exclusivamente en estructura: dependencias entre capas, contratos, y responsabilidades.