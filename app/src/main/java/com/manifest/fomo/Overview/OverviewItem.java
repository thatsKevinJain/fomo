package com.manifest.fomo.Overview;

/**
 * Created by Kevin on 09/03/18.
 */

public class OverviewItem {
    private String name;
    private String title;
    private String image;
    private int color;
    private int type;

    OverviewItem(String name, String title, String image, int color, int type) {
        this.name = name;
        this.title = title;
        this.image = image;
        this.color = color;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
