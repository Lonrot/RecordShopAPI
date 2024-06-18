package schmalbach.recordshopapi.service;

import schmalbach.recordshopapi.exception.AlbumAlreadyExist;
import schmalbach.recordshopapi.model.Album;
import schmalbach.recordshopapi.model.Genre;

import java.util.List;

public interface AlbumService {

    boolean albumExists(long id);
    List<Album> getAllAlbumsInStock();
    Album getAlbumByID(long ID);
    List<Album> getAllAlbumsByArtist(String artist);
    List<Album> getAllAlbumsByYear(int year);
    List<Album> getAllAlbumsByGenre(Genre genre);
    Album getAlbumInfoByName(String albumName);
    Album addAlbum (Album album) throws AlbumAlreadyExist;
    Album updateAlbum (long ID,Album album);
    boolean deleteAlbumByID(long album);
}
