package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.MouseEvent;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.io.*;
import java.util.*;

public class App extends PApplet {

    // load the map
    int currentLevel = 2;
    GameMap gameMap;
    Monster monster;
    List<Wave> waves = new ArrayList<>();
    List<Monster> monsters = new ArrayList<>();
    int currentWaveIndex = 0;
    Wave currentWave;

    private List<WaveInfo> waveInfoList;

    public static final int CELLSIZE = 32;
    public static final int SIDEBAR = 120;
    public static final int TOPBAR = 40;
    public static final int BOARD_WIDTH = 20;

    public static int WIDTH = CELLSIZE * BOARD_WIDTH + SIDEBAR;
    public static int HEIGHT = BOARD_WIDTH * CELLSIZE + TOPBAR;

    public static final int FPS = 60;

    public String configPath;

    public Random random = new Random();

    // Feel free to add any additional methods or attributes you want. Please put classes in different files.

    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
     */
    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
     */
    @Override
    public void setup() {
        frameRate(FPS);
//        gameMap = new GameMap(this, "src/main/resources/WizardTD/level1.txt");
        loadLevel(currentLevel);
        // Set initial position
//        monster = new Monster("gremlin", 10, 1.0f, 2, 10);
//        monster.setTileSize(CELLSIZE);
//        monster.loadMonster(this);
//        monster.PathSelector(Monster.path_2);
        JSONObject configData = loadJSONObject("config.json");
        JSONArray wavesData = configData.getJSONArray("waves");
        //println(wavesData);
        if (wavesData != null) {
            waves = Wave.parseWaves(wavesData);
            if (!waves.isEmpty()) {
                currentWave = waves.get(0);
                List<WaveInfo> waveInfoList = currentWave.getWaveInfo();
                println(waveInfoList);
                for (WaveInfo waveInfo : waveInfoList) {
                    List<MonsterInfo> monstersInfo = waveInfo.getMonstersInfo();
                    for (MonsterInfo monsterInfo : monstersInfo) {
                        for (int i = 0; i < monsterInfo.get_quantity(); i++) {
                            Monster monster = new Monster(monsterInfo.get_type(), monsterInfo.get_hp(), monsterInfo.get_speed(), monsterInfo.get_armour(), monsterInfo.get_mana_gained_on_kill());
                            monster.setTileSize(CELLSIZE);
                            monster.loadMonster(this);
                            monster.PathSelector(Monster.path_2);
                            monsters.add(monster);
                        }
                    }
                }
            }
        } else {
            println("error");
        }


//        loadLevel(1);
//        gameMap = new GameMap(this, "./level2.txt");
        // Load images during setup
        // Eg:
        // loadImage("src/main/resources/WizardTD/tower0.png");
        // loadImage("src/main/resources/WizardTD/tower1.png");
        // loadImage("src/main/resources/WizardTD/tower2.png");
    }



    private void loadLevel(int level) {
        gameMap = new GameMap(this, "level" + level + ".txt");
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
	@Override
    public void keyPressed(){
        
    }

    /**
     * Receive key released signal from the keyboard.
     */
	@Override
    public void keyReleased(){

    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /*@Override
    public void mouseDragged(MouseEvent e) {

    }*/

    /**
     * Draw all elements in the game by current frame.
     */
	@Override
    public void draw() {
        background(131,111,75);
        gameMap.draw();
        fill(131,111,75);
        rect(650,60,45,45);
        rect(650,120,45,45);
        rect(650,180,45,45);
        rect(650,240,45,45);
        rect(650,300,45,45);
        rect(650,360,45,45);
        rect(650,420,45,45);
        //new Readconf();
//        monster.move();
//        monster.draw(this);
        if (currentWaveIndex < waves.size()) {
            currentWave = waves.get(currentWaveIndex);
            currentWave.update();
        }


        for (Monster monster : monsters) {
            monster.move();
            monster.draw(this);
        }





    }

    public static void main(String[] args) {
        PApplet.main("WizardTD.App");
    }

    /**
     * Source: https://stackoverflow.com/questions/37758061/rotate-a-buffered-image-in-java
     * @param pimg The image to be rotated
     * @param angle between 0 and 360 degrees
     * @return the new rotated image
     */
    public PImage rotateImageByDegrees(PImage pimg, double angle) {
        BufferedImage img = (BufferedImage) pimg.getNative();
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        PImage result = this.createImage(newWidth, newHeight, ARGB);
        //BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        BufferedImage rotated = (BufferedImage) result.getNative();
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        for (int i = 0; i < newWidth; i++) {
            for (int j = 0; j < newHeight; j++) {
                result.set(i, j, rotated.getRGB(i, j));
            }
        }

        return result;
    }
}
