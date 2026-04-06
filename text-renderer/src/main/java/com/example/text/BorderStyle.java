package com.example.text;

public class BorderStyle {

    public enum Template {
        SINGLE,
        DOUBLE
    }

    public char horizontal;
    public char vertical;
    public char topLeft;
    public char topRight;
    public char bottomLeft;
    public char bottomRight;

    public BorderStyle(char horizontal, char vertical, char topLeft,
                       char topRight, char bottomLeft, char bottomRight) {
        this.horizontal = horizontal;
        this.vertical = vertical;
        this.topRight = topRight;
        this.topLeft = topLeft;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }

    public static BorderStyle SINGLE() {
        return new BorderStyle('─', '│', '┌', '┐', '└', '┘');
    }

    public static BorderStyle SINGLE_THICK() {
        return new BorderStyle('━', '┃', '┏', '┓', '┗', '┛');
    }

    public static BorderStyle DOUBLE() {
        return new BorderStyle('═', '║', '╔', '╗', '╚', '╝');
    }

    public BorderStyle() {
        this('═', '║', '╔', '╗', '╚', '╝');
    }
}
