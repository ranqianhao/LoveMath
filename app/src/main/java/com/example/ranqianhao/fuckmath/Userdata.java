package com.example.ranqianhao.fuckmath;

public class Userdata {
    private String userName;//用户名
    private String userPsw;//密码
    private int userId; //用户Id
    public int pswReset = 0;
    public int pwdresetFlag=0;

    public Userdata(String userName, String userPsw) {
        this.userName = userName;
        this.userPsw = userPsw;
    }


    //获取userID 由于userid是int类型，所以这里也是int
    public int getUserId(){
        return userId;
    }
    //获取密码
    public String getUserPsw() {
        return userPsw;
    }
    //获取username
    public String getUserName() {
        return userName;
    }
    //获取pswreset
    public int getPswReset() {
        return pswReset;
    }


    //输入用户名
    public void setUserName(String userName) {
        this.userName = userName;
    }
    //输入用户id
    public void setUserId(int userId) {
        this.userId = userId;
    }
   //输入重置密码
    public void setPswReset(int pswReset) {
        this.pswReset = pswReset;
    }
    //输入用户密码
    public void setUserPsw(String userPsw) {
        this.userPsw = userPsw;
    }

    public void UserData(String userName, String userPsw) {  //这里只采用用户名和密码
      //  super();
        this.userName = userName;
        this.userPsw = userPsw;
    }

}
