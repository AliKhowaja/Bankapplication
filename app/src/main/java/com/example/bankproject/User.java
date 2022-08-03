package com.example.bankproject;

public class User {
    public String Email, Password, Fullname, Age, Mobilephone, Address, Country, Balance, AccountNO;

    public User(){

    }
    public User(String Email,String Password,String Fullname,String Age,String Mobilephone,String Address, String Country, String Balance,
                String AccountNO){
        this.Email = Email;
        this.Password = Password;
        this.Fullname = Fullname;
        this.Age = Age;
        this.Mobilephone = Mobilephone;
        this.Address = Address;
        this.Country = Country;
        this.Balance = Balance;
        this.AccountNO = AccountNO;
    }

}

