package com.zsl.custombox.authentication.model.pojo.login;


public class Role {

  private java.lang.Long id;
  private String roleName;
  private java.time.LocalDateTime insertTime;
  private java.time.LocalDateTime updateTime;


  public java.lang.Long getId() {
    return id;
  }

  public void setId(java.lang.Long id) {
    this.id = id;
  }


  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }


  public java.time.LocalDateTime getInsertTime() {
    return insertTime;
  }

  public void setInsertTime(java.time.LocalDateTime insertTime) {
    this.insertTime = insertTime;
  }


  public java.time.LocalDateTime getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(java.time.LocalDateTime updateTime) {
    this.updateTime = updateTime;
  }

}
