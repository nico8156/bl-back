package com.blogdelivres.bl.repository;
import com.blogdelivres.bl.entity.Library;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface LibraryRepository extends CrudRepository<Library, Long> {
    List<Library> findAllByUserId(Long Id);
    Optional<Library> findByLibraryId(Long libraryId);
}
