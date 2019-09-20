package com.example.counselapp.Model

object UserList {

    fun getUserList() : List<User> {
        return listOf(
            User(1,"aaa","aaa"),
            User(2,"bbb","bbb"),
            User(3,"ccc","ccc")
        )
    }
}