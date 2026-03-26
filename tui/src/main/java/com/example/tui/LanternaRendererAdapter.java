package com.example.tui;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.graphics.TextGraphics;

import com.example.Map;
import com.example.console.PFConsoleRenderer;
import com.example.console.ConsoleRenderFrameOptions;

import java.io.IOException;

class LanternaRendererAdapter {
    private final Screen screen;
    private final PFConsoleRenderer renderer;

    private int offsetX;
    private int offsetY;

    public LanternaRendererAdapter(Screen screen,
                                   Map map,
                                   int offsetX,
                                   int offsetY) {
        this.screen = screen;
        this.offsetX = offsetX;
        this.offsetY = offsetY;

        ConsoleRenderFrameOptions baseFrame = 
        new ConsoleRenderFrameOptions('═', '║', new char[]{'╔', '╗', '╚', '╝'});

        renderer = new PFConsoleRenderer(0, baseFrame, map);
    }

    public void renderFrame(Map map) throws IOException {
        int cellWidth = PFConsoleRenderer.evaluateCellWidth(map);
        renderer.setCellWidth(cellWidth);
        renderer.initRender(map);
        renderer.renderMap(map);

        String frame = renderer.renderToString(cellWidth);
        renderFrame(frame); 
    }

    public void renderFrame(String frame) throws IOException {
        TextGraphics tg = screen.newTextGraphics();

        String[] lines = frame.split("\n");

        for (int y = 0; y < lines.length; y++)
            tg.putString(offsetX, offsetY + y, lines[y]);

        screen.refresh();
    }
}
