package com.example.project2_test1;

public class GridItem {
    private String mday;
    private String mtitle;

    public GridItem(String day, String title){
        mday=day;
        mtitle=title;
    }

    public String getDay(){
        return mday;
    }

    public  void setDay(String mday){
        this.mday=mday;
    }

    public  void setTitle(String mtitle){
        this.mtitle=mtitle;
    }

    public String getTitle(){
        return mtitle;
    }
}

