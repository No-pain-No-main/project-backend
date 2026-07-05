package com.adanext.NoPainNoMain.domain.types;

public class UserStatus {
  private Integer id;
  private String name;

  public UserStatus(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public UserStatus() {}

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
