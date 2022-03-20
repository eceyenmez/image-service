package com.example.imageservice.controllers;

import com.example.imageservice.annotation.ImageTypeValidation;
import com.example.imageservice.model.PathResponse;
import com.example.imageservice.model.PredefinedImageType;
import com.example.imageservice.services.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@Validated
@RestController
@RequestMapping("/image")
public class ImageController {

    final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(value = {"/show/{predefinedTypeName}/", "/show/{predefinedTypeName}/{seoName}/"})
    public ResponseEntity<PathResponse> getImage(@PathVariable(name = "predefinedTypeName") @ImageTypeValidation String typeName,
                                                 @PathVariable(required = false, name = "seoName") String seoName,
                                                 @RequestParam(required = true) String reference) throws IOException {

        String path = imageService.getImage(PredefinedImageType.valueOf(typeName.toUpperCase()), reference);

        return new ResponseEntity<PathResponse>(new PathResponse(path),HttpStatus.OK);

    }

    @GetMapping("/flush/{predefinedTypeName}/")
    public ResponseEntity flushImage(@PathVariable(name = "predefinedTypeName") @ImageTypeValidation String typeName,
                                     @RequestParam(required = true) String reference) throws IOException {

        imageService.deleteImage(PredefinedImageType.valueOf(typeName.toUpperCase()), reference);

        return ResponseEntity.ok().build();

    }
}
