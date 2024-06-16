package schmalbach.recordshopapi.model;

import jakarta.persistence.*;


@Entity
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false,nullable = false)
    private  long id;
    @Column//(unique = true,nullable = false)
    private String name;
    @Column
    private String artist;
    @Column
    private int releaseYear;
    @Column
    @Enumerated(EnumType.STRING) //@Enumerated
    private Genre genre;
    @Column
    private String label;
    @Column
    private double price;
    @Column
    private int stockQuantity;

    // Constructors
    public Album() {}

    public Album(Long id, String name, String artist, int releaseYear, Genre genre, String label, double price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.label = label;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", releaseYear=" + releaseYear +
                ", genre='" + genre + '\'' +
                ", label='" + label + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}
