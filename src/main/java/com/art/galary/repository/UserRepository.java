package com.art.galary.repository;

import com.art.galary.models.User;

public interface UserRepository {
    public User findByEmail(String email);
    public boolean userExists(String email);
    public void save(User user);
    public User findByUserId(int id);
}
