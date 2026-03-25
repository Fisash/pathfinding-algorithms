package com.example.tui;

import com.example.Config;
import java.io.IOException;

class ConfigPanel {
    private Button button;
    public Button getButton() {
        return button;
    }

    private String pathToFile;
    public String getPath() {
        return pathToFile;
    }

    private Config config;
    public Config getConfig() {
        return config;
    }

    ConfigPanel(String pathToFile, Button button){
        this.pathToFile = pathToFile;
        this.button = button;
        try {
            config = new Config(pathToFile);
        }
        catch (IOException e) {
            config = null;
        }
    }
}
