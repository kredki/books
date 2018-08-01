package pl.jstk.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.jstk.constants.ModelConstants;
import pl.jstk.constants.ViewNames;
import pl.jstk.enumerations.BookStatus;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
public class BooksControllerTest {
    @Mock
    BookService bookService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    BooksController booksController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new BooksController()).build();
        MockitoAnnotations.initMocks(bookService);
        Mockito.reset(bookService);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        ReflectionTestUtils.setField(booksController, "bookService", bookService);
    }

    @Test
    @WithMockUser(username = "john", roles = {"ADMIN"})
    public void shouldShowBooks() throws Exception {
        // given
        long bookId1 = 1L;
        String title1 = "title";
        String authors1 = "authors";
        BookStatus status1 = BookStatus.FREE;
        BookTo book1 = new BookTo();
        book1.setId(bookId1);
        book1.setTitle(title1);
        book1.setAuthors(authors1);
        book1.setStatus(status1);
        long bookId2 = 1L;
        String title2 = "title";
        String authors2 = "authors";
        BookStatus status2 = BookStatus.FREE;
        BookTo book2 = new BookTo();
        book1.setId(bookId2);
        book1.setTitle(title2);
        book1.setAuthors(authors2);
        book1.setStatus(status2);
        List<BookTo> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        Mockito.when(bookService.findAllBooks()).thenReturn(books);

        // when
        ResultActions resultActions = mockMvc.perform(get("/books"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name(ViewNames.BOOKS))
                .andDo(print())
                .andExpect(model().attribute("bookList", hasSize(2)))
                .andExpect(model().attribute("bookList", hasItem(
                        allOf(
                                hasProperty("id", is(bookId1)),
                                hasProperty("title", is(title1)),
                                hasProperty("authors", is(authors1)),
                                hasProperty("status", is(status1))
                        )
                )))
                .andExpect(model().attribute("bookList", hasItem(
                        allOf(
                                hasProperty("id", is(bookId2)),
                                hasProperty("title", is(title2)),
                                hasProperty("authors", is(authors2)),
                                hasProperty("status", is(status2))
                        )
                )));

        verify(bookService, times(1)).findAllBooks();
        verifyNoMoreInteractions(bookService);
    }

    @Test
    @WithMockUser(username = "john", roles = {"ADMIN"})
    public void shouldShowBook() throws Exception {
        // given
        long bookId = 1L;
        String title = "title";
        String authors = "authors";
        BookStatus status = BookStatus.FREE;
        BookTo book = new BookTo();
        book.setId(bookId);
        book.setTitle(title);
        book.setAuthors(authors);
        book.setStatus(status);
        Mockito.when(bookService.findBooksById(bookId)).thenReturn(book);

        // when
        ResultActions resultActions = mockMvc.perform(get("/books/book?id=1"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name(ViewNames.BOOK))
                .andDo(print())
                .andExpect(model().attribute("book", hasProperty("id", is(bookId))))
                .andExpect(model().attribute("book", hasProperty("title", is(title))))
                .andExpect(model().attribute("book", hasProperty("authors", is(authors))))
                .andExpect(model().attribute("book", hasProperty("status", is(status))));

        verify(bookService, times(1)).findBooksById(Mockito.anyLong());
        verifyNoMoreInteractions(bookService);
    }

    @Test
    @WithMockUser(username = "john", roles = {"ADMIN"})
    public void shouldAddBook() throws Exception {
        // given
        long bookId = 1L;
        String title = "title";
        String authors = "authors";
        BookStatus status = BookStatus.FREE;
        BookTo book = new BookTo();
        book.setId(bookId);
        book.setTitle(title);
        book.setAuthors(authors);
        book.setStatus(status);
        Mockito.when(bookService.saveBook(book)).thenReturn(book);

        // when
        ResultActions resultActions = mockMvc.perform(post("/greeting"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name(ViewNames.WELCOME))
                .andDo(print())
                .andExpect(model().attribute(ModelConstants.INFO, "Book added."));
    }
}