package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import view.model.UserDTO;

import java.util.List;

public class AdminView {
    private TableView<UserDTO> userTableView;
    private final ObservableList<UserDTO> usersObservableList;
    private TextField usernameTextField;
    private TextField passwordTextField;
    private Label usernameLabel;
    private Label passwordLabel;
    private Button addEmployeeButton;
    private Button generateReportButton;

    public AdminView(Stage primaryStage, List<UserDTO> users) {
        primaryStage.setTitle("Admin Dashboard");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);

        usersObservableList = FXCollections.observableArrayList(users);
        initTableView(gridPane);

        initAdminOperations(gridPane);

        primaryStage.show();
    }

    private void initializeGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void initTableView(GridPane gridPane) {
        userTableView = new TableView<>();
        userTableView.setPlaceholder(new Label("No users to display"));

        TableColumn<UserDTO, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        //TableColumn<UserDTO, String> passwordColumn = new TableColumn<>("Hashed Password");
        //passwordColumn.setCellValueFactory(new PropertyValueFactory<>("hashedPassword"));

        TableColumn<UserDTO, String> rolesColumn = new TableColumn<>("Roles");
        rolesColumn.setCellValueFactory(new PropertyValueFactory<>("roles"));

        userTableView.getColumns().add(usernameColumn);
        userTableView.getColumns().add(rolesColumn);

        userTableView.setItems(usersObservableList);

        gridPane.add(userTableView, 0, 0, 5, 1);
    }

    private void initAdminOperations(GridPane gridPane) {
        usernameLabel = new Label("Username:");
        gridPane.add(usernameLabel, 1, 1);

        usernameTextField = new TextField();
        gridPane.add(usernameTextField, 2, 1);

        passwordLabel = new Label("Password:");
        gridPane.add(passwordLabel, 3, 1);

        passwordTextField = new TextField();
        gridPane.add(passwordTextField, 4, 1);

        addEmployeeButton = new Button("Add Employee");
        gridPane.add(addEmployeeButton, 5, 1);

        generateReportButton = new Button("Generate Report");
        gridPane.add(generateReportButton, 5, 2);
    }

    public void addAddEmployeeButtonListener(EventHandler<ActionEvent> addEmployeeButtonListener) {
        addEmployeeButton.setOnAction(addEmployeeButtonListener);
    }

    public void addGenerateReportButtonListener(EventHandler<ActionEvent> generateReportButtonListener) {
        generateReportButton.setOnAction(generateReportButtonListener);
    }

    public void addDisplayAlertMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public String getUsername() {
        return usernameTextField.getText();
    }

    public String getPassword() {
        return passwordTextField.getText();
    }

    public void addUserToObservableList(UserDTO userDTO) {
        this.usersObservableList.add(userDTO);
    }

    public TableView<UserDTO> getUserTableView() {
        return userTableView;
    }
}
