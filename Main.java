import java.util.List;
import java.util.ArrayList;

class Main
{
    public static void main(String[] args)
    {
        Map map = new Map("./test_map2.tex");
        List<Point> path = BFS.search(map, new Point(0, 12), new Point(7, 10));

        if (path == null)
        {
            System.err.println("Path is not found!");
            return;
        }

        for (int i = 0; i < path.size(); i++)
            System.out.print(path.get(i).toString());
        System.out.print("\n");
        PathConsoleVisualizer.clear();
        System.out.println("Path finded by BFS:");

        String result = PathConsoleVisualizer.printPath(map, path);
        System.out.print(result);
    }
}
