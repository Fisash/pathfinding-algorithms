package com.example.tui;

import com.example.Map;
import com.example.Point;
import com.example.text.TextRenderConfig;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.ArrayList;

public class MapView extends Component {
    private LanternaRenderer renderer;
    private Map map;

    private int width;
    private int height;

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setStartAndFinish(Point start, Point finish) {
        ArrayList<Point> list = new ArrayList<>();
        list.add(start);
        list.add(finish);
        renderer.fillPath(list);    
    }

    public void setMap(Map map) {
        this.map = map;
        renderer.fillMap(map);
        this.width = map.getWidth() * renderer.getCellWidth();
        this.height = map.getHeight();
    }

    public MapView(int x, int y, Map map, TextRenderConfig config, TextGraphics tg) {
        super(x, y);
        this.map = map;
        this.renderer = new LanternaRenderer(map, tg, config);
        
        this.width = map.getWidth() * renderer.getCellWidth();
        this.height = map.getHeight();
    }

    public LanternaRenderer getRenderer() {
        return renderer;
    }

    @Override
    public void draw(TextGraphics tg, boolean isFocused) {
        TextGraphics mapCanvas = tg.newTextGraphics(
                new TerminalPosition(x, y),
                new TerminalSize(width+2, height+2)
        );

        renderer.setGraphics(mapCanvas);
        renderer.draw(); 
    }
}
