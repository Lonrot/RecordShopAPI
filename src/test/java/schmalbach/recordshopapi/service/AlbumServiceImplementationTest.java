package schmalbach.recordshopapi.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import schmalbach.recordshopapi.exception.AlbumAlreadyExist;
import schmalbach.recordshopapi.exception.AlbumNotFoundException;
import schmalbach.recordshopapi.model.Album;
import schmalbach.recordshopapi.model.Genre;
import schmalbach.recordshopapi.repository.AlbumRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@DataJpaTest
class AlbumServiceImplementationTest {

    @Mock
    AlbumRepository albumRepositoryMock;

    @InjectMocks
    private AlbumServiceImplementation ASI; // AlbumServiceImplementation.class


    @Test
    void getAllAlbumsInStock() {
        List<Album> albums = new ArrayList<>();

        albums.add(new Album(1L,"Mutter","Rammstein",2001, Genre.HEAVY_METAL,"Universal Music", 50.0,100));
        albums.add(new Album(2L, "Sehnsucht", "Rammstein", 1997, Genre.HEAVY_METAL, "Motor Music", 45.0, 200));
        albums.add(new Album(3L, "Reise, Reise", "Rammstein", 2004, Genre.HEAVY_METAL, "Universal Music", 55.0, 150));
        albums.add(new Album(4L, "Liebe ist für alle da", "Rammstein", 2009, Genre.HEAVY_METAL, "Universal Music", 60.0, 180));
        albums.add(new Album(5L, "Rammstein", "Rammstein", 2019, Genre.HEAVY_METAL, "Universal Music", 65.0, 120));

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

        albums.add(new Album(1L,"Mutter","Rammstein",2001, Genre.HEAVY_METAL,"Universal Music", 50.0,100));
        albums.add(new Album(2L, "Sehnsucht", "Rammstein", 1997, Genre.HEAVY_METAL, "Motor Music", 45.0, 200));
        albums.add(new Album(3L, "Reise, Reise", "Rammstein", 2004, Genre.HEAVY_METAL, "Universal Music", 55.0, 150));
        albums.add(new Album(4L, "Liebe ist für alle da", "Rammstein", 2009, Genre.HEAVY_METAL, "Universal Music", 60.0, 180));
        albums.add(new Album(5L, "Rammstein", "Rammstein", 2019, Genre.HEAVY_METAL, "Universal Music", 65.0, 120));
        albums.add(new Album(6L, "Herzeleid", "Rammstein", 1995, Genre.HEAVY_METAL, "Motor Music", 40.0, 250));
        albums.add(new Album(7L, "Rosenrot", "Rammstein", 2005, Genre.HEAVY_METAL, "Universal Music", 55.0, 130));
        //Books below have no stock
        albums.add(new Album(8L, "Liebe ist für alle da (Special Edition)", "Rammstein", 2009, Genre.HEAVY_METAL, "Universal Music", 70.0, 0));
        albums.add(new Album(9L, "Made in Germany 1995–2011", "Rammstein", 2011, Genre.HEAVY_METAL, "Universal Music", 60.0, 0));
        albums.add(new Album(10L, "XXI (The Vinyl Box Set)", "Rammstein", 2015, Genre.HEAVY_METAL, "Universal Music", 120.0, 0));


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
        albums.add(new Album(1L, "Mutter", "Rammstein", 2001, Genre.HEAVY_METAL, "Universal Music", 50.0, 100));
        albums.add(new Album(2L, "Sehnsucht", "Rammstein", 1997, Genre.HEAVY_METAL, "Motor Music", 45.0, 200));


        when(albumRepositoryMock.findById(1L)).thenReturn(Optional.of(albums.getFirst()));
        when(albumRepositoryMock.findById(0L)).thenReturn(Optional.empty());


        // Test with valid ID (1L)
        Album resultAlbum = ASI.getAlbumByID(1L);
        assertThat(resultAlbum).isNotNull();
        assertThat(resultAlbum.getName()).isEqualTo("Mutter");


        Album resultEmptyAlbum = ASI.getAlbumByID(0L);
        assertNull(resultEmptyAlbum, "Expected null for non-existing album");
    }

