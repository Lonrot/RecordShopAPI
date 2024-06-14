package schmalbach.recordshopapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import schmalbach.recordshopapi.model.Album;
import schmalbach.recordshopapi.model.Genre;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import schmalbach.recordshopapi.service.AlbumServiceImplementation;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
class AlbumControllerTest {

    @Mock
    private AlbumServiceImplementation albumServiceImplementation;

    @InjectMocks
    private AlbumController albumController;

    @Autowired
    private MockMvc mockMvcController;
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mockMvcController = MockMvcBuilders.standaloneSetup(albumController).build();
        mapper = new ObjectMapper();
    }

    @Test
    void getAllAlbums() throws Exception {
        List<Album> albums = new ArrayList<>();

        albums.add(new Album(1L, "Rock Album", "Rock Artist", 2000, Genre.ROCK, "Rock Label", 9.99, 10));
        albums.add(new Album(2L, "Pop Album", "Pop Artist", 2001, Genre.POP, "Pop Label", 8.99, 20));
        albums.add(new Album(3L, "Jazz Album", "Jazz Artist", 2002, Genre.JAZZ, "Jazz Label", 7.99, 30));
        albums.add(new Album(4L, "Reggaeton Album", "Reggaeton Artist", 2003, Genre.REGGAETON, "Reggaeton Label", 6.99, 40));
        albums.add(new Album(5L, "Rock Album 2", "Rock Artist 2", 2004, Genre.ROCK, "Rock Label", 5.99, 50));

        when(albumServiceImplementation.getAllAlbumsInStock()).thenReturn(albums);

        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/album/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Rock Album"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].artist").value("Rock Artist"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].stockQuantity").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Pop Album"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].artist").value("Pop Artist"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].stockQuantity").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("Jazz Album"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].artist").value("Jazz Artist"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].stockQuantity").value(30))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].name").value("Reggaeton Album"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].artist").value("Reggaeton Artist"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].stockQuantity").value(40))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].name").value("Rock Album 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].artist").value("Rock Artist 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].stockQuantity").value(50));

    }
}