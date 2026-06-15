package com.adanext.NoPainNoMain.service.register;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.adanext.NoPainNoMain.domain.Administrator;
import com.adanext.NoPainNoMain.persistence.impl.AdministratorRepositoryImpl;
import com.adanext.NoPainNoMain.service.jsonconverter.JsonToClass;
import com.adanext.NoPainNoMain.service.register.helpers.AdminRegisterHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AdministratorRegisterTest {

  @Mock private JsonToClass<Administrator> jsonToClass;

  @Mock private AdministratorRepositoryImpl administratorRepository;

  @Mock private AdminRegisterHelper helper;

  @InjectMocks private AdministratorRegister administratorRegister;

  private Administrator validAdmin;
  private final String validJson =
      "{\"documentNumber\":\"12345678\",\"email\":\"admin@nopainnomain.com\"}";

  @BeforeEach
  void setUp() {
    validAdmin = new Administrator();
    validAdmin.setDocumentNumber("12345678");
    validAdmin.setEmail("admin@nopainnomain.com");
  }

  @Test
  void save_validJsonAndNoDuplicates_savesAndReturnsAdmin() {
    when(jsonToClass.convert(validJson, Administrator.class)).thenReturn(validAdmin);
    when(helper.isDuplicateDocument(validAdmin)).thenReturn(false);
    when(helper.isDuplicateEmail(validAdmin)).thenReturn(false);
    when(administratorRepository.save(validAdmin)).thenReturn(validAdmin);

    Administrator result = administratorRegister.save(validJson);

    assertNotNull(result);
    assertEquals("12345678", result.getDocumentNumber());
    assertEquals("admin@nopainnomain.com", result.getEmail());

    verify(administratorRepository).save(validAdmin);
  }

  @Test
  void save_duplicateDocument_throwsIllegalStateException() {
    when(jsonToClass.convert(validJson, Administrator.class)).thenReturn(validAdmin);
    when(helper.isDuplicateDocument(validAdmin)).thenReturn(true);

    IllegalStateException ex =
        assertThrows(IllegalStateException.class, () -> administratorRegister.save(validJson));

    assertTrue(ex.getMessage().contains("ya existe en el sistema"));

    verify(helper, never()).isDuplicateEmail(any());
    verify(administratorRepository, never()).save(any());
  }

  @Test
  void save_duplicateEmail_throwsIllegalStateException() {
    when(jsonToClass.convert(validJson, Administrator.class)).thenReturn(validAdmin);
    when(helper.isDuplicateDocument(validAdmin)).thenReturn(false);
    when(helper.isDuplicateEmail(validAdmin)).thenReturn(true);

    IllegalStateException ex =
        assertThrows(IllegalStateException.class, () -> administratorRegister.save(validJson));

    assertTrue(ex.getMessage().contains("ya está registrado por otro administrador"));
    verify(administratorRepository, never()).save(any());
  }

  @Test
  void save_invalidJson_throwsExceptionWithoutCheckingBusinessRules() {
    String invalidJson = "{ bad_json }";
    when(jsonToClass.convert(invalidJson, Administrator.class))
        .thenThrow(new IllegalArgumentException("Error parseando JSON"));

    assertThrows(IllegalArgumentException.class, () -> administratorRegister.save(invalidJson));

    verify(helper, never()).isDuplicateDocument(any());
    verify(helper, never()).isDuplicateEmail(any());
    verify(administratorRepository, never()).save(any());
  }
}
