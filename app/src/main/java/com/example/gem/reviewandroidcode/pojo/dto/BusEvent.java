package com.example.gem.reviewandroidcode.pojo.dto;

/**
 * Created by Giga on 11/2/2017.
 */

public class BusEvent {
  private int type;
  private Object content;

  public BusEvent(int type) {
    this.type = type;
  }

  public BusEvent(int type, Object content) {
    this.type = type;
    this.content = content;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public Object getContent() {
    return content;
  }

  public void setContent(Object content) {
    this.content = content;
  }

  public static class TYPE {
    public static final int KEY_NUMBER_PRESSED = 1;
    public static final int CONNECTION_STATUS = 8;
    public static final int CONNECTION_ONLINE = 9;
    public static final int CONNECTION_OFFLINE = 10;
    public static final int LOCATION = 2;
    public static final int SPOT_MODEL = 3;
    public static final int DOWNLOAD_AR = 5;
  }
}
