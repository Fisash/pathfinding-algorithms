class ConsoleRenderFrameOptions
{
    char h_view;
    char v_view;
    char corner;
    int thickness;
    
    ConsoleRenderFrameOptions(char h_view, char v_view, char corner, int thickness)
    {
        this.h_view = h_view;
        this.v_view = v_view;
        this.corner = corner;
        this.thickness = thickness;
    }
}
