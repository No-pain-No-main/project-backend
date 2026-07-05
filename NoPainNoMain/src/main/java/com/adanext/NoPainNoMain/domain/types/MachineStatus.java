package com.adanext.NoPainNoMain.domain.types;

public class MachineStatus {
  private Integer id;
  private String name;

  public MachineStatus(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public MachineStatus() {}

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
