package schmalbach.recordshopapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import schmalbach.recordshopapi.model.Album;
import schmalbach.recordshopapi.repository.AlbumRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Album> getAllAlbumsByArtist(String artistInput) {
        List<Album> albumsByArtist = getAllAlbumsInStock();
        return albumsByArtist.stream().filter(a -> a.getArtist().equalsIgnoreCase(artistInput)).toList();
    }

    @Override
    public List<Album> getAllAlbumsByYear(int year) {
        List<Album> albumsByYear = getAllAlbumsInStock();
        return albumsByYear.stream()
                .filter(a -> a.getReleaseYear() == year)
                .collect(Collectors.toList());

    }

    @Override
    public List<Album> getAllAlbumsByGenre(String genreInput) {
        List<Album> albumsByYear = getAllAlbumsInStock();
        return albumsByYear.stream()
                .filter(a -> a.getGenre().equalsIgnoreCase(genreInput))
                .collect(Collectors.toList());
    }

    @Override
    public Album getAlbumInfoByName(String albumName) {
        return albumRepository.findByNameIgnoreCase(albumName).orElse(null);

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
