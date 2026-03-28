package com.example.console;
public class ConsoleFrameOptions{
    char h_view;
    char v_view;
    char[] corners;
    
    public ConsoleFrameOptions(char h_view, char v_view, char corners[]){
        this.h_view = h_view;
        this.v_view = v_view;
        this.corners = corners;
    }
    

    private void addNoCornerLine(StringBuilder result, int lineLength, int cellWidth, int separatorWidth) {
        int totalWidth = lineLength * cellWidth + (lineLength - 1) * separatorWidth;
        for(int i = 0; i < totalWidth; i++)
            result.append(h_view);
    }


    public void addTopLine(StringBuilder result, int lineLength, int cellWidth, int separatorWidth){
        result.append(corners[0]);
        addNoCornerLine(result, lineLength, cellWidth, separatorWidth);
        result.append(corners[1]);
        result.append("\n");
    } 

    public void addBottomLine(StringBuilder result, int lineLength, int cellWidth, int separatorWidth){
        result.append(corners[2]);
        addNoCornerLine(result, lineLength, cellWidth, separatorWidth);
        result.append(corners[3]);
        result.append("\n");
    } 
}
