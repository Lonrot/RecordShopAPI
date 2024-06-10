package schmalbach.recordshopapi.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlbumTest {

    @Test
    void testGettersAndSetters(){
        Album albumTestOne = new Album();
        albumTestOne.setId(1L);
        albumTestOne.setName("Test Album");
        albumTestOne.setArtist("Test Artist");
        albumTestOne.setReleaseYear(2024);
        albumTestOne.setGenre(Genre.Rock);
        albumTestOne.setLabel("Test Label");
        albumTestOne.setPrice(9.99);
        albumTestOne.setStockQuantity(100);

        assertEquals(1L, albumTestOne.getId());
        assertEquals("Test Album", albumTestOne.getName());
        assertEquals("Test Artist", albumTestOne.getArtist());
        assertEquals(2024, albumTestOne.getReleaseYear());
        assertEquals("Rock", albumTestOne.getGenre());
        assertEquals("Test Label", albumTestOne.getLabel());
        assertEquals(9.99, albumTestOne.getPrice());
        assertEquals(100, albumTestOne.getStockQuantity());
    }
        @Test
        public void testToString() {
            Album album = new Album(1L, "Test Album", "Test Artist", 2021, Genre.Rock, "Test Label", 9.99, 100);
            String expectedString = "Album{id=1, name='Test Album', artist='Test Artist', releaseYear=2021, genre='Rock', label='Test Label', price=9.99, stockQuantity=100}";
            System.out.println(album.toString());
            assertEquals(expectedString, album.toString());
        }
}