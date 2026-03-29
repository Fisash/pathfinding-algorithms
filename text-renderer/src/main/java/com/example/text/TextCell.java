package com.example.text;

public class TextCell {
    public String text;
    public RenderColor fg;
    public RenderColor bg;

    public TextCell(String text, RenderColor fg, RenderColor bg) {
        this.text = text;
        this.fg = fg;
        this.bg = bg;
    }

    public TextCell() {
        this(" ", RenderColor.WHITE, RenderColor.BLACK);
    }
}
