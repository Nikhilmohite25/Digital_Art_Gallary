package com.art.galary.services;

import java.util.List;

import com.art.galary.models.Artwork;
import com.art.galary.models.User;

public interface ArtworkService {
    List<Artwork> getArtworks();
    void save(Artwork artwork, User user);
    Artwork findArtworkById(int id);
    List<Artwork> findArtworkByOwner(int id);
    
    void updateArtworkLikes(int id, int likes);
    String getArtOwnerName(Artwork artwork);
}
