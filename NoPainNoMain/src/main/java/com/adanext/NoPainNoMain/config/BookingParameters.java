package com.adanext.NoPainNoMain.config;

/**
 * Parámetros configurables del módulo de reservas.
 * 
 * Estos valores pueden ser modificados en el futuro por un rol
 * administrador, ya sea desde BD o desde una interfaz de gestión.
 * Por ahora se definen como constantes locales.
 */
public final class BookingParameters {

    private BookingParameters() {
        // Clase de constantes, no instanciable
    }

    /** Máximo de reservas activas simultáneas permitidas por estudiante */
    public static final int MAX_ACTIVE_BOOKINGS_PER_STUDENT = 3;
}
