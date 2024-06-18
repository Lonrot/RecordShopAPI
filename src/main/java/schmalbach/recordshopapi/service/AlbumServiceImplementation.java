package schmalbach.recordshopapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import schmalbach.recordshopapi.exception.AlbumAlreadyExist;
import schmalbach.recordshopapi.exception.AlbumNotFoundException;
import schmalbach.recordshopapi.model.Album;
import schmalbach.recordshopapi.model.Genre;
import schmalbach.recordshopapi.repository.AlbumRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlbumServiceImplementation implements AlbumService {

    @Autowired
    AlbumRepository albumRepository;
    @Override
    public boolean albumExists(long id) {
        return albumRepository.existsById(id);
    }

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
    public Album getAlbumByID(long ID) {
        return albumRepository.findById(ID).orElse(null);
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
    public List<Album> getAllAlbumsByGenre(Genre genreInput) {
       List<Album> albumInputGenreList= albumRepository.findByGenre(genreInput);

       if(!albumInputGenreList.isEmpty()) {
           return albumInputGenreList;
       }
       throw new RuntimeException("NO LIST");
    }


    @Override
    public Album getAlbumInfoByName(String albumName) {
        return albumRepository.findByNameIgnoreCase(albumName).orElse(null);

    }

    @Override
    public Album addAlbum(Album albumToSave) throws AlbumAlreadyExist {
        if(!albumRepository.findByNameIgnoreCase(albumToSave.getName()).isPresent()) {
            return albumRepository.save(albumToSave);
        } else {
            throw new AlbumAlreadyExist();
        }

    }

    @Override
    public Album updateAlbum(long ID ,Album albumInput) {
        Optional<Album> retrievedAlbum = albumRepository.findById(ID);

        if(retrievedAlbum.isPresent()) {
            Album updatedAlbum = retrievedAlbum.get();
            updatedAlbum.setName(albumInput.getName());
            updatedAlbum.setArtist(albumInput.getArtist());
            updatedAlbum.setReleaseYear(albumInput.getReleaseYear());
            updatedAlbum.setGenre(albumInput.getGenre());
            updatedAlbum.setLabel(albumInput.getLabel());

            return albumRepository.save(updatedAlbum);
        } else {
            throw new AlbumNotFoundException("Album with id " + ID + " not found");
        }
    }


    @Override
    public boolean deleteAlbumByID(long albumToDeleteID) {
       Album albumToDelete = albumRepository.findById(albumToDeleteID).orElse(null);
       if (albumToDelete != null) {
           albumRepository.delete(albumToDelete);
           return true;
       }
       return false;
    }

    public static Genre getGenreFromString(String genre) {
        if (genre == null) {
            throw new IllegalArgumentException("Genre string cannot be null");
        }

        return switch (genre.toUpperCase()) {
            case "HEAVY_METAL" -> Genre.HEAVY_METAL;
            case "ROCK" -> Genre.ROCK;
            case "PUNK" -> Genre.PUNK;
            case "PHONK" -> Genre.PHONK;
            case "POP" -> Genre.POP;
            case "ELECTRONIC" -> Genre.ELECTRONIC;
            case "LO_FI" -> Genre.LO_FI;
            case "REGGAETON" -> Genre.REGGAETON;
            case "HIP_HOP" -> Genre.HIP_HOP;
            case "JAZZ" -> Genre.JAZZ;
            case "BLUES" -> Genre.BLUES;
            case "COUNTRY" -> Genre.COUNTRY;
            case "FUNK" -> Genre.FUNK;
            case "CLASSICAL" -> Genre.CLASSICAL;
            case "INDIE" -> Genre.INDIE;
            case "RAP" -> Genre.RAP;
            case "R_AND_B" -> Genre.R_AND_B;
            case "SOUL" -> Genre.SOUL;
            case "EDM" -> Genre.EDM;
            case "DISCO" -> Genre.DISCO;
            case "HOUSE" -> Genre.HOUSE;
            default -> throw new IllegalArgumentException("Unknown genre: " + genre);
        };
    }
}
