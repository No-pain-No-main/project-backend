package com.adanext.NoPainNoMain.mapper.types;

import com.adanext.NoPainNoMain.domain.types.BookingStatus;
import com.adanext.NoPainNoMain.persistence.types.BookingStatusEntity;

public class BookingStatusMapper {

  // Convierte de Entidad de Base de Datos a Objeto de Dominio
  public static BookingStatus toDomain(BookingStatusEntity entity) {
    if (entity == null) {
      return null;
    }

    return new BookingStatus(entity.getId(), entity.getName());
  }

  // Convierte de Objeto de Dominio a Entidad de Base de Datos
  public static BookingStatusEntity toEntity(BookingStatus domain) {
    if (domain == null) {
      return null;
    }

    return new BookingStatusEntity(domain.getId(), domain.getName());
  }
}
