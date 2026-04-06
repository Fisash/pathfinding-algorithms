package com.example.text;

public class TextRenderConfig {
    
    public String wallSymbol;
    public RenderColor wallFg;
    public RenderColor wallBg;

    public String pathSymbol;
    public RenderColor pathFg;
    public RenderColor pathBg;

    public String startSymbol;
    public RenderColor startFg;

    public String finishSymbol;
    public RenderColor finishFg;

    public String intermediateFinishSymbol;
    public RenderColor intermediateFinishFg;

    public String unvisitedSymbol;
    public RenderColor unvisitedFg;
    public RenderColor unvisitedBg;

    public RenderColor visitedFg;
    public RenderColor visitedBg;

    public TextRenderConfig() {
        wallSymbol = " ";
        wallFg = RenderColor.BLACK;
        wallBg = RenderColor.BLUE;

        pathSymbol = "●";
        pathFg = RenderColor.RED;
        pathBg = RenderColor.BLACK;

        startSymbol = "S";
        startFg = RenderColor.RED;

        finishSymbol = "F";
        finishFg = RenderColor.RED;

        intermediateFinishSymbol = "F";
        intermediateFinishFg = RenderColor.YELLOW;

        unvisitedSymbol = " ";
        unvisitedFg = RenderColor.DEFAULT;
        unvisitedBg = RenderColor.BLACK;

        visitedFg = RenderColor.WHITE;
        visitedBg = RenderColor.BLACK;
    }
}
