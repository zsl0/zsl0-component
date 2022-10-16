package com.zsl.custombox.authentication.model.pojo.login;


public class UserInfo {

  private java.lang.Long id;
  private String username;
  private String accountName;
  private String password;
  private String email;
  private String phone;
  private java.lang.Long loginType;
  private java.time.LocalDateTime loginTime;
  private String accessToken;
  private String refreshToken;
  private java.lang.Long status;
  private java.lang.Long admin;
  private java.lang.Long deleted;
  private java.time.LocalDateTime insertTime;
  private java.time.LocalDateTime updateTime;


  public java.lang.Long getId() {
    return id;
  }

  public void setId(java.lang.Long id) {
    this.id = id;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public java.lang.Long getLoginType() {
    return loginType;
  }

  public void setLoginType(java.lang.Long loginType) {
    this.loginType = loginType;
  }


  public java.time.LocalDateTime getLoginTime() {
    return loginTime;
  }

  public void setLoginTime(java.time.LocalDateTime loginTime) {
    this.loginTime = loginTime;
  }


  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }


  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }


  public java.lang.Long getStatus() {
    return status;
  }

  public void setStatus(java.lang.Long status) {
    this.status = status;
  }


  public java.lang.Long getAdmin() {
    return admin;
  }

  public void setAdmin(java.lang.Long admin) {
    this.admin = admin;
  }


  public java.lang.Long getDeleted() {
    return deleted;
  }

  public void setDeleted(java.lang.Long deleted) {
    this.deleted = deleted;
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
