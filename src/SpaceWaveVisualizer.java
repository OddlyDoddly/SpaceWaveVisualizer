import objects.RoundBarVisualizer;
import objects.Terrain;
import processing.core.PApplet;
import processing.core.PImage;
import processing.sound.AudioIn;
import processing.sound.FFT;

public class SpaceWaveVisualizer extends PApplet {

    private Terrain terrain;
    private PImage background, eclipse;
    private RoundBarVisualizer roundBarVisualizer;

    private final float[] audioSpectrum = new float[128];
    private FFT fft;
    private final AudioIn audioIn = new AudioIn(this, 0);

    public void settings() {
        size(720, 480, P3D);
    }

    public void setup() {
        colorMode(HSB);

        this.fft = new FFT(this, audioSpectrum.length);
        audioIn.start();
        fft.input(audioIn);

        terrain = new Terrain(this, width/2, height/2, 0, width*1.5f, height, 10, 0.1f);
        roundBarVisualizer = new RoundBarVisualizer(this, width/2 + 16, height/2 - 64, 0, 256, audioSpectrum.length);
        background = loadImage("data/background.jpg");
        background.resize(width, height);
        frameRate(12);
    }

    public void draw() {
        background(background);
        fft.analyze(audioSpectrum);
        pushMatrix();
        translate(0, -height/2/2, -540);
        roundBarVisualizer.draw(audioSpectrum);
        popMatrix();
        terrain.draw();
    }
    public static void main(String[] args) {
        PApplet.main("SpaceWaveVisualizer");
    }
}
