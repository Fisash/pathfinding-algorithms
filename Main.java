import java.util.List;
import java.util.ArrayList;

class Main
{
    public static void main(String[] args)
    {
        Map map = new Map("./test_map2.tex");

        ConsoleRenderFrameOptions baseFrame = 
        new ConsoleRenderFrameOptions('-', '|', 'X', 1);

        PFConsoleRenderer renderer = new PFConsoleRenderer(100, baseFrame);

        List<Point> path = BFS.search(map, new Point(0, 12), new Point(7, 10), renderer);

        if (path == null)
        {
            System.err.println("Path is not found!");
            return;
        }

        System.out.println("Path found by BFS:");
        renderer.draw(map, path);
    }
}
