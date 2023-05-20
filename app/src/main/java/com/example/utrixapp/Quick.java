package com.example.utrixapp;

public class Quick {
    public String announcement,announce_date;
    public Quick(){}
    public Quick(String announcement, String announce_date) {
        this.announcement = announcement;
        this.announce_date = announce_date;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public String getAnnounce_date() {
        return announce_date;
    }

    public void setAnnounce_date(String announce_date) {
        this.announce_date = announce_date;
    }
}
