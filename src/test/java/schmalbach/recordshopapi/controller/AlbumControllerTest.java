package schmalbach.recordshopapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import schmalbach.recordshopapi.model.Album;
import schmalbach.recordshopapi.model.Genre;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import schmalbach.recordshopapi.service.AlbumServiceImplementation;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class AlbumControllerTest {

    @Mock
    private AlbumServiceImplementation albumServiceImplementation;

    @InjectMocks
    private AlbumController albumController;

    @Autowired
    private MockMvc mockMvcController;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvcController = MockMvcBuilders.standaloneSetup(albumController).build();
        mapper = new ObjectMapper().registerModule(new JavaTimeModule());

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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name")
                        .value("Rock Album"))
                .andExpect(jsonPath("$[0].artist").value("Rock Artist"))
                .andExpect(jsonPath("$[0].stockQuantity").value(10))
                .andExpect(jsonPath("$[1].name").value("Pop Album"))
                .andExpect(jsonPath("$[1].artist").value("Pop Artist"))
                .andExpect(jsonPath("$[1].stockQuantity").value(20))
                .andExpect(jsonPath("$[2].name").value("Jazz Album"))
                .andExpect(jsonPath("$[2].artist").value("Jazz Artist"))
                .andExpect(jsonPath("$[2].stockQuantity").value(30))
                .andExpect(jsonPath("$[3].name").value("Reggaeton Album"))
                .andExpect(jsonPath("$[3].artist").value("Reggaeton Artist"))
                .andExpect(jsonPath("$[3].stockQuantity").value(40))
                .andExpect(jsonPath("$[4].name").value("Rock Album 2"))
                .andExpect(jsonPath("$[4].artist").value("Rock Artist 2"))
                .andExpect(jsonPath("$[4].stockQuantity").value(50));

    }

    @Test
    @DisplayName("Find album in the DB")
    void getAlbumByID() throws Exception {
        List<Album> albums = new ArrayList<>();

        albums.add(new Album(1L, "Rock Album", "Rock Artist", 2000, Genre.ROCK, "Rock Label", 9.99, 10));
        albums.add(new Album(2L, "Pop Album", "Pop Artist", 2001, Genre.POP, "Pop Label", 8.99, 20));

        when(albumServiceImplementation.getAllAlbumsInStock()).thenReturn(albums);
        when(albumServiceImplementation.getAlbumByID(1L)).thenReturn(albums.getFirst());

        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/album/1")).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Rock Album")).andExpect(jsonPath("$.artist").value("Rock Artist")).andExpect(jsonPath("$.stockQuantity").value(10));
    }

    @Test
    @DisplayName("Album not in DB, Error")
    void getAlbumByIDNotFound() throws Exception {
        when(albumServiceImplementation.getAllAlbumsInStock()).thenReturn(null);
        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/album/1")).andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Add Album Successfully")
    void addAlbum() throws Exception {

        // JSON representation of the album to post
        String albumJson = "{"
                + "\"id\": 1,"
                + "\"name\": \"Rock Album\","
                + "\"artist\": \"Rock Artist\","
                + "\"releaseYear\": 2000,"
                + "\"genre\": \"ROCK\","
                + "\"label\": \"Rock Label\","
                + "\"price\": 9.99,"
                + "\"stockQuantity\": 10"
                + "}";


        when(albumServiceImplementation.addAlbum(Mockito.any(Album.class))).thenAnswer(invocation -> {
            Album album = invocation.getArgument(0);
            album.setId(1L);
            return album;
        });


        mockMvcController.perform(MockMvcRequestBuilders.post("/api/v1/album/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(albumJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated()) // Expect HTTP 201 Created
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Rock Album"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.artist").value("Rock Artist"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.releaseYear").value(2000));

        verify(albumServiceImplementation, times(1)).addAlbum(Mockito.any(Album.class));
    }



    @Test
    void updateAlbumByID() throws Exception {
        Album album = new Album(1L, "Rock Album", "Rock Artist", 2000, Genre.ROCK, "Rock Label", 9.99, 10);
        Album albumUpdated = new Album(1L, "Rock Album", "Rock Artist", 2000, Genre.ROCK, "Rock Label", 9.99, 10);
        long albumID = 1L;
        when(albumServiceImplementation.updateAlbum(albumID, albumUpdated)).thenReturn(albumUpdated);
        System.out.println(mapper.writeValueAsString(albumUpdated));

           mockMvcController.perform(MockMvcRequestBuilders.put("/api/v1/album/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(albumUpdated))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Rock Album"));

    }
}