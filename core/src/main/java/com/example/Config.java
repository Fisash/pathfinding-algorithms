package com.example;

import java.util.Properties;
import java.io.FileReader;
import java.io.IOException;

public class Config {

    public Map map;

    public Point start;
    public Point finish;

    public int rendererStateDelay;    
    public Algorithm algorithm;

    public Config (String path) throws IOException {
        try(FileReader reader = new FileReader(path)){
            Properties props = new Properties();
            props.load(reader);

            String mapString = props.getProperty("map");
            if (mapString == null || mapString.isEmpty())
                throw new IllegalArgumentException("Map string is empty");
            map = new Map(mapString);

            String[] startString = props.getProperty("start").split(";");
            int start_x = Integer.parseInt(startString[0].trim());
            int start_y = Integer.parseInt(startString[1].trim());

            String[] finishString = props.getProperty("finish").split(";");
            int finish_x = Integer.parseInt(finishString[0].trim());
            int finish_y = Integer.parseInt(finishString[1].trim());

            start = new Point(start_x, start_y);
            finish = new Point(finish_x, finish_y);

            rendererStateDelay = Integer.parseInt(props.getProperty("renderer_delay"));
            String algorithmString = props.getProperty("algorithm");
            algorithm = Enum.valueOf(Algorithm.class, algorithmString); 

        }
        catch (IOException e){
            throw e;
        }
    }

}
