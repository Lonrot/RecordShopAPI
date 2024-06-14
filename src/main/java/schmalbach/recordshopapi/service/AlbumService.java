package schmalbach.recordshopapi.service;

import schmalbach.recordshopapi.model.Album;

import java.util.List;

public interface AlbumService {

    List<Album> getAllAlbumsInStock();
    Album getAlbumByID(long ID);
    List<Album> getAllAlbumsByArtist(String artist);
    List<Album> getAllAlbumsByYear(int year);
    List<Album> getAllAlbumsByGenre(String genre);
    Album getAlbumInfoByName(String albumName);
    Album addAlbum (Album album);
    Album updateAlbum (Album album);
    boolean deleteAlbumByID(long album);
}
