package com.example.console;

public class ConsoleFrame {
    public char h, v, topLeft, topRight, bottomLeft, bottomRight;
    
    public ConsoleFrame(char h, char v, char topLeft, char topRight, 
                        char bottomLeft, char bottomRight) {
        this.h = h;
        this.v = v;
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }
    

    private void drawNoCornerLine(int lineLength, int cellWidth, int separatorWidth) {
        int totalWidth = lineLength * cellWidth + (lineLength - 1) * separatorWidth;
        for(int i = 0; i < totalWidth; i++)
            System.out.print(h);;
    }


    public void drawTopLine(int lineLength, int cellWidth, int separatorWidth){
        System.out.print(topLeft);
        drawNoCornerLine(lineLength, cellWidth, separatorWidth);
        System.out.print(topLeft);
        System.out.print("\n");
    } 

    public void drawBottomLine(int lineLength, int cellWidth, int separatorWidth){
        System.out.print(bottomLeft);
        drawNoCornerLine(lineLength, cellWidth, separatorWidth);
        System.out.print(bottomRight);
        System.out.print("\n");
    } 
}
