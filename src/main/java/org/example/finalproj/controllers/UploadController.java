package org.example.finalproj.controllers;

import org.example.finalproj.models.Photo;
import org.example.finalproj.models.Product;
import org.example.finalproj.repositories.PhotosRepository;
import org.example.finalproj.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/employee/images")
@PreAuthorize("hasAnyAuthority('Employee')")
public class UploadController {

    private final ProductsRepository productsRepository;
    private final PhotosRepository photosRepository;
    public static String UPLOAD_DIRECTORY = "src/main/resources/static";

    @Autowired
    public UploadController(ProductsRepository productsRepository, PhotosRepository photosRepository) {
        this.productsRepository = productsRepository;
        this.photosRepository = photosRepository;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("products",productsRepository.findAll());
        return "employee/product/upload";
    }

    @PostMapping()
    public String uploadImage(Model model, @RequestParam("image") MultipartFile file,
                              @RequestParam("prodID") String id
    ) throws IOException {
        StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        Photo photo = new Photo();
        photo.setLink(fileNameAndPath.toString());
        var t = photosRepository.save(photo);
        Product product = productsRepository.findById(id).orElseThrow();
        product.setExist(true);
        product.setPhotos(t);
        productsRepository.save(product);
        return  "redirect:/employee/products";
    }
}
