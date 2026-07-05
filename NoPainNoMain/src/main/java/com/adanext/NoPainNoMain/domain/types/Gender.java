package com.adanext.NoPainNoMain.domain.types;

public class Gender {
  private Integer id;
  private String name;

  public Gender(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public Gender() {}

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
