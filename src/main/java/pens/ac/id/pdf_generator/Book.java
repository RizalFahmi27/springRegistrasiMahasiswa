package pens.ac.id.pdf_generator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {
    private String title;
    private String author;
    private String isbn;
    private String publishedDate;
    private float price;
 
    public Book(String title, String author, String isbn, String publishedDate,
            float price) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishedDate = publishedDate;
        this.price = price;
    }
 
    // getters and setters
 
}