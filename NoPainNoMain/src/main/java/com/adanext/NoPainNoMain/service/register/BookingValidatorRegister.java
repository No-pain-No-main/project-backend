package com.adanext.NoPainNoMain.service.register;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.domain.BookingValidator;
import com.adanext.NoPainNoMain.domain.repository.BookingValidatorRepository;
import com.adanext.NoPainNoMain.service.jsonConverter.JsonToClass;
import com.adanext.NoPainNoMain.service.register.helpers.PasswordHashHelper;

@Service
public class BookingValidatorRegister {

    private final JsonToClass<BookingValidator> jsonToClass;
    private final BookingValidatorRepository bookingValidatorRepository;
    private final PasswordHashHelper passwordHashHelper;

    public BookingValidatorRegister(JsonToClass<BookingValidator> jsonToClass,
                                     BookingValidatorRepository bookingValidatorRepository,
                                     PasswordHashHelper passwordHashHelper) {
        this.jsonToClass = jsonToClass;
        this.bookingValidatorRepository = bookingValidatorRepository;
        this.passwordHashHelper = passwordHashHelper;
    }

    public BookingValidator save(String jsonRegister) {
        BookingValidator validator = jsonToClass.convert(jsonRegister, BookingValidator.class);

        if (validator.getDocumentNumber() == null || validator.getDocumentNumber().isBlank()) {
            throw new IllegalArgumentException("El número de documento del validador es requerido");
        }

        String hashed = passwordHashHelper.hashPassword(validator.getPasswordHash());
        validator.registerPassword(hashed);

        return bookingValidatorRepository.save(validator);
    }
}