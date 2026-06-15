package com.adanext.NoPainNoMain.domain.types;

public class DocumentType {
  private Integer id;
  private String name;

  public DocumentType(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public DocumentType() {}

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
