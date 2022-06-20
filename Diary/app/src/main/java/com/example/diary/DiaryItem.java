package com.example.diary;

public class DiaryItem {
    private int id;
    private String title;
    private String content;
    private String year;
    private String month;
    private String day;
    private String mood_id;
    private String imageUrl;
    private String weather_id;

    public DiaryItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String  getYear() { return year; }

    public void setYear(String year) {
        this.year = year;
    }

    public String  getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String  getDay() { return day; }

    public void setDay(String day) {
        this.day = day;
    }

    public String  getMood_id() { return mood_id; }

    public void setMood_id(String mood_id) { this.mood_id = mood_id; }

    public String getImageUrl() { return imageUrl; } //////

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getWeather_id() { return weather_id; }

    public void setWeather_id(String weather_id) { this.weather_id = weather_id; }
}
