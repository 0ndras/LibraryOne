package controller;

import helper.LoggedInUser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.BookMapper;
import model.Order;
import model.builder.OrderBuilder;
import service.book.BookService;
import service.order.OrderService;
import view.BookView;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;

public class BookController {
    private final BookView bookView;
    private final BookService bookService;
    private final OrderService orderService;

    public BookController(BookView bookView, BookService bookService, OrderService orderService){
        this.bookView = bookView;
        this.bookService = bookService;
        this.orderService = orderService;

        this.bookView.addSaveButtonListener(new SaveButtonListener());
        this.bookView.addDeleteButtonListener(new DeleteButtonListener());
        this.bookView.addSellButtonListener(new SellButtonListener());
    }

    private class SaveButtonListener implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            String title = bookView.getTitle();
            String author = bookView.getAuthor();
            String stock = bookView.getStock();
            String price = bookView.getPrice();

            if (title.isEmpty() || author.isEmpty() || stock.isEmpty() || price.isEmpty()){
                bookView.addDisplayAlertMessage("Save Error", "Problem at Author, Title, Stock or Price fields", "Can not have any of the fields empty.");
            } else {
                BookDTO bookDTO = new BookDTOBuilder().setTitle(title).setAuthor(author).setStock(Integer.parseInt(stock)).setPrice(Integer.parseInt(price)).build();
                boolean savedBook = bookService.save(BookMapper.convertBookDTOToBook(bookDTO));

                if (savedBook){
                    bookView.addDisplayAlertMessage("Save Successful", "Book Added", "Book was successfully added to the database.");
                    bookView.addBookToObservableList(bookDTO);
                } else {
                    bookView.addDisplayAlertMessage("Save Error", "Problem at adding Book", "There was a problem at adding the book to the database. Please try again!");
                }
            }
        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();
            if (bookDTO != null){
                boolean deletionSuccessful = bookService.delete(BookMapper.convertBookDTOToBook(bookDTO));

                if (deletionSuccessful){
                    bookView.addDisplayAlertMessage("Delete Successful", "Book Deleted", "Book was successfully deleted from the database.");
                    bookView.removeBookFromObservableList(bookDTO);
                } else {
                    bookView.addDisplayAlertMessage("Delete Error", "Problem at deleting book", "There was a problem with the database. Please try again!");
                }
            } else {
                bookView.addDisplayAlertMessage("Delete Error", "Problem at deleting book", "You must select a book before pressing the delete button.");
            }
        }
    }

    private class SellButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();
            if (bookDTO != null) {
                if (bookDTO.getStock() > 0) {
                    bookDTO.setStock(bookDTO.getStock() - 1);

                    String saleDate = java.time.LocalDate.now().toString();
                    String loggedInUsername = LoggedInUser.getInstance().getUsername(); // Preluăm username-ul utilizatorului logat

                    Order order = new OrderBuilder()
                            .setBookTitle(bookDTO.getTitle())
                            .setSaleDate(java.time.LocalDate.now())
                            .setEmployeeUsername(loggedInUsername) // Setăm username-ul utilizatorului logat
                            .build();

                    String saleNotification = "The book \"" + bookDTO.getTitle() + "\" was sold on: " + saleDate;

                    boolean saleSuccessful = bookService.updateStock(BookMapper.convertBookDTOToBook(bookDTO));
                    boolean orderSuccesful = orderService.save(order);

                    if (saleSuccessful && orderSuccesful) {
                        bookView.getBookTableView().refresh();
                        bookView.addDisplayAlertMessage("Sale Successful", "Book Sold", saleNotification);
                    } else {
                        bookView.addDisplayAlertMessage("Sale Error", "Problem at updating stock", "There was a problem with updating the stock. Please try again.");
                    }
                } else {
                    bookView.addDisplayAlertMessage("Sale Error", "Out of Stock", "This book is out of stock.");
                }
            } else {
                bookView.addDisplayAlertMessage("Sale Error", "No Book Selected", "Please select a book to sell.");
            }
        }
    }
}