    @Test
    void getAllAlbumsByArtist() {
        List<Album> albums = new ArrayList<>();


        albums.add(new Album(1L,"Mutter","Rammstein",2001, Genre.HEAVY_METAL,"Universal Music", 50.0,100));
        albums.add(new Album(2L, "Sehnsucht", "NotRammstein", 1997, Genre.HEAVY_METAL, "Motor Music", 45.0, 200));

        albums.add(new Album(3L, "Reise, Reise", "Rammstein", 2004, Genre.HEAVY_METAL, "Universal Music", 55.0, 150));
        albums.add(new Album(4L, "Liebe ist für alle da", "NotRammstein", 2009, Genre.HEAVY_METAL, "Universal Music", 60.0, 180));
        albums.add(new Album(5L, "Rammstein", "NotRammstein", 2019, Genre.HEAVY_METAL, "Universal Music", 65.0, 120));
        albums.add(new Album(6L, "Herzeleid", "NotRammstein", 1995, Genre.HEAVY_METAL, "Motor Music", 40.0, 250));
        albums.add(new Album(7L, "Rosenrot", "NotRammstein", 2005, Genre.HEAVY_METAL, "Universal Music", 55.0, 130));
        //Books below have no stock and some are Rammstein
        albums.add(new Album(8L, "Liebe ist für alle da (Special Edition)", "Rammstein", 2009, Genre.HEAVY_METAL, "Universal Music", 70.0, 0));
        albums.add(new Album(9L, "Made in Germany 1995–2011", "Rammstein", 2011, Genre.HEAVY_METAL, "Universal Music", 60.0, 0));
        albums.add(new Album(10L, "XXI (The Vinyl Box Set)", "NotRammstein", 2015, Genre.HEAVY_METAL, "Universal Music", 120.0, 0));

        when(albumRepositoryMock.findAll()).thenReturn(albums);
        List<Album> resultAlbums = ASI.getAllAlbumsByArtist("Rammstein");

        assertThat(resultAlbums).hasSize(2);
    }

    @Test
    void getAllAlbumsByArtistThatIsNotInTheList() {
        List<Album> albums = new ArrayList<>();

        albums.add(new Album(1L,"Mutter","Rammstein",2001, Genre.HEAVY_METAL,"Universal Music", 50.0,100));
        albums.add(new Album(2L, "Sehnsucht", "Rammstein", 1997, Genre.HEAVY_METAL, "Motor Music", 45.0, 200));
        albums.add(new Album(3L, "Reise, Reise", "Rammstein", 2004, Genre.HEAVY_METAL, "Universal Music", 55.0, 150));
        albums.add(new Album(4L, "Liebe ist für alle da", "Rammstein", 2009, Genre.HEAVY_METAL, "Universal Music", 60.0, 180));
        albums.add(new Album(5L, "Rammstein", "Rammstein", 2019, Genre.HEAVY_METAL, "Universal Music", 65.0, 120));
        albums.add(new Album(6L, "Herzeleid", "Rammstein", 1995, Genre.HEAVY_METAL, "Motor Music", 40.0, 250));
        albums.add(new Album(7L, "Rosenrot", "Rammstein", 2005, Genre.HEAVY_METAL, "Universal Music", 55.0, 130));
        albums.add(new Album(8L, "Liebe ist für alle da (Special Edition)", "Rammstein", 2009, Genre.HEAVY_METAL, "Universal Music", 70.0, 0));
        albums.add(new Album(9L, "Made in Germany 1995–2011", "Rammstein", 2011, Genre.HEAVY_METAL, "Universal Music", 60.0, 0));
        albums.add(new Album(10L, "XXI (The Vinyl Box Set)", "Rammstein", 2015, Genre.HEAVY_METAL, "Universal Music", 120.0, 0));

        when(albumRepositoryMock.findAll()).thenReturn(albums);
        List<Album> resultAlbums = ASI.getAllAlbumsByArtist("Pink Floyd");

        assertThat(resultAlbums).hasSize(0);
    }

