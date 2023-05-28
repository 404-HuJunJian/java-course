package com.parsley.constant;

public class OrdinaryResponseCodeConst extends ResponseCodeConst{

    public static final OrdinaryResponseCodeConst SUCCESS = new OrdinaryResponseCodeConst(100,"操作成功",true);

    public static final OrdinaryResponseCodeConst TIME_OUT = new OrdinaryResponseCodeConst(104,"超时",false);

    public OrdinaryResponseCodeConst(int code,String msg){super(code,msg);}

    public OrdinaryResponseCodeConst(int code,String msg,boolean success){super(code, msg, success);}

}
