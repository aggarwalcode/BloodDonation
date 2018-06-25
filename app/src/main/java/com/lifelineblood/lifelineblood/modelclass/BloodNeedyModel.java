package com.lifelineblood.lifelineblood.modelclass;

import java.io.Serializable;

public class BloodNeedyModel implements Serializable {

    String name;
    String address;
    String bloodgroup;
    String email;
    String contactNum;
    String timestamp;
    String fBaseId;

    String taskStatus;
    int amount,wordCount,invoiceAmt;

    public BloodNeedyModel(String name, String address, String bloodgroup,
                           String email, String contactNum, String timestamp, String fBaseId,
                           String taskStatus, int amount, int wordCount, int invoiceAmt) {
        this.name = name;
        this.address = address;
        this.bloodgroup = bloodgroup;
        this.email = email;
        this.contactNum = contactNum;
        this.timestamp = timestamp;
        this.fBaseId = fBaseId;
        this.taskStatus = taskStatus;
        this.amount = amount;
        this.wordCount = wordCount;
        this.invoiceAmt = invoiceAmt;
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

    public String getTaskStatus() {
        return taskStatus;
    }

    public int getAmount() {
        return amount;
    }

    public int getWordCount() {
        return wordCount;
    }

    public int getInvoiceAmt() {
        return invoiceAmt;
    }


    public BloodNeedyModel(){};


}
