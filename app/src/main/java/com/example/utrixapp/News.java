package com.example.utrixapp;

public class News {
    String news;
    String Date;
    String imageurl;

    public News(){}
    public News (String news, String date){
        this.news=news;
        this.Date=date;
    }
    public News(String news, String date, String imageurl) {
        this.news = news;
        this.Date=date;
        this.imageurl=imageurl;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
