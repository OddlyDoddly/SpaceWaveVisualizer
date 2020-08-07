package objects;

import lombok.Data;
import lombok.Getter;
import processing.core.PApplet;
import processing.core.PShape;

import static processing.core.PApplet.*;


@Data
public class Terrain {

    @Getter
    private float x, y, z;

    @Getter
    private final float gridWidth, gridHeight;

    private final int cols, rows, blockScale;

    private float waveSpeed;
    private float[][] noiseGrid;

    PApplet parent;

    public Terrain(PApplet parent, float x, float y, float z,
                   float gridWidth, float gridHeight,
                   int blockScale, float waveSpeed) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.z = z;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.blockScale = blockScale;
        this.cols = (int) gridWidth/blockScale;
        this.rows =  (int) gridHeight/blockScale;
        this.noiseGrid = new float[cols][rows];
    }

    public void draw() {
        parent.pushMatrix();

        parent.translate(x, y, z);
        parent.rotateX(PI/3);
        parent.translate(-gridWidth/2, -gridHeight/2);

        float yOffset = waveSpeed;
        float yWeight = 0.2f;
        float xWeight = 0.2f;

        noiseGrid[0][0] = map(parent.noise(0, yOffset), 0, 1, -50, 50);
        for(int y =0; y < rows-1; ++y) {
            parent.beginShape(TRIANGLE_STRIP);
            parent.stroke(0);
            float xOffset = 0;
            for(int x = 0; x < cols; ++x) {
                noiseGrid[x][y+1] = map(parent.noise(xOffset + (xWeight*(cols - x)), yOffset+yWeight), 0, 1, -50, 50);
                parent.fill(map(y%255, 0, rows-1, 0, 255), 255, 255);
                parent.vertex(x*blockScale, y*blockScale, noiseGrid[x][y]);
                parent.vertex(x*blockScale, (y+1)*blockScale, noiseGrid[x][y+1]);
            }
            yOffset += yWeight;
            parent.endShape();
        }
        waveSpeed -= 0.01;
        parent.popMatrix();
    }
}
