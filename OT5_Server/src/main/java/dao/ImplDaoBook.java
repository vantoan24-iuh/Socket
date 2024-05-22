package dao;

import entity.Book;

import java.util.List;
import java.util.Map;

public interface ImplDaoBook {
    public List<Book>  listRatedBooks(String author, int rating);
    public Map<String,Long> countBooksByAuthor();
}
