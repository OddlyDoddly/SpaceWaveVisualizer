package objects;

import processing.core.PApplet;

import static processing.core.PApplet.map;
import static processing.core.PConstants.TWO_PI;

public class RoundBarVisualizer {

    private final PApplet parent;
    private final float x, y, z, r;
    private final int bands;

    private float angle = 0;

    public RoundBarVisualizer(PApplet parent, float x, float y, float z, float r, int bands) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
        this.bands = bands;
        this.angle = TWO_PI/bands;
    }

    public void draw(float[] spectrum) {
        parent.strokeWeight(6);
        for(int i =0; i < bands; ++i) {
            parent.pushMatrix();
            float mod = map(spectrum[i], 0, 1, 10, 1000);
            parent.translate(x, y, z);
            parent.rotate(angle+i*TWO_PI/bands);
            parent.stroke(map(i%255, 0, bands, 0, 255), 255, 255);
            parent.line(r, 0, r+mod, 0);
            parent.popMatrix();
        }
        angle += 0.01;
        parent.strokeWeight(1);
    }
}
