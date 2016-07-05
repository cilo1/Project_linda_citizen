package com.example.cmigayi.projectlindacitizen;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cmigayi on 24-Mar-16.
 */
public class Citizen {
    int citizen_id;
    String fname, lname,surname,email,phone,profession,nationality,home,work,nationalID,password,dob,
    dateTime;
    ArrayList<HashMap<String,String>> citizenList;

    public Citizen(int citizen_id, String fname, String lname, String surname, String email,
                   String phone, String profession, String nationality, String home, String work,
                   String nationalID, String password, String dob) {
        this.citizen_id = citizen_id;
        this.fname = fname;
        this.lname = lname;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.profession = profession;
        this.nationality = nationality;
        this.home = home;
        this.work = work;
        this.nationalID = nationalID;
        this.password = password;
        this.dob = dob;
    }

    public Citizen(ArrayList<HashMap<String,String>> citizenList){
        this.citizenList = citizenList;
    }

    public int getCitizen_id() {
        return citizen_id;
    }

    public void setCitizen_id(int citizen_id) {
        this.citizen_id = citizen_id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
