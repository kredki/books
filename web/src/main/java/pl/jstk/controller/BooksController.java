package pl.jstk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.jstk.constants.ViewNames;
import pl.jstk.service.BookService;

@Controller
public class BooksController {
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
}
