package com.roy.selfiegeek.datamodel;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/**
 * made use of builder pattern to construct an immutable data model
 * Though it best to use for complex models which is not the case here
 * but for future purposes and showcasing my abilities
 *
 * @author prabhat.roy
 */

public class ImageItem {

    @NonNull
    private Bitmap image;
    @NonNull
    private String title;

    public ImageItem(Bitmap image, String title) {
        super();
        this.image = image;
        this.title = title;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public ImageItem(Builder builder) {
        image = builder.mImage;
        title = builder.mTitle;
    }

    /**
     * This is our builder class for contacts data model
     */
    public static class Builder {

        private Bitmap mImage;
        private String mTitle;

        public Builder(Bitmap image, String title) {
            this.mImage = image;
            this.mTitle = title;
        }

        public ImageItem.Builder image(Bitmap image) {
            this.mImage = image;
            return this;
        }

        public ImageItem.Builder title(String title) {
            this.mTitle = title;
            return this;
        }

        public ImageItem build() {
            return new ImageItem(this);
        }

    }


}
