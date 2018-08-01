package pl.jstk.service;

import pl.jstk.to.BookTo;

import java.util.List;

public interface BookService {

    List<BookTo> findAllBooks();
    List<BookTo> findBooksByTitle(String title);
    List<BookTo> findBooksByAuthor(String author);
    List<BookTo> findBooksByAuthorAndTitle(String author, String title);
    BookTo findBooksById(long id);

    BookTo saveBook(BookTo book);
    void deleteBook(Long id);
}
