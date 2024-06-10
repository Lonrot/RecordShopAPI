package schmalbach.recordshopapi.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import schmalbach.recordshopapi.model.Album;
import schmalbach.recordshopapi.model.Genre;
import schmalbach.recordshopapi.repository.AlbumRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@DataJpaTest
class AlbumServiceImplementationTest {

    @Mock
    private AlbumRepository albumRepositoryMock;
    @InjectMocks
    private AlbumServiceImplementation ASI; // AlbumServiceImplementation.class


    @Test
    void getAllAlbumsInStock() {
        List<Album> albums = new ArrayList<>();

        albums.add(new Album(1,"Mutter","Rammstein",2001, Genre.Heavy_metal,"Universal Music", 50.0,100));
        albums.add(new Album(2, "Sehnsucht", "Rammstein", 1997, Genre.Heavy_metal, "Motor Music", 45.0, 200));
        albums.add(new Album(3, "Reise, Reise", "Rammstein", 2004, Genre.Heavy_metal, "Universal Music", 55.0, 150));
        albums.add(new Album(4, "Liebe ist für alle da", "Rammstein", 2009, Genre.Heavy_metal, "Universal Music", 60.0, 180));
        albums.add(new Album(5, "Rammstein", "Rammstein", 2019, Genre.Heavy_metal, "Universal Music", 65.0, 120));

        //Everytime findALl() is invoke we return the list above;
        when(albumRepositoryMock.findAll()).thenReturn(albums);

        List<Album> resultList = ASI.getAllAlbumsInStock();
        for (Album album : resultList) {
            System.out.println(album);
        }

        assertThat(resultList).hasSize(5);

    }

    @Test
    void getAllAlbumsThatAreInStockOnly() {
        List<Album> albums = new ArrayList<>();

        albums.add(new Album(1,"Mutter","Rammstein",2001, Genre.Heavy_metal,"Universal Music", 50.0,100));
        albums.add(new Album(2, "Sehnsucht", "Rammstein", 1997, Genre.Heavy_metal, "Motor Music", 45.0, 200));
        albums.add(new Album(3, "Reise, Reise", "Rammstein", 2004, Genre.Heavy_metal, "Universal Music", 55.0, 150));
        albums.add(new Album(4, "Liebe ist für alle da", "Rammstein", 2009, Genre.Heavy_metal, "Universal Music", 60.0, 180));
        albums.add(new Album(5, "Rammstein", "Rammstein", 2019, Genre.Heavy_metal, "Universal Music", 65.0, 120));
        albums.add(new Album(6, "Herzeleid", "Rammstein", 1995, Genre.Heavy_metal, "Motor Music", 40.0, 250));
        albums.add(new Album(7, "Rosenrot", "Rammstein", 2005, Genre.Heavy_metal, "Universal Music", 55.0, 130));
        //Books below have no stock
        albums.add(new Album(8, "Liebe ist für alle da (Special Edition)", "Rammstein", 2009, Genre.Heavy_metal, "Universal Music", 70.0, 0));
        albums.add(new Album(9, "Made in Germany 1995–2011", "Rammstein", 2011, Genre.Heavy_metal, "Universal Music", 60.0, 0));
        albums.add(new Album(10, "XXI (The Vinyl Box Set)", "Rammstein", 2015, Genre.Heavy_metal, "Universal Music", 120.0, 0));


        //Everytime findALl() is invoke we return the list above;
        when(albumRepositoryMock.findAll()).thenReturn(albums);

        List<Album> resultList = ASI.getAllAlbumsInStock();
        for (Album album : resultList) {
            System.out.println(album);
        }

        assertThat(resultList).hasSize(7);
    }

    @Test
    void getAlbumByID() {
        List<Album> albums = new ArrayList<>();
        albums.add(new Album(1,"Mutter","Rammstein",2001, Genre.Heavy_metal,"Universal Music", 50.0,100));
        albums.add(new Album(2, "Sehnsucht", "Rammstein", 1997, Genre.Heavy_metal, "Motor Music", 45.0, 200));

        Album expectedAlbum = new Album(1,"Mutter","Rammstein",2001, Genre.Heavy_metal,"Universal Music", 50.0,100);
        when(albumRepositoryMock.findById(1L)).thenReturn(Optional.of(expectedAlbum));

        Album resultAlbum = ASI.getAlbumByID(1);
        assertThat(resultAlbum.getName()).isEqualTo(expectedAlbum.getName());

        Album expectedAlbumTwo = null;

        Album resultEmptyAlbum = ASI.getAlbumByID(0);
        assertNull(expectedAlbumTwo,"empty");

    }

    @Test
    void getAllAlbumsByArtist() {
    }

    @Test
    void getAllAlbumsByYear() {
    }

    @Test
    void getAllAlbumsByGenre() {
    }

    @Test
    void getAlbumInfo() {
    }

    @Test
    void addAlbum() {
    }

    @Test
    void updateAlbum() {
    }

    @Test
    void deleteAlbum() {
    }
}