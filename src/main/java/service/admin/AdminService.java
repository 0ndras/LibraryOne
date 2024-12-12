package service.admin;

import model.Book;
import model.User;

import java.io.File;
import java.util.List;

public interface AdminService {
    List<User> findAll();
    //User findById(Long id);
    boolean save(User user);
    //File generateReport (String filePath);
}
