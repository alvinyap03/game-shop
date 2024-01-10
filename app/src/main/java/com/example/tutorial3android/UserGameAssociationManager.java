package com.example.tutorial3android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserGameAssociationManager {

    private static UserGameAssociationManager instance;
    private final Map<String, List<String>> userGameAssociations;

    private UserGameAssociationManager() {
        userGameAssociations = new HashMap<>();
    }

    public static UserGameAssociationManager getInstance() {
        if (instance == null) {
            instance = new UserGameAssociationManager();
        }
        return instance;
    }

    public void addUserGameAssociation(UserGameData association) {
        String username = association.getUsername();
        String gameName = association.getGameName();

        if (!userGameAssociations.containsKey(username)) {
            userGameAssociations.put(username, new ArrayList<>());
        }

        List<String> userGames = userGameAssociations.get(username);
        if (!userGames.contains(gameName)) {
            userGames.add(gameName);
        }
    }

    public List<String> getGamesForUser(String username) {
        return userGameAssociations.getOrDefault(username, new ArrayList<>());
    }
}
