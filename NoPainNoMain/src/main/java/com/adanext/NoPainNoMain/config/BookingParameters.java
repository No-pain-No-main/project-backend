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

    // ─── Estados de máquina ───────────────────────────────────────
    /** ID del estado "Disponible" en la tabla machine_status */
    public static final int MACHINE_STATUS_AVAILABLE = 1;
    /** ID del estado "Inactiva" en la tabla machine_status */
    public static final int MACHINE_STATUS_INACTIVE = 2;
    /** ID del estado "Reservada" en la tabla machine_status */
    public static final int MACHINE_STATUS_RESERVED = 3;

    // ─── Estados de reserva ───────────────────────────────────────
    /** ID del estado "Activa" en la tabla booking_status */
    public static final int BOOKING_STATUS_ACTIVE = 1;
    /** ID del estado "Cancelada" en la tabla booking_status */
    public static final int BOOKING_STATUS_CANCELLED = 2;
    /** ID del estado "Finalizada" en la tabla booking_status */
    public static final int BOOKING_STATUS_FINISHED = 3;

    // ─── Cancelación ──────────────────────────────────────────────
    /** Minutos mínimos antes de la franja para permitir cancelación */
    public static final int CANCELLATION_MINUTES_BEFORE = 30;
}
