package com.samramez.googlemap;

/**
 * Class to model data for Drawer Layout
 */
public class DrawerModel {
    private int icon;
    private String title;

    public DrawerModel(int icon, String title){
        this.icon = icon;
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
