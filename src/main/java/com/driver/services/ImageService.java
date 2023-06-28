package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    BlogRepository blogRepository2;
    @Autowired
    ImageRepository imageRepository2;

    public Image addImage(Integer blogId, String description, String dimensions){
        //add an image to the blog
        Blog blog = blogRepository2.findById(blogId).get();
        Image image = new Image();
        image.setDescription(description);
        image.setDimensions(dimensions);
        image.setBlog(blog);
        blog.getImageList().add(image);

        blogRepository2.save(blog);
        return image;
    }

    public void deleteImage(Integer id){
        Image image = imageRepository2.findById(id).get();
        Blog blog = image.getBlog();
        blog.getImageList().remove(image);
        blogRepository2.save(blog);
    }

    public int countImagesInScreen(Integer id, String screenDimensions) {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        Image image = imageRepository2.findById(id).get();
        String description = image.getDescription();
        int imageSize = 0;
        int windowSize = 0;

        String[] imageDesc = description.split("[X]", 0);
        String[] screenDesc = screenDimensions.split("[X]", 0);

        int imageL = StringToInt(imageDesc[0]);
        int imageB = StringToInt(imageDesc[1]);
        int imageArea = imageL*imageB;

        int screenL = StringToInt(screenDesc[0]);
        int screenB = StringToInt(screenDesc[1]);
        int screenArea = screenB * screenL;

        int cnt = screenArea/imageArea;
        return cnt;
    }

    public int StringToInt(String s){
        int res = 0;
        for(int i=0; i<s.length(); i++){
            int a = s.charAt(i) - '0';
            res = res*10 + a;
        }
        return res;
    }
}
