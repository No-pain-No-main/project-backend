package com.adanext.NoPainNoMain.controller;

import com.adanext.NoPainNoMain.domain.Administrator;
import com.adanext.NoPainNoMain.domain.repository.AdministratorRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/administrators")
public class AdministratorController {

  private final AdministratorRepository administratorRepository;

  // Spring inyecta automáticamente tu AdministratorRepositoryImpl aquí
  public AdministratorController(AdministratorRepository administratorRepository) {
    this.administratorRepository = administratorRepository;
  }

  // Endpoint para listar todos los administradores
  @GetMapping
  public ResponseEntity<List<Administrator>> getAll() {
    return ResponseEntity.ok(administratorRepository.findAll());
  }

  // Endpoint para buscar uno por ID
  @GetMapping("/{id}")
  public ResponseEntity<Administrator> getById(@PathVariable String id) {
    return administratorRepository
        .findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
