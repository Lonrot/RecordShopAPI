package schmalbach.recordshopapi.exception;

public class AlbumAlreadyExist extends Exception {
    public AlbumAlreadyExist() {
        super("Album already exist in the Database");
    }
}
