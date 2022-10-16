package com.zsl.custombox.authentication.model.pojo.log;


public class OperationLog {

  private java.lang.Long id;
  private java.lang.Long userId;
  private java.time.LocalDateTime datetime;
  private String content;
  private String host;
  private String uri;
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


  public java.time.LocalDateTime getDatetime() {
    return datetime;
  }

  public void setDatetime(java.time.LocalDateTime datetime) {
    this.datetime = datetime;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }


  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
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
