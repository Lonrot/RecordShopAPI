package schmalbach.recordshopapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import schmalbach.recordshopapi.model.Album;
import schmalbach.recordshopapi.repository.AlbumRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumServiceImplementation implements AlbumService {

    @Autowired
    AlbumRepository albumRepository;

    @Override
    public List<Album> getAllAlbumsInStock() {
        List<Album> albumsInStock = new ArrayList<>();
        albumRepository.findAll().forEach(album -> {
            if (album.getStockQuantity() > 0) {
                albumsInStock.add(album);
            }
        });
        return albumsInStock;
    }

    @Override
    public Album getAlbumByID(int ID) {
        Long IDToLong = (long) ID;
        return albumRepository.findById(IDToLong).orElse(null);
    }

    @Override
    public List<Album> getAllAlbumsByArtist(String artist) {
        return List.of();
    }

    @Override
    public List<Album> getAllAlbumsByYear(String title) {
        return List.of();
    }

    @Override
    public List<Album> getAllAlbumsByGenre(String genre) {
        return List.of();
    }

    @Override
    public Album getAlbumInfo(Album album) {
        return null;
    }

    @Override
    public Album addAlbum(Album album) {
        return null;
    }

    @Override
    public Album updateAlbum(Album album) {
        return null;
    }

    @Override
    public void deleteAlbum(Album album) {

    }
}
