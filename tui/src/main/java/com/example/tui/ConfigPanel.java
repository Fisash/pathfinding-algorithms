package com.example.tui;

class ConfigPanel {
    private String name;

    private String pathToFile;

    public String getPath() {
        return pathToFile;
    }

    public String getName() {
        return name;
    }

    ConfigPanel(String name, String pathToFile) {
        this.name = name;
        this.pathToFile = pathToFile;
    }
}
