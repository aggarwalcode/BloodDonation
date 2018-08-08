package com.lifelineblood.lifelineblood.modelclass;

import java.io.Serializable;

public class BloodRequesteeDetails implements Serializable {

    String name;
    String address;
    String bloodgroup;
    String email;
    String contactNum;
    String timestamp;
    String fBaseId;
    String requestedBy;
    String sex;
    int age;
    String hospital;
    String bloodType;

    public BloodRequesteeDetails(){};

    public BloodRequesteeDetails(String name, String address, String bloodgroup,
                                 String email, String contactNum, String timestamp,
                                 String fBaseId, String requestedBy, String sex,
                                 int age, String hospital, String bloodType) {
        this.name = name;
        this.address = address;
        this.bloodgroup = bloodgroup;
        this.email = email;
        this.contactNum = contactNum;
        this.timestamp = timestamp;
        this.fBaseId = fBaseId;
        this.requestedBy = requestedBy;
        this.sex = sex;
        this.age = age;
        this.hospital = hospital;
        this.bloodType = bloodType;
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

    public String getRequestedBy() {
        return requestedBy;
    }

    public String getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public String getHospital() {
        return hospital;
    }

    public String getBloodType() {
        return bloodType;
    }

}
