package com.example.tutorial3android;

public class UserGameData {
    private String username;
    private String game_name;

    public UserGameData(String username, String game_name) {
        this.username = username;
        this.game_name = game_name;
    }

    public String getUsername() {
        return username;
    }

    public String getGameName() {
        return game_name;
    }
}
