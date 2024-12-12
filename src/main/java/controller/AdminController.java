package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Order;
import model.Right;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validator.Notification;
import service.admin.AdminService;
import service.order.OrderService;
import service.user.AuthenticationService;
import view.AdminView;
import view.model.UserDTO;
import view.model.builder.UserDTOBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static database.Constants.Roles.*;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class AdminController {
    private final AdminView adminView;
    private final AdminService adminService;
    private final AuthenticationService authenticationService;
    private final OrderService orderService;

    public AdminController(AdminView adminView, AdminService adminService, AuthenticationService authenticationService, OrderService orderService) {
        this.adminView = adminView;
        this.adminService = adminService;
        this.authenticationService=authenticationService;
        this.orderService=orderService;

        this.adminView.addAddEmployeeButtonListener(new AddEmployeeButtonListener());
        this.adminView.addGenerateReportButtonListener(new GenerateReportButtonListener());
    }

    private class AddEmployeeButtonListener implements EventHandler<ActionEvent> {
            @Override
            public void handle (ActionEvent event){
                String username = adminView.getUsername();
                String password = adminView.getPassword();

                if(username.isEmpty()||password.isEmpty())
                    adminView.addDisplayAlertMessage("Save Error", "Problem at Author or Title fields", "Can not have an empty Title or Author field.");
                else {

                    UserDTO userDTO = new UserDTOBuilder()
                            .setUsername(username)
                            .setPassword(password)
                            .setRoles(FXCollections.observableArrayList())
                            .build();

                    Notification<Boolean> registerNotification = authenticationService.register(username, password);

                    if (registerNotification.hasErrors())
                    {
                        adminView.addDisplayAlertMessage("Save Failed", "Trouble Saving Employee", "Employee " + username + " was not added to the database.");
                    } else {
                        adminView.addDisplayAlertMessage("Save Successful", "Employee Added", "Employee " + username + " was successfully added to the database.");
                        adminView.addUserToObservableList(userDTO);
                    }
                }
            }
    }

    public class GenerateReportButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            UserDTO selectedUser = (UserDTO) adminView.getUserTableView().getSelectionModel().getSelectedItem();

            if (selectedUser == null) {
                adminView.addDisplayAlertMessage("Report Error", "No User Selected", "Please select a user to generate the report.");
                return;
            }

            List<Order> userOrders = orderService.findAllByUsername(selectedUser.getUsername());

            if (userOrders.isEmpty()) {
                adminView.addDisplayAlertMessage("Report Error", "No Orders Found", "This user has no orders to generate a report.");
                return;
            }

            try {
                String fileName = "Report_" + selectedUser.getUsername() + ".pdf";
                PdfWriter writer = new PdfWriter(new FileOutputStream(fileName));
                PdfDocument pdfDocument = new PdfDocument(writer);
                Document document = new Document(pdfDocument);

                document.add(new Paragraph("Order Report for User: " + selectedUser.getUsername()));
                document.add(new Paragraph("--------------------------------------------------"));

                for (Order order : userOrders) {
                    document.add(new Paragraph("Book Title: " + order.getBookTitle()));
                    document.add(new Paragraph("Sale Date: " + order.getSaleDate()));
                    document.add(new Paragraph("--------------------------------------------------"));
                }

                document.close();
                adminView.addDisplayAlertMessage("Report Generated", "Success", "The report has been saved as: " + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                adminView.addDisplayAlertMessage("Report Error", "Failed to Generate Report", "An error occurred while generating the report.");
            }
        }
    }

}
