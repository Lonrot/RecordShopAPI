package schmalbach.recordshopapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import schmalbach.recordshopapi.exception.AlbumAlreadyExist;
import schmalbach.recordshopapi.model.Album;
import schmalbach.recordshopapi.service.AlbumServiceImplementation;

import java.util.List;

@RestController
@RequestMapping("/api/v1/album")
public class AlbumController {

    @Autowired
    AlbumServiceImplementation albumService;

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

    @PostMapping("/add")
    public ResponseEntity<Album> addAlbumController(@RequestBody Album albumInput) {
        try {
            Album toReturnAlbum = albumService.addAlbum(albumInput);
            return new ResponseEntity<>(toReturnAlbum, HttpStatus.CREATED);
        }catch (AlbumAlreadyExist e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Album already exist");
        }

    }

    @PutMapping("/update/{albumID}")
    public ResponseEntity<Album> updateAlbum(@PathVariable long albumID, @RequestBody Album albumInput) {

        if (albumService.albumExists(albumID)) {
            return new ResponseEntity<>(albumService.updateAlbum(albumID, albumInput), HttpStatus.OK);
        } else {
            try {
                return new ResponseEntity<>(albumService.addAlbum(albumInput), HttpStatus.CREATED);
            } catch (AlbumAlreadyExist e) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Album already exist");
            }
        }
    }

    @DeleteMapping("/delete/{albumID}")
    public ResponseEntity<String> deleteAlbum(@PathVariable long albumID) {
        if(albumService.deleteAlbumByID(albumID)) {
            return ResponseEntity.ok("Deleted album with ID " + albumID + " successfully");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Album with ID " + albumID + " not found");
        }
    }

}
