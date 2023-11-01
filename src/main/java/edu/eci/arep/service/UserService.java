package edu.eci.arep.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import edu.eci.arep.model.User;
import edu.eci.arep.response.UserResponse;
import jakarta.enterprise.context.ApplicationScoped;
import org.mindrot.jbcrypt.BCrypt;

@ApplicationScoped
public class UserService {
    private final Set<User> users;

    public UserService() {
        users = Collections.synchronizedSet(new HashSet<>());
        addUser(new User("lucas", "123"));
    }

    public Set<UserResponse> list() {
        return users.stream()
                .map(UserResponse::new)
                .collect(Collectors.toSet());
    }

    public Set<UserResponse> add(User newUser) {
        addUser(newUser);
        return list();
    }

    public UserResponse check(User user){
        User userRegistered = findUser(user.getUsername());
        if (userRegistered != null && BCrypt.checkpw(user.getPassword(), userRegistered.getPassword())) {
            return new UserResponse(userRegistered);
        }
        return null;
    }

    private void addUser(User user) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        users.add(new User(user.getUsername(), hashedPassword));
    }

    private User findUser(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
    
}
