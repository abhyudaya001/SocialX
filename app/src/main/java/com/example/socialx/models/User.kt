package com.example.socialx.models

class User(
    private var userName:String,
    private var mail:String,
    private var password:String,
    private var phoneNumber:String
) {
    constructor():this("","","","")
    constructor(
        userName:String,
        mail:String,
        password:String
    ):this(userName,mail,password,"")

    fun getUserName():String{
        return userName
    }
    fun setUserName(userName: String){
        this.userName=userName
    }
    fun getMail():String{
        return mail
    }
    fun setMail(mail: String){
        this.mail=mail
    }
    fun getPassword():String{
        return password
    }
    fun setPassword(password: String){
        this.password=password
    }
    fun getUserPhoneNumber():String{
        return phoneNumber
    }
    fun setPhoneNumber(userId: String){
        this.phoneNumber=userId
    }


}