package com.art.galary.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.art.galary.models.Artwork;
import com.art.galary.repository.ArtworkRepository;
import com.art.galary.services.ArtworkService;


@Controller
public class HomepageController {
    ArtworkRepository artworkRepository;
    ArtworkService artworkService;
    

    @Autowired
    public HomepageController(ArtworkRepository artworkRepository, ArtworkService artworkService) {
        this.artworkRepository = artworkRepository;
        this.artworkService = artworkService;
        
    }

    @RequestMapping({ "/", "/homepage" })
    public String homepage(Model model) {
        // Fetch all artworks from the repository
        List<Artwork> artworks = artworkRepository.getArtworks();

        // Handle case when there are fewer than 6 artworks
        int size = artworks.size();
        List<Artwork> featured;
        if (size >= 6) {
            // If there are 6 or more artworks, get the last 6
            featured = artworks.subList(size - 6, size);
        } else {
            // If there are fewer than 6, show all artworks
            featured = artworks;
        }

        // Create a map to hold artwork and their respective owners
        Map<Object, String> artAndOwner = new HashMap<>();

        for (Artwork artwork : featured) {
            artAndOwner.put(artwork, artworkService.getArtOwnerName(artwork));
        }

        // Log the list of featured artworks
        System.out.println("In home controller : " + featured);

        // Add featured artworks and their owners to the model
        model.addAttribute("artworks", featured);
        model.addAttribute("artAndOwner", artAndOwner);

        
                return "homepage";
    }

    // About page
    @GetMapping("/about")
    public String about() {
        return "about";
    }
}