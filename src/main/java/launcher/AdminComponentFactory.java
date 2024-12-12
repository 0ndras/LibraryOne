package launcher;

import controller.AdminController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import mapper.UserMapper;
import repository.order.OrderRepository;
import repository.order.OrderRepositoryImpl;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.admin.AdminService;
import service.admin.AdminServiceImpl;
import service.order.OrderService;
import service.order.OrderServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import view.AdminView;
import view.model.UserDTO;

import java.sql.Connection;
import java.util.List;

public class AdminComponentFactory {
    private final AdminView adminView;
    private final AdminController adminController;
    private final UserRepository userRepository;
    private final AdminService adminService;
    private final AuthenticationService authenticationService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private static AdminComponentFactory instance;

    public static AdminComponentFactory getInstance(Boolean componentsForTest, Stage primaryStage) {
        if (instance == null) {
            instance = new AdminComponentFactory(componentsForTest, primaryStage);
        }
        return instance;
    }

    public AdminComponentFactory(Boolean componentsForTest, Stage primaryStage) {
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);

        this.orderRepository = new OrderRepositoryImpl(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository); // Corectat!
        this.adminService = new AdminServiceImpl(userRepository);
        List<UserDTO> userDTOs = UserMapper.convertUserListToUserDTOList(userRepository.findAll());
        this.adminView = new AdminView(primaryStage, userDTOs);
        this.authenticationService=new AuthenticationServiceMySQL(userRepository,rightsRolesRepository);
        this.orderService = new OrderServiceImpl(orderRepository);
        this.adminController = new AdminController(adminView, adminService, authenticationService, orderService);
    }

    public AdminView getAdminView() {
        return adminView;
    }

    public AdminController getAdminController() {
        return adminController;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public AdminService getAdminService() {
        return adminService;
    }
}
