package com.philcst.www.engineeringreviewer.interfaces;

/**
 * Created by HP User on 1/1/2018.
 * An interface exposes the Icon, Name, Description to enable
 * to display to the Recycler View
 */

public interface ListItemShowable {
    int getIcon();
    String getName();
    String getDescription();
}