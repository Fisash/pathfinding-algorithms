package com.example.tui;

import com.googlecode.lanterna.graphics.TextGraphics;

import com.example.Config;
import com.example.Map;
import com.example.text.TextRenderConfig;
import com.example.text.BorderStyle;

import java.io.File;
import java.io.IOException;

public class Main {

    private static LanternaBorderDrawer borderDrawer;
    private static MapView mapView;

    private static void addMap(ListView<Config> list, App app, TextGraphics tg) {
        Config pfConfig = list.getItem(0);

        Map map = pfConfig.map;
        TextRenderConfig visualizeConfig = new TextRenderConfig();

        mapView = new MapView(0, 0, map, visualizeConfig, tg);
        mapView.setStartAndFinish(pfConfig.start, pfConfig.finish);
        app.addToRoot(mapView, 30, 10);
    }

    public static void main(String[] args) throws IOException {
        App app = new App();
        TextGraphics tg = app.getTextGraphics();
        borderDrawer = new LanternaBorderDrawer(BorderStyle.SINGLE(), tg);


        ListView<Config> configListView = new ListView<>(0, 0, borderDrawer);
        loadConfigs("./configs", configListView);

        addMap(configListView, app, tg);

        Button createNew = new Button("Create new", 0, 0, () -> {
            //todo: creating new pathfining config
        });

        Label title = new Label("Pathfinding-TUI", 0, 0);
        LayoutHelper.centerHorizontally(title, app.getRoot());
        app.addToRoot(title, title.getX(), 1); 

        app.addToRoot(configListView, 3, 5);
        app.addToRoot(createNew, 3, 3);

        createNew.linkDown(configListView);

        app.getRoot().setFocusedChild(createNew);
        app.run();
    }

    private static void loadConfigs(String path, ListView listView) {
        File folder = new File(path);
        if (!folder.exists() || !folder.isDirectory()) return;

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".properties"));
        if (files == null) return;

        for (File file : files) {
            String name = file.getName().replace(".properties", "");
            String fullPath = file.getPath();
            Config config;
            try {
                config = new Config(fullPath);
            } catch (IOException e) {
                continue;
            }

            listView.add(createConfigButton(name, config), config);
        }
    }

    private static Button createConfigButton(String label, Config config) {
        Runnable onClick = () -> {};
        Runnable onFocusGain = () -> {
            mapView.setMap(config.map);
            mapView.setStartAndFinish(config.start, config.finish);
        };
        Button result = new Button(label, 0, 0, onClick);
        result.setFocusGainedCallback(onFocusGain);
        return result;
    }
}
