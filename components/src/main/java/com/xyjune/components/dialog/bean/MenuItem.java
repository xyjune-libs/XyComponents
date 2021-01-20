package com.xyjune.components.dialog.bean;

public class MenuItem {

    private int icon;
    private String text;

    public MenuItem(int icon, String text) {
        this.icon = icon;
        this.text = text;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
