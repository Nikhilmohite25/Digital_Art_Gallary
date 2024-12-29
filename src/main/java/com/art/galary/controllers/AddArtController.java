package com.art.galary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.art.galary.models.Artwork;
import com.art.galary.models.User;
import com.art.galary.services.ArtworkService;
import com.art.galary.services.SecurityService;
import com.art.galary.services.UserService;

import java.io.IOException;

@Controller
public class AddArtController {

    private final ArtworkService artworkService;
    private final UserService userService;
    private final SecurityService securityService;

    @Autowired
    public AddArtController(ArtworkService artworkService, UserService userService, SecurityService securityService) {
        this.artworkService = artworkService;
        this.userService = userService;
        this.securityService = securityService;
    }

    @GetMapping("/addArt")
    public String addArt(Model model) {
        model.addAttribute("artwork", new Artwork());
        return "addArt";
    }

    @PostMapping("/addArt")
    public String addArt(@ModelAttribute("artwork") Artwork artwork, Model model, @RequestParam("image") MultipartFile multipartFile) {
        try {
            System.out.println("Starting addArt process");
            int currentUserId = userService.findByUsername(securityService.findLoggedInUsername()).getId();
            System.out.println("Current User ID: " + currentUserId);

            User user = userService.findByUserId(currentUserId);
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            artwork.setImgUrl(fileName);

            artworkService.save(artwork, user);

            String uploadDir = "src/main/resources/static/img/artwork-photos/" + artwork.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

            System.out.println("Redirecting to homepage");
            return "redirect:/homepage";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while adding the artwork.");
            return "addArt";
        }
    }
    

    private static class FileUploadUtil {

        public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
            java.nio.file.Path uploadPath = java.nio.file.Paths.get(uploadDir);

            if (!java.nio.file.Files.exists(uploadPath)) {
                java.nio.file.Files.createDirectories(uploadPath);
            }

            try (java.io.InputStream inputStream = multipartFile.getInputStream()) {
                java.nio.file.Path filePath = uploadPath.resolve(fileName);
                java.nio.file.Files.copy(inputStream, filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioe) {
                throw new IOException("Could not save image file: " + fileName, ioe);
            }
        }
    }
    
    
}
