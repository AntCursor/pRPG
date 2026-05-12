package scene;

import types.Vec2;

public interface SceneInf {
    void setup();

    void draw(Vec2 screenSize);

    void handleClick(Vec2 clickPos, Vec2 screenSize);

    void handleKey(int keyCode);
}
