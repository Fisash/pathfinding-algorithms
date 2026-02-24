class PFConsoleRenderCell
{
    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final String RESET = "\u001B[0m"; 

    private static String wrap(String input, String COLOR)
    {
        return COLOR+input+RESET;
    }

    private static final String WALL = "#";
    private static final String PATHPOINT = "o";
    private static final String UNVISITED = "_";
    private static final String WIDTH_FILLER = " ";

    int value;
    boolean isWall;
    boolean isPath;

    PFConsoleRenderCell(int value, boolean isWall, boolean isPath)
    {
        this.value = value;
        this.isWall = isWall;
        this.isPath = isPath;
    }

    PFConsoleRenderCell (int mapCell)
    {
        value = -1;
        isWall = mapCell == Map.WALL;
        isPath = false;
    }

    private static String fillToWidth(String content, int width)
    {
        int length = content.length();
        if (length >= width) 
            return content;

        int zerosToAdd = width - length;
        StringBuilder result = new StringBuilder(width);
        for (int i = 0; i < zerosToAdd; i++)
            result.append(WIDTH_FILLER);
        result.append(content);
        return result.toString();
    }

    public String stringView(int width)
    {
        if(isWall)
            return wrap(fillToWidth(WALL, width), BLUE);
        if(isPath)
            return wrap(fillToWidth(PATHPOINT, width), RED);
        if(value == -1)
            return fillToWidth(UNVISITED, width);
        return fillToWidth(String.valueOf(value), width); 
    }
}
