package com.blogdelivres.bl.repository;
import com.blogdelivres.bl.entity.Book;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {
    Optional<Book> getBookByGoogleId(String googleId);

}
