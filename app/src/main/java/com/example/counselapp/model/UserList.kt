package com.example.counselapp.model

object UserList {
    var USER_NORMAL :Int = 0
    var USER_EXPERT :Int = 1
    var description :String = "세상엔 당신이 어떻게 할 수 없는 일도 많습니다. 지금 당장 할 수 있는 것부터 차근차근 같이 해 볼까요?"

    val userData : List<User> = listOf<User>(
        User("aaa","aaa", USER_NORMAL,"솔이","",""),
        User("bbb","bbb", USER_EXPERT,"이예솔", description,"동작정신건강센터"),
        User("ccc","ccc", USER_EXPERT,"김라트", description,"강남정신건강센터"),
        User( "ddd","ddd", USER_EXPERT,"이망고", description,"구로정신건강센터"),
        User( "ddd","ddd", USER_EXPERT,"호머 심슨", description,"서초정신건강센터"),
        User( "ddd","ddd", USER_EXPERT,"마지 심슨", description,"역삼정신건강센터")
    )

    fun getUserList() : ArrayList<User> {
        val list = ArrayList<User>()
        for(i in userData.indices){
            list.add(userData[i])
        }
        return list
    }

}