package schmalbach.recordshopapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import schmalbach.recordshopapi.model.Album;
import schmalbach.recordshopapi.model.Genre;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends CrudRepository<Album, Long> {

    //Custom query to find album by name , case-insensitive.
    Optional<Album> findByNameIgnoreCase(String name);
    List<Album> findByGenre(Genre genre);
    List<Album> findByArtistIgnoreCase(String artist);
    List<Album> findByReleaseYear(int releaseYear);

}
