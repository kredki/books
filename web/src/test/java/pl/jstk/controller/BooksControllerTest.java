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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.jstk.constants.ViewNames;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
        List<BookTo> books = new ArrayList<>();
        books.add(new BookTo());
        books.add(new BookTo());
        Mockito.when(bookService.findAllBooks()).thenReturn(books);
        // when
        ResultActions resultActions = mockMvc.perform(get("/books"));
        // then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name(ViewNames.BOOKS));
                //.andDo(print())
                //.andExpect(model().attribute("bookList", bookService.findAllBooks()))
                //.andExpect(content().string(containsString("")));

    }
}