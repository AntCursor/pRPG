package scenes;

import grid.*;
import processing.core.*;

public class Exploration implements SceneInf{
    private float noiseScale = 0.15f;
    private PApplet sketch;
    private Grid grid;
    private GridRenderer renderer;

    private PImage darkgrassImg, grassImg, sandImg, rockImg;
    

    public Exploration(PApplet sketch) {
        this.sketch = sketch;
        worldgen(this.grid);
        
        //imagens 
        darkgrassImg = sketch.loadImage("");
        grassImg  = sketch.loadImage("");
        sandImg   = sketch.loadImage("");
        rockImg   = sketch.loadImage("");

        this.renderer = new GridRenderer(sketch, grid, (byte type) -> getSprite(type));
    }

    public PImage getSprite(byte type){
        switch (type){
            case 0: return darkgrassImg;
            case 1: return grassImg;
            case 2: return sandImg;
            case 3: return rockImg;
            default: return grassImg;
        }
    }

    public void worldgen(Grid grid) {
        sketch.noiseSeed((long)sketch.random(1000));
        grid.forEach((x, y) -> {
            float nos = sketch.noise(x * noiseScale, y * noiseScale);
            byte type;
            if (nos < 0.4) type = 0;
            else if (nos < 0.6) type = 1;
            else if (nos < 0.9) type = 2;
            else type = 3;

            grid.set(x, y, type);
        });
    }

    @Override
    public void Sdraw() {
        renderer.render();
    }
}
