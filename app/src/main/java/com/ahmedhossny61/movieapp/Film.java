package com.ahmedhossny61.movieapp;

public class Film implements Comparable<Film>{
    private  String image_Base="https://image.tmdb.org/t/p/w500";
    private String  image_backdrop_path="https://image.tmdb.org/t/p/w500";
    private String image_url;
    private String name;
    private String rate;
    private int vote_count;
    private String url;
    private int id;
    private String overview;
    String release_date;
    String language;

    public Film(String mName,String mRate,String mimage_url,String mimage_backdrop_path,int mvote_count,int mid,String moverview,String mrelease_date,String mlanguage){
        name=mName;
        rate=mRate;
        image_url=image_Base+mimage_url;
        image_backdrop_path+=mimage_backdrop_path;
        vote_count=mvote_count;
        id=mid;
        overview=moverview;
        release_date=mrelease_date;
        language=mlanguage;
    }
    public Film(int mid,String mName,String mlanguage,String mreleaseDate,String mRate,int mvoteCount,String mimage_url){
        name=mName;
        rate=mRate;
        image_url=mimage_url;
        vote_count=mvoteCount;
        id=mid;
        release_date=mreleaseDate;
        language=mlanguage;
    }

    public String getLanguage() {
        return language;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getOverview() {
        return overview;
    }

    public int getVote_count() {
        return vote_count;
    }

    public int getId() {
        return id;
    }
    public String getImage_backdrop_path() {
        return image_backdrop_path;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getRate() {
        return rate;
    }

    @Override
    public int compareTo(Film o) {
        if (Integer.parseInt(this.rate)<Integer.parseInt(o.rate))
            return 1;
        else if(Integer.parseInt(this.rate)>Integer.parseInt(o.rate))
            return -1;
        else
            return 0;
    }
}
