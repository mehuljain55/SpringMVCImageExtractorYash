package com.ImageExtractor.Controller;

import com.ImageExtractor.Entity.UserImage;
import com.ImageExtractor.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/upload")
    public String uploadPage() {
        System.out.println("Upload page accessed");
        return "uploadImagePage";
    }


    @PostMapping("/uploadImage")
    public String uploadImage(@ModelAttribute("file") MultipartFile file) {
        String status = userService.uploadImage(file);
        System.out.println("Upload Status: " + status);
        return "redirect:/uploadImagePage";
    }

    @GetMapping("/viewImages")
    public String viewImages(Model model) {

        List<UserImage> images = userService.getAllImages();
        model.addAttribute("images", images);
        return "imageGallery";
    }
}
