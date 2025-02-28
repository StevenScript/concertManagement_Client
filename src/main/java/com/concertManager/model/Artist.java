package com.concertManager.model;

public class Artist {
    private Long id;
    private String stageName;
    private String genre;
    private Integer membersCount;
    private String homeCity;

    // Getters/Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getStageName() {
        return stageName;
    }
    public void setStageName(String stageName) {
        this.stageName = stageName;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public Integer getMembersCount() {
        return membersCount;
    }
    public void setMembersCount(Integer membersCount) {
        this.membersCount = membersCount;
    }
    public String getHomeCity() {
        return homeCity;
    }
    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }
}
