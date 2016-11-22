package com.naf.groupbuying.entity;

/**
 * 首页 GridView
 */
public class HomeIconInfo {
    public String iconName;
    public int iconID;

    public HomeIconInfo(String iconName, int iconID) {
        this.iconName = iconName;
        this.iconID = iconID;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }
}
