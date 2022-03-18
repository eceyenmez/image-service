package com.example.imageservice.controllers;


import org.apache.commons.io.IOUtils;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
public class ImageServiceErrorController implements ErrorController {

    @RequestMapping(value="/error",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] handleError() throws IOException {
        //do something like logging
        InputStream in = getClass()
                .getResourceAsStream("src/main/resources/static/catPic.jpeg");
        return IOUtils.toByteArray(in);
    }
}


