import java.util.List;
import java.util.ArrayList;

class Main
{
    public static void main(String[] args)
    {
        Map map = new Map("./test_map.tex");
        System.out.print(PathConsoleVisualizer.printMap(map));

        List<Point> path = BFS.search(map, new Point(0, 5), new Point(2, 0));
        if (path == null)
        {
            System.err.println("no fucking way!");
            return;
        }

        for (int i = 0; i < path.size(); i++)
            System.out.print(path.get(i).toString());
        System.out.print("\n");
        System.out.println("Path finded by BFS:");
        System.out.print(PathConsoleVisualizer.printPath(map, path));
    }
}
