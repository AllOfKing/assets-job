package com.rookie.opcua.utils;


import static java.util.UUID.*;

public class UUIDUtils {


    public static String getUUID()
    {
        String uuid= randomUUID().toString();
        char[] cs=new char[32];
        char c=0;
        for(int i=uuid.length()/2,j=1;i-->0;){
            if((c=uuid.charAt(i))!='-'){
                cs[j++]=c;
            }
        }
        String uid=String.valueOf(cs).trim();
        return uid;
    }
}
