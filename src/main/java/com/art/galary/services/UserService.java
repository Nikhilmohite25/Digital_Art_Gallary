package com.art.galary.services;

import com.art.galary.models.User;

public interface UserService {
    public void save(User user);
    public User findByUsername(String email);
    public boolean userExists(String email);
    public User findByUserId(int id);
}
