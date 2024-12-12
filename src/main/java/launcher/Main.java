package launcher;

import model.Book;
import model.builder.BookBuilder;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args){
//        Book book = new BookBuilder()
//                .setTitle("Ion")
//                .setAuthor("Liviu Rebreanu")
//                .setPublishedDate(LocalDate.of(1910, 10, 20))
//                .setStock(5)
//                .setPrice(50)
//                .build();
//
//        System.out.println(book);
        Launcher.main(args);
    }
}
