package schmalbach.recordshopapi.service;

import schmalbach.recordshopapi.model.Album;

import java.util.List;

public interface AlbumService {

    List<Album> getAllAlbumsInStock();
    Album getAlbumByID(int ID);
    List<Album> getAllAlbumsByArtist(String artist);
    List<Album> getAllAlbumsByYear(String title);
    List<Album> getAllAlbumsByGenre(String genre);
    Album getAlbumInfo (Album album);
    Album addAlbum (Album album);
    Album updateAlbum (Album album);
    void deleteAlbum (Album album);
}
