package com.lifelineblood.lifelineblood.modelclass;

public class UserCredientials {

    String name;
    String address;
    String bloodgroup;
    String email;
    String contactNum;
    String timestamp;
    String lastClickOnDonate;
    String fBaseId;
    String sex;
    String isNeedy;
    String isDoner;
    int age;

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setfBaseId(String fBaseId) {
        this.fBaseId = fBaseId;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setIsNeedy(String isNeedy) {
        this.isNeedy = isNeedy;
    }

    public void setIsDoner(String isDoner) {
        this.isDoner = isDoner;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public UserCredientials(){}

    public UserCredientials(String name, String address, String bloodgroup, String email,
                            String contactNum, String timestamp, String fBaseId, String sex,
                            int age,String isNeedy, String isDoner) {
        this.name = name;
        this.address = address;
        this.bloodgroup = bloodgroup;
        this.email = email;
        this.contactNum = contactNum;
        this.timestamp = timestamp;
        this.fBaseId = fBaseId;
        this.sex = sex;
        this.age = age;
        this.isDoner = isDoner;
        this.isNeedy = isNeedy;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNum() {
        return contactNum;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getfBaseId() {
        return fBaseId;
    }

    public String getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public String getIsNeedy() {
        return isNeedy;
    }

    public String getIsDoner() {
        return isNeedy;
    }
}