    @Test
    void getAllAlbumsByYear() {
        List<Album> albums = new ArrayList<>();

        albums.add(new Album(1L, "Rock Album", "Rock Artist", 2000, Genre.ROCK, "Rock Label", 9.99, 10));
        albums.add(new Album(2L, "Pop Album", "Pop Artist", 2001, Genre.POP, "Pop Label", 8.99, 20));
        albums.add(new Album(3L, "Jazz Album", "Jazz Artist", 2002, Genre.JAZZ, "Jazz Label", 7.99, 30));
        albums.add(new Album(4L, "Reggaeton Album", "Reggaeton Artist", 2003, Genre.REGGAETON, "Reggaeton Label", 6.99, 40));
        albums.add(new Album(5L, "HipHop Album", "HipHop Artist", 2004, Genre.HIP_HOP, "HipHop Label", 5.99, 50));
        albums.add(new Album(6L, "Electronic Album", "Electronic Artist", 2005, Genre.ELECTRONIC, "Electronic Label", 4.99, 60));
        albums.add(new Album(7L, "Heavy Metal Album", "Heavy Metal Artist", 2006, Genre.HEAVY_METAL, "Heavy Metal Label", 3.99, 70));
        albums.add(new Album(8L, "Blues Album", "Blues Artist", 2007, Genre.BLUES, "Blues Label", 2.99, 80));
        albums.add(new Album(9L, "Phonk Album", "Phonk Artist", 2024, Genre.PHONK, "Phonk Label", 1.99, 90));
        albums.add(new Album(10L, "LoFi Album", "LoFi Artist", 2024, Genre.LO_FI, "LoFi Label", 0.99, 100));

        when(albumRepositoryMock.findAll()).thenReturn(albums);
        List<Album> resultList2024 = ASI.getAllAlbumsByYear(2024);
        List<Album> resultList2000 = ASI.getAllAlbumsByYear(2000);

        assertThat(resultList2024).hasSize(2);
        assertThat(resultList2000).hasSize(1);

    }

    @Test
    void getAllAlbumsByYearNotInTheList() {
        List<Album> albums = new ArrayList<>();

        albums.add(new Album(1L, "Rock Album", "Rock Artist", 2000, Genre.ROCK, "Rock Label", 9.99, 10));
        albums.add(new Album(2L, "Pop Album", "Pop Artist", 2001, Genre.POP, "Pop Label", 8.99, 20));
        albums.add(new Album(3L, "Jazz Album", "Jazz Artist", 2002, Genre.JAZZ, "Jazz Label", 7.99, 30));
        albums.add(new Album(4L, "Reggaeton Album", "Reggaeton Artist", 2003, Genre.REGGAETON, "Reggaeton Label", 6.99, 40));
        albums.add(new Album(5L, "HipHop Album", "HipHop Artist", 2004, Genre.HIP_HOP, "HipHop Label", 5.99, 50));
        albums.add(new Album(6L, "Electronic Album", "Electronic Artist", 2005, Genre.ELECTRONIC, "Electronic Label", 4.99, 60));
        albums.add(new Album(7L, "Heavy Metal Album", "Heavy Metal Artist", 2006, Genre.HEAVY_METAL, "Heavy Metal Label", 3.99, 70));
        albums.add(new Album(8L, "Blues Album", "Blues Artist", 2007, Genre.BLUES, "Blues Label", 2.99, 80));
        albums.add(new Album(9L, "Phonk Album", "Phonk Artist", 2024, Genre.PHONK, "Phonk Label", 1.99, 90));
        albums.add(new Album(10L, "LoFi Album", "LoFi Artist", 2024, Genre.LO_FI, "LoFi Label", 0.99, 100));

        when(albumRepositoryMock.findAll()).thenReturn(albums);
        List<Album> resultList = ASI.getAllAlbumsByYear(3555);

        assertThat(resultList).hasSize(0);
    }

    @Test
    void getAllAlbumsByGenre() {
        List<Album> albums = new ArrayList<>();

        albums.add(new Album(1L, "Rock Album", "Rock Artist", 2000, Genre.ROCK, "Rock Label", 9.99, 10));
        albums.add(new Album(2L, "Pop Album", "Pop Artist", 2001, Genre.POP, "Pop Label", 8.99, 20));
        albums.add(new Album(3L, "Jazz Album", "Jazz Artist", 2002, Genre.JAZZ, "Jazz Label", 7.99, 30));
        albums.add(new Album(4L, "Reggaeton Album", "Reggaeton Artist", 2003, Genre.REGGAETON, "Reggaeton Label", 6.99, 40));
        albums.add(new Album(5L, "Rock Album 2", "Rock Artist 2", 2004, Genre.ROCK, "Rock Label", 5.99, 50));


        when(albumRepositoryMock.findByGenre(Genre.ROCK)).thenReturn(albums.subList(0, 2));
        when(albumRepositoryMock.findByGenre(Genre.POP)).thenReturn(albums.subList(1, 2));


        List<Album> albumRock = ASI.getAllAlbumsByGenre(Genre.ROCK);
        assertThat(albumRock).hasSize(2);

        List<Album> albumPop = ASI.getAllAlbumsByGenre(Genre.POP);
        assertThat(albumPop).hasSize(1);

    }

