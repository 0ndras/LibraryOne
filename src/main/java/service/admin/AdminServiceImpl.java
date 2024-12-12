package service.admin;

import model.User;
import repository.user.UserRepository;

import java.io.File;
import java.util.List;

public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;

    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean save(User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return false;
        }
        return userRepository.save(user);
    }

//    @Override
//    public File generateReport(String filePath) {
//    }
}
