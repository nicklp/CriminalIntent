package com.nick.criminalintent.tools;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nick on 2016/9/2.
 */
public class DebugTags {
    private Map<String,String> map = new HashMap<String,String>(){
        {
            put("criminalintent","criminalintent");
        }
    };
    private DebugTags(){}

    public void addTag(String key,String value){
        map.put(key,value);
    }

    private static  DebugTags debugTags = null;
    public static DebugTags getInstance(){
        if (debugTags == null){
            debugTags = new DebugTags();
        }
        return debugTags;
    }

    public String getValueByKey(String key){
        if (key == null){
            return map.get("criminalintent");
        }else{
            return map.get(key);
        }
    }
}
