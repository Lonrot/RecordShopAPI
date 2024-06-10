package schmalbach.recordshopapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import schmalbach.recordshopapi.model.Album;

@Repository
public interface AlbumRepository extends CrudRepository<Album, Long> {

}
