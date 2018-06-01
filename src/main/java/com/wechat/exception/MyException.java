package com.wechat.exception;

public class MyException extends Exception //getMessage();
{

    private String msg;
    public MyException(String msg)
    {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

}