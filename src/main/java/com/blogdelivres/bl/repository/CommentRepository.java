package com.blogdelivres.bl.repository;

import com.blogdelivres.bl.entity.Book;
import com.blogdelivres.bl.entity.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findAllByBook(Book book);
}
