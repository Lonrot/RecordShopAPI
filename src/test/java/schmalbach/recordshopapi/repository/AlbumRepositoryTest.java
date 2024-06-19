package schmalbach.recordshopapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import schmalbach.recordshopapi.model.Album;
import schmalbach.recordshopapi.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
class AlbumRepositoryTest {

    @Autowired
    private AlbumRepository albumRepository;

    /*
     * This test utilizes the "dev" profile, configuring an H2 in-memory database specifically for testing purposes.
     *
     * Key Details:
     * - Database: An H2 in-memory database is employed, ensuring a fresh, isolated database instance for each test run.
     * - Data Population: The database is populated with a predefined schema and data, facilitating consistent and repeatable tests.
     * - Schema Initialization: The database schema is defined in the "schema.sql" file located in the "resources" directory.
     * - Data Insertion: The "data.sql" file, also located in the "resources" directory, contains SQL statements to insert 20 entries into the database.
     * - Automated Setup: Spring Boot automatically executes the 'schema.sql' and "data.sql" scripts during the test context initialization,
     *  ensuring the database is ready with the necessary schema and data before any tests are executed.
     */

    @BeforeEach
    void setUp() {
        Album album1 = new Album();
        album1.setName("Album test 1");
        album1.setArtist("Artist test 1");
        album1.setGenre(Genre.ROCK);
        albumRepository.save(album1);

        Album album2 = new Album();
        album2.setName("Album test 2");
        album2.setArtist("Artist test 2");
        album2.setGenre(Genre.POP);
        albumRepository.save(album2);

        Album album3 = new Album();
        album3.setName("Album test 3");
        album3.setArtist("Artist test 3");
        album3.setGenre(Genre.HEAVY_METAL);
        albumRepository.save(album3);
    }

    @Test
    @DisplayName("Find album by name")
    void findByNameIgnoreCase() {
        Optional<Album> foundAlbum = albumRepository.findByNameIgnoreCase("Thriller");

        assertTrue(foundAlbum.isPresent());
        assertEquals("Thriller", foundAlbum.get().getName());
        assertEquals("Michael Jackson", foundAlbum.get().getArtist());
        System.out.println(foundAlbum.get().getName());
    }
    @Test
    @DisplayName("Find album by name - no input")
    void findByNameIgnoreCase_emptyString() {
        Optional<Album> foundAlbum = albumRepository.findByNameIgnoreCase("");

        assertFalse(foundAlbum.isPresent());
    }

    @Test
    @DisplayName("Find Albums by genre")
    void findByGenre() {
        List<Album> foundAlbum = albumRepository.findByGenre(Genre.ROCK);
        // 6 Album with Rock genre in the DB can be found in the data.sql file in resources.

        assertThat(foundAlbum,hasSize(6));
        assertEquals(Genre.ROCK, foundAlbum.getFirst().getGenre());
        assertEquals(Genre.ROCK, foundAlbum.get(1).getGenre());
        assertEquals(Genre.ROCK, foundAlbum.getLast().getGenre());

    }
    @Test
    @DisplayName("Find Albums by genre - no albums")
    void findByGenre_noAlbums() {
        List<Album> foundAlbum = albumRepository.findByGenre(null);

        assertThat(foundAlbum, hasSize(0));
    }
}