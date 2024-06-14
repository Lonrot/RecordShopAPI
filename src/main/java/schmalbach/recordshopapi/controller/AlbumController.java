package schmalbach.recordshopapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import schmalbach.recordshopapi.model.Album;
import schmalbach.recordshopapi.service.AlbumService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/album")
public class AlbumController {

    @Autowired
    AlbumService albumService;

    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums() {
        List<Album> allAlbumsList = albumService.getAllAlbumsInStock();
        return new ResponseEntity<>(allAlbumsList, HttpStatus.OK);
    }

    @GetMapping("/{albumID}")
    public ResponseEntity<Album> getAlbumById(@PathVariable long albumID) {
        Album albumResponse = albumService.getAlbumByID(albumID);

        if (albumResponse == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with ID " + albumID +" not found");
        }
        return new ResponseEntity<>(albumResponse, HttpStatus.OK);
    }


}
