package com.example.ufood.Model;

public class UserHolder {
    public String user_name;
    public String email;
    public String phone;
    public String password;
    public String profile_pic;
    public String user_type;
    public UserHolder(){

    }

    public UserHolder(String user_name, String email, String phone, String password,String profile_pic, String user_type) {
        this.user_name = user_name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.profile_pic = profile_pic;
        this.user_type = user_type;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
