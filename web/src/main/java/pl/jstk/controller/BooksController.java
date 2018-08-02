package pl.jstk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.jstk.constants.ModelConstants;
import pl.jstk.constants.ViewNames;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BooksController {
    private static final String BOOK_ADDED = "Book added.";
    private static final String BOOK_NOT_ADDED = "Book added.";
    private static final String BOOK_DELETED = "Book deleted.";
    protected static final String WELCOME = "This is a welcome page";

    @Autowired
    BookService bookService;

    @GetMapping(value = "/books")
    public String books(Model model) {
        model.addAttribute("bookList", bookService.findAllBooks());
        return ViewNames.BOOKS;

    }

    @GetMapping(value = "/books/book")
    public String book(@RequestParam(value = "id")long id, Model model) {
        model.addAttribute("book", bookService.findBooksById(id));
        return ViewNames.BOOK;
    }

    @GetMapping(value = "/books/add")
    public String addBookPage(Model model) {
        model.addAttribute("newBook", new BookTo());
        return ViewNames.ADD_BOOK;
    }

    @PostMapping(value = "/greeting")
    public String addBook(Model model, @ModelAttribute("newBook") @Valid BookTo bookToAdd) {
        BookTo savedBook = bookService.saveBook(bookToAdd);
        if(savedBook != null) {
            model.addAttribute(ModelConstants.INFO, BOOK_ADDED);
        } else {
            model.addAttribute(ModelConstants.INFO, BOOK_NOT_ADDED);
        }
        return ViewNames.WELCOME;
    }

    @GetMapping(value = "/books/search")
    public String searchPage(Model model) {
        model.addAttribute("searchBook", new BookTo());
        return ViewNames.SEARCH;
    }

    @PostMapping(value = "/search")
    public String searchBooks(Model model, @ModelAttribute("searchBook") BookTo book) {
        String title = book.getTitle();
        String authors = book.getAuthors();
        List<BookTo> result = new ArrayList<>();
        if(title != null && authors != null) {
            result = bookService.findBooksByAuthorAndTitle(authors, title);
        }
        model.addAttribute("bookList", result);
        return ViewNames.BOOKS;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/delete/book")
    public String deleteBook(@RequestParam(value = "id")long id, Model model) {
        bookService.deleteBook(id);
        model.addAttribute("deleted", BOOK_DELETED);
        model.addAttribute("bookList", bookService.findAllBooks());
        return ViewNames.BOOKS;
    }
}