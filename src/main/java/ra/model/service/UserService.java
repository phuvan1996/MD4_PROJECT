package ra.model.service;

import ra.model.entity.Users;

import java.util.List;

public interface UserService {
    Users findById(int id);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    Users saveOrUpdate(Users user);
    void deleteUser(int id);
    List<Users> findAll();
  Users getUserById(int userId);
}
