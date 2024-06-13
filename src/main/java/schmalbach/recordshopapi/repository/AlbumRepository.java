package schmalbach.recordshopapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import schmalbach.recordshopapi.model.Album;

import java.util.Optional;

@Repository
public interface AlbumRepository extends CrudRepository<Album, Long> {

    //Custom query to find album by name , case-insensitive.
    Optional<Album> findByNameIgnoreCase(String name);
   // Optional<Album> updateById(Long id, Album album);
}
