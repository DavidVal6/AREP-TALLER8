package edu.eci.arep.response;

import edu.eci.arep.model.User;

public class UserResponse {
    private String user;

    public UserResponse() {
    }

    public UserResponse(User user) {
        this.user = user.getUsername();
    }

    public String getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user.getUsername();
    }
}
