package com.zsl.custombox.authentication.model.pojo.login;


public class UserDetailsInfo {

  private java.lang.Long id;
  private java.lang.Long userId;
  private java.lang.Long gender;
  private String country;
  private String province;
  private String city;
  private String addrDetails;
  private java.time.LocalDateTime birthday;
  private String realname;
  private java.time.LocalDateTime insertTime;
  private java.time.LocalDateTime updateTime;


  public java.lang.Long getId() {
    return id;
  }

  public void setId(java.lang.Long id) {
    this.id = id;
  }


  public java.lang.Long getUserId() {
    return userId;
  }

  public void setUserId(java.lang.Long userId) {
    this.userId = userId;
  }


  public java.lang.Long getGender() {
    return gender;
  }

  public void setGender(java.lang.Long gender) {
    this.gender = gender;
  }


  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }


  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }


  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }


  public String getAddrDetails() {
    return addrDetails;
  }

  public void setAddrDetails(String addrDetails) {
    this.addrDetails = addrDetails;
  }


  public java.time.LocalDateTime getBirthday() {
    return birthday;
  }

  public void setBirthday(java.time.LocalDateTime birthday) {
    this.birthday = birthday;
  }


  public String getRealname() {
    return realname;
  }

  public void setRealname(String realname) {
    this.realname = realname;
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
