package schmalbach.recordshopapi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import schmalbach.recordshopapi.model.Album;
import schmalbach.recordshopapi.model.Genre;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class AlbumRepositoryTest {

    @Autowired
    private AlbumRepository albumRepository;

    @Test
    void testFindAllAlbumsReturnAlbums(){
        Album album = new Album(1L,"Mutter","Rammstein",2001, Genre.Heavy_metal,"Universal",60.00,300);
        albumRepository.save(album);

        Iterable<Album> albumsList = albumRepository.findAll();

        assertThat(albumsList).hasSize(1);
    }

}