package com.adanext.NoPainNoMain.service.register.helpers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adanext.NoPainNoMain.domain.Administrator;
import com.adanext.NoPainNoMain.persistence.impl.AdministratorRepositoryImpl;

@ExtendWith(MockitoExtension.class)
class AdministratorRegisterHelperTest {

    @Mock
    private AdministratorRepositoryImpl repository;

    @InjectMocks
    private AdminRegisterHelper helper;

    private Administrator admin;

    @BeforeEach
    void setUp() {
        admin = new Administrator();
    }

 // ─── isDuplicateDocument ────────────────────────────────────────────────

    @Test
    void isDuplicateDocument_whenDocumentExists_returnsTrue() {
        admin.setDocumentNumber("12345678");
        when(repository.findById("12345678")).thenReturn(Optional.of(admin));

        boolean result = helper.isDuplicateDocument(admin);

        assertTrue(result);
        verify(repository).findById("12345678");
    }

    @Test
    void isDuplicateDocument_whenDocumentDoesNotExist_returnsFalse() {
        admin.setDocumentNumber("87654321");
        when(repository.findById("87654321")).thenReturn(Optional.empty());

        boolean result = helper.isDuplicateDocument(admin);

        assertFalse(result);
        verify(repository).findById("87654321");
    }

    @Test
    void isDuplicateDocument_whenDocumentIsNull_returnsFalseWithoutQueryingDB() {
        admin.setDocumentNumber(null);

        boolean result = helper.isDuplicateDocument(admin);

        assertFalse(result);
        verify(repository, never()).findById(any());
    }

 // ─── isDuplicateEmail ────────────────────────────────────────────────


    @Test
    void isDuplicateEmail_whenEmailExists_returnsTrue() {
        admin.setEmail("admin@nopainnomain.com");
        when(repository.findByEmail("admin@nopainnomain.com")).thenReturn(Optional.of(admin));

        boolean result = helper.isDuplicateEmail(admin);

        assertTrue(result);
        verify(repository).findByEmail("admin@nopainnomain.com");
    }

    @Test
    void isDuplicateEmail_whenEmailDoesNotExist_returnsFalse() {
        admin.setEmail("nuevo@nopainnomain.com");
        when(repository.findByEmail("nuevo@nopainnomain.com")).thenReturn(Optional.empty());

        boolean result = helper.isDuplicateEmail(admin);

        assertFalse(result);
        verify(repository).findByEmail("nuevo@nopainnomain.com");
    }

    @Test
    void isDuplicateEmail_whenEmailIsNull_returnsFalseWithoutQueryingDB() {
        admin.setEmail(null);

        boolean result = helper.isDuplicateEmail(admin);

        assertFalse(result);
        verify(repository, never()).findByEmail(any());
    }
}