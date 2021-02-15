package com.example.action.item;

public class OneScriptItem {
    private String name;
    private String sid; // script id

    // Firebase 데이터 에서 Script id 가져와야함
    public OneScriptItem(String name, String sid) {
        this.name = name;
        this.sid = sid;
    }
}
