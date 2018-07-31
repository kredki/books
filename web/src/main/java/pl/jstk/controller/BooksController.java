package pl.jstk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import pl.jstk.constants.ModelConstants;
import pl.jstk.constants.ViewNames;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

@Controller
public class BooksController {
    private static final String BOOK_ADDED = "Book added.";
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
        model.addAttribute("book", bookService.findBooksById(id).get(0));
        return ViewNames.BOOK;
    }

    @GetMapping(value = "/books/add")
    public String addBookPage(Model model) {
        model.addAttribute("newBook", new BookTo());
        return ViewNames.ADD_BOOK;
    }

    @PostMapping(value = "/greeting")
    public String addBook(Model model, @RequestBody BookTo bookToAdd) {
        BookTo savedBook = bookService.saveBook(bookToAdd);
        if(savedBook != null) {
            model.addAttribute(ModelConstants.INFO, BOOK_ADDED);
        }
        return ViewNames.WELCOME;
    }

    @GetMapping(value = "/books/search")
    public String searchPage(Model model) {
        return "search";
    }
}
