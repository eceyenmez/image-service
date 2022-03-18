package com.example.imageservice.model;

import lombok.AllArgsConstructor;

import java.io.File;
import java.io.InputStream;

@AllArgsConstructor
public enum PredefinedImageType {

    THUMBNAIL(new ImageDetails(3, 5, 9, ScaleType.CROP, "SADF", ImageType.JPG)),
    PROFILE(new ImageDetails(3, 5, 9, ScaleType.FILL, "FFFF", ImageType.PNG)),
    CUTE_CAT_PICS(new ImageDetails(3, 5, 9, ScaleType.SKEW, "KAWAI", ImageType.JPG)),
    ORIGINAL(new ImageDetails(3, 5, 9, ScaleType.SKEW, "ORG", ImageType.JPG));

    private final ImageDetails imageDetails;

    public ImageDetails getImageDetails() {
        return imageDetails;
    }

    public static boolean contains(String s) {

        for (PredefinedImageType imageType : values()) {
            if (imageType.name().equals(s.toUpperCase()))
                return true;
        }
        return false;
    }

    public static File resize(InputStream originalImage) {
        System.out.println("BIP BOP RESIZING");
        return null;
    }
}
