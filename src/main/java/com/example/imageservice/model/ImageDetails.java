package com.example.imageservice.model;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ImageDetails {
    private int height;
    private int weight;
    private int quality;
    private ScaleType scaleType;
    private String hexColor;
    private ImageType imageType;
}
