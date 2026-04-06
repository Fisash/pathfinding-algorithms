package com.example.console;

import com.example.text.BorderDrawer;
import com.example.text.BorderStyle;

public class ConsoleBorderDrawer extends BorderDrawer {

    public ConsoleBorderDrawer() {}
    public ConsoleBorderDrawer(BorderStyle style) {
        super(style);
    }

    @Override
    protected void drawChar(char c) {
        System.out.print(c);
    }

    @Override
    protected void drawNewLine() {
        System.out.println();
    }
}