    @Test
    void getAlbumInfoByName() {
        Album mutter = new Album(1L,"Mutter","Rammstein",2001, Genre.HEAVY_METAL,"Universal Music", 50.0,100);

        when(albumRepositoryMock.findByNameIgnoreCase("Mutter")).thenReturn(Optional.of(mutter));

        Album resultAlbum = ASI.getAlbumInfoByName("Mutter");

        assertThat(resultAlbum).isNotNull();
        assertEquals("Mutter", resultAlbum.getName());
        assertEquals(Genre.HEAVY_METAL, resultAlbum.getGenre());
    }

    @Test
    @DisplayName("add Album")
    void addAlbum() {
        Album mutter = new Album(1L,"Mutter","Rammstein",2001, Genre.HEAVY_METAL,"Universal Music", 50.0,100);
        when(albumRepositoryMock.save(mutter)).thenReturn(mutter);

        try {
            Album resultAlbum = ASI.addAlbum(mutter);
            assertEquals(mutter, resultAlbum);
        } catch (AlbumAlreadyExist e){
            fail("AlbumAlreadyExist exception was thrown, NOT EXPECTED IN THIS TASTE CASE");
        }
    }
    @Test
    @DisplayName("Album to Add already in DB")
    void addAlbumAlreadyInDB() throws AlbumAlreadyExist {
        Album mutter = new Album(1L,"Mutter","Rammstein",2001, Genre.HEAVY_METAL,"Universal Music", 50.0,100);
        when(albumRepositoryMock.findByNameIgnoreCase(mutter.getName())).thenReturn(Optional.of(mutter));

        assertThrows(AlbumAlreadyExist.class, () -> ASI.addAlbum(mutter));
        verify(albumRepositoryMock, times(1)).findByNameIgnoreCase(mutter.getName());
    }

    @Test
    @DisplayName("update existing Album")
    void updateAlbumInDB() {

        Album albumInDB = new Album(1L, "Mutter", "Rammstein", 2001, Genre.HEAVY_METAL, "Universal Music", 50.0, 100);
        Album albumInput = new Album(1L, "Mutter Updated", "Rammstein Updated", 2010, Genre.HEAVY_METAL, "Universal Music Updated", 50.0, 100);


        when(albumRepositoryMock.findById(1L)).thenReturn(Optional.of(albumInDB));
        when(albumRepositoryMock.save(any(Album.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Return the saved entity


        Album updatedAlbum = ASI.updateAlbum(1L, albumInput);


        assertEquals(albumInput.getName(), updatedAlbum.getName());
        assertEquals(albumInput.getArtist(), updatedAlbum.getArtist());
        assertEquals(albumInput.getReleaseYear(), updatedAlbum.getReleaseYear());
        assertEquals(albumInput.getGenre(), updatedAlbum.getGenre());
        assertEquals(albumInput.getLabel(), updatedAlbum.getLabel());
        assertEquals(albumInput.getPrice(), updatedAlbum.getPrice());
        assertEquals(albumInput.getStockQuantity(), updatedAlbum.getStockQuantity());


        verify(albumRepositoryMock, times(1)).findById(1L);
        verify(albumRepositoryMock, times(1)).save(any(Album.class)); // Verify that save was called with any Album
    }

    @Test
    @DisplayName("Update non Existing Album in DB")
    void testUpdateNonExistingAlbum() {
        Album nonExistingAlbum = new Album(1L,"Mutter","Rammstein",2001, Genre.HEAVY_METAL,"Universal Music", 50.0,100);
        when(albumRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AlbumNotFoundException.class, () -> {
            ASI.updateAlbum(1L,nonExistingAlbum);
        });
    }


    @Test
    void deleteAlbumByID() {

        Album album = new Album(1L,"Mutter","Rammstein",2001, Genre.HEAVY_METAL,"Universal Music", 50.0,100);
        when(albumRepositoryMock.findById(1L)).thenReturn(Optional.of(album));

        ASI.deleteAlbumByID(1L);

        verify(albumRepositoryMock,times(1)).findById(1L);
        verify(albumRepositoryMock,times(1)).delete(album);

    }

}