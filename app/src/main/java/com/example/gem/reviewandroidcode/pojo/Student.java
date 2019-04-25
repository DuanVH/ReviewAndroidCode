package com.example.gem.reviewandroidcode.pojo;

public class Student {

  int id;
  String name;
  String address;
  String numPhone;

  public Student(int id, String name, String address, String numPhone) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.numPhone = numPhone;
  }

  public Student(String name, String address, String numPhone) {
    this.name = name;
    this.address = address;
    this.numPhone = numPhone;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getNumPhone() {
    return numPhone;
  }

  public void setNumPhone(String numPhone) {
    this.numPhone = numPhone;
  }
}
