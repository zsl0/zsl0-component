package com.zsl.custombox.authentication.model.pojo.login;


public class Menu {

  private java.lang.Long id;
  private String menuName;
  private java.lang.Long parentId;
  private java.time.LocalDateTime insertTime;
  private java.time.LocalDateTime updateTime;


  public java.lang.Long getId() {
    return id;
  }

  public void setId(java.lang.Long id) {
    this.id = id;
  }


  public String getMenuName() {
    return menuName;
  }

  public void setMenuName(String menuName) {
    this.menuName = menuName;
  }


  public java.lang.Long getParentId() {
    return parentId;
  }

  public void setParentId(java.lang.Long parentId) {
    this.parentId = parentId;
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
