package com.example.tui;

public class LayoutHelper {
    public static void centerHorizontally(Component comp, Container parent) {
        int newX = (parent.getWidth() - comp.getWidth()) / 2;
        comp.setPosition(newX, comp.getY());
    }
}
