/*nauveau classe java pour ajouter les inscription*/
package com.example.saveanimals;

public class UserHelperClass {

String name,username,email,phoneNo,password;
//droit sourie => generate => constructor => selection de tous le attribus<=>consturcteur m3abi

//ona besoin d'un constructeur vide aussi pour le 'Firebase'


    public UserHelperClass() {
    }

    public UserHelperClass(String name, String username, String email, String phoneNo, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
    }

    //nous besoins de tous les getters and setters aussi :
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
} //fin de la classe
