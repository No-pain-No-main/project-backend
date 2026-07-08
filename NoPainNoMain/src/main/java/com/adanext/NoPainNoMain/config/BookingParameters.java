package com.adanext.NoPainNoMain.config;

public final class BookingParameters {

  private BookingParameters() {
  }

  public static final int MAX_ACTIVE_BOOKINGS_PER_STUDENT = 3;

  public static final int MACHINE_STATUS_AVAILABLE = 1;

  public static final int MACHINE_STATUS_INACTIVE = 2;

  public static final int MACHINE_STATUS_RESERVED = 3;

  public static final int BOOKING_STATUS_ACTIVE = 1;
  
  public static final int BOOKING_STATUS_CANCELLED = 2;

  public static final int BOOKING_STATUS_CONFIRMED = 3;

  public static final int REGISTRATION_MINUTES_BEFORE = 20;

  public static final int CANCELLATION_MINUTES_BEFORE = 30;

  public static final int CONFIRMATION_WINDOW_MINUTES = 10;

  // ─── Tarea programada ─────────────────────────────────────────
  /** Cron: se ejecuta al segundo 1 de cada hora entre las 8am y 5pm */
  public static final String RELEASE_CRON = "1 0 8-17 * * *";
}
