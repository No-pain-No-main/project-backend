package com.adanext.NoPainNoMain.domain.types;

public class MachineType {
  private Integer id;
  private String name;

  public MachineType(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public MachineType() {}

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
