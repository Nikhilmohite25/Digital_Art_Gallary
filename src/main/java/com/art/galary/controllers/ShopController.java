package com.art.galary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.art.galary.models.Artwork;
import com.art.galary.models.Orders;
import com.art.galary.repository.ArtworkRepository;
import com.art.galary.services.ArtworkService;
import com.art.galary.services.OrdersService;
import com.art.galary.services.SecurityService;
import com.art.galary.services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ShopController {
    UserService userService;
    ArtworkRepository artworkRepository;
    ArtworkService artworkService;
    SecurityService securityService;
    OrdersService ordersService;

    @Autowired
    public ShopController(UserService userService, ArtworkRepository artworkRepository, ArtworkService artworkService, SecurityService securityService, OrdersService ordersService) {
        this.userService = userService;
        this.artworkRepository = artworkRepository;
        this.artworkService = artworkService;
        this.securityService = securityService;
        this.ordersService = ordersService;
    }

    @GetMapping("/shop")
    public String shop(Model model) {
        List<Artwork> artworks = artworkRepository.getArtworks();
        Map<Object, String> artAndOwner = new HashMap<>();
        for (Artwork artwork : artworks) {
            artAndOwner.put(artwork, artworkService.getArtOwnerName(artwork));
        }

        model.addAttribute("artworks", artworks);
        model.addAttribute("artAndOwner", artAndOwner);
        return "shop";
    }

    @GetMapping("/hatching")
    public String hatching(Model model) {
        // Filter artworks based on the "hatching" category
        List<Artwork> artworks = artworkRepository.getArtworksByCategory("hatching");

        Map<Object, String> artAndOwner = new HashMap<>();
        for (Artwork artwork : artworks) {
            artAndOwner.put(artwork, artworkService.getArtOwnerName(artwork));
        }

        model.addAttribute("artworks", artworks);
        model.addAttribute("artAndOwner", artAndOwner);
        return "hatching";
    }

    @GetMapping("/watercolorPainting")
    public String watercolorPainting(Model model) {
        // Filter artworks based on the "watercolor painting" category
        List<Artwork> artworks = artworkRepository.getArtworksByCategory("watercolorPainting");

        Map<Object, String> artAndOwner = new HashMap<>();
        for (Artwork artwork : artworks) {
            artAndOwner.put(artwork, artworkService.getArtOwnerName(artwork));
        }

        model.addAttribute("artworks", artworks);
        model.addAttribute("artAndOwner", artAndOwner);
        return "watercolorPainting";
    }

    @GetMapping("/oilPainting")
    public String oilPainting(Model model) {
        // Filter artworks based on the "oil painting" category
        List<Artwork> artworks = artworkRepository.getArtworksByCategory("oilPainting");

        Map<Object, String> artAndOwner = new HashMap<>();
        for (Artwork artwork : artworks) {
            artAndOwner.put(artwork, artworkService.getArtOwnerName(artwork));
        }

        model.addAttribute("artworks", artworks);
        model.addAttribute("artAndOwner", artAndOwner);
        return "oilPainting";
    }

    @GetMapping("/receivedArtworks")
    public String receivedArtworks(Model model) {
        List<Artwork> artworks = artworkRepository.getArtworks();
        Map<Object, String> artAndOwner = new HashMap<>();
        for (Artwork artwork : artworks) {
            artAndOwner.put(artwork, artworkService.getArtOwnerName(artwork));
        }

        model.addAttribute("artworks", artworks);
        model.addAttribute("artAndOwner", artAndOwner);
        return "receivedArtworks";
    }

    @RequestMapping(value = "/acceptArt", method = { RequestMethod.GET, RequestMethod.POST })
    public String acceptArt(Model model, @RequestParam("artwork_id") int artwork_id) {
        artworkRepository.acceptArt(artwork_id);
        return "redirect:/receivedArtworks";
    }

    @RequestMapping(value = "/declineArt", method = { RequestMethod.GET, RequestMethod.POST })
    public String declineArt(Model model, @RequestParam("artwork_id") int artwork_id) {
        artworkRepository.declineArt(artwork_id);
        return "redirect:/receivedArtworks";
    }

    @RequestMapping(value = "/shop/{artId}", method = { RequestMethod.GET, RequestMethod.POST })
    public String ArtworkById(Model model, @PathVariable("artId") int artworkId) {
        model.addAttribute("loggedIn", securityService.isLoggedIn());
        Map<String, Object> map = new HashMap<>();
        Orders orders = new Orders();
        model.addAttribute(orders);
        Artwork artwork = artworkService.findArtworkById(artworkId);
        map.put("artwork", artwork);
        model.addAttribute("owner", artworkService.getArtOwnerName(artwork));
        model.addAllAttributes(map);

        artworkService.updateArtworkLikes(artworkId, artwork.getLikes());

        if (securityService.isLoggedIn()) return "singleArt"; 
        else return "login";
        
        
    }
    
 // Delete artwork
    @RequestMapping(value = "/deleteArtwork/{id}", method = RequestMethod.POST)
    public String deleteArtwork(@PathVariable("id") int artworkId) {
        // Delete the artwork by its ID
        artworkRepository.deleteById(artworkId); 
        
        // Redirect to the shop page after deletion
        return "redirect:/shop";
    }

 // Show the update form
    @GetMapping("/updateArtwork/{id}")
    public String updateArtwork(@PathVariable("id") int id, Model model) {
        // Retrieve the artwork by ID
        Artwork artwork = artworkRepository.findArtworkById(id);
        model.addAttribute("artwork", artwork); // Add artwork to model to pre-fill the form
        return "addart"; // Return the addart.html page
    }
    
    
    @PostMapping("/saveArtwork")
    public String saveArtwork(@ModelAttribute Artwork artwork) {
        // Update the artwork in the database
        artworkRepository.updateArtwork(artwork); // Use the method to update the artwork
        return "redirect:/shop"; // Redirect to the shop page after updating
    }

  }
