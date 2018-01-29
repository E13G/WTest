package com.example.jcmor.wtest.exercise1;

/*
 * Created by jcmor on 27/01/2018.
 *
 * A simple Data class to insert data in the database.
 */

public class PostalCode {

    private int id;
    private String postalCode;

    PostalCode() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String getPostalCode() {
        return postalCode;
    }

    void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
