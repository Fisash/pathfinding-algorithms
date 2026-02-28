package com.example;
class ConsoleRenderFrameOptions
{
    char h_view;
    char v_view;
    char[] corners;
    
    ConsoleRenderFrameOptions(char h_view, char v_view, char corners[])
    {
        this.h_view = h_view;
        this.v_view = v_view;
        this.corners = corners;
    }
    

    private void addNoCornerLine(StringBuilder result, int lineLength, int cellWidth)
    {
        for(int i = 0; i < lineLength*cellWidth+lineLength; i++)
            result.append(h_view);
    }

    public void addTopLine(StringBuilder result, int lineLength, int cellWidth)
    {
        result.append(corners[0]);
        addNoCornerLine(result, lineLength, cellWidth);
        result.append(corners[1]);
        result.append("\n");
    } 

    public void addBottomLine(StringBuilder result, int lineLength, int cellWidth)
    {
        result.append(corners[2]);
        addNoCornerLine(result, lineLength, cellWidth);
        result.append(corners[3]);
        result.append("\n");
    } 
}
