package com.art.galary.repository;

import java.util.List;

import com.art.galary.models.Artwork;

public interface ArtworkRepository {

    List<Artwork> getArtworks();

    List<Artwork> findAllArtworks();

    void save(Artwork artwork);

    Artwork findArtworkById(int id);

    List<Artwork> findArtworkByOwner(int id);

    void updateArtwork(Artwork artwork);

    void updateArtworkLikes(int id, int likes);

    void acceptArt(int id);

    void declineArt(int id);

    // Add the new method to fetch artworks by category
    List<Artwork> getArtworksByCategory(String category);
    
    void deleteById(int id);
}
