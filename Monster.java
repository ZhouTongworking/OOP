package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

public class Monster {
    public String type;
    public double hp;
    public float speed;
    public double armour;
    public double mana_gained_on_kill;
    int death_count = 0;
    private boolean isdead = false; // gremlin live

    private float x; // current
    private float y;

    PApplet pApplet;
    PImage gremlin;
    PImage gremlin1;
    PImage gremlin2;
    PImage gremlin3;
    PImage gremlin4;
    PImage gremlin5;

    private GameMap gameMap;
    private int currentIndex = 0;
    //private ArrayList<PVector> pathInfo;

    static float[][] path_1 = {
            {0.25f, 4.25f},
            {4.25f, 4.25f},
            {4.25f, 6.25f},
            {16.25f, 6.25f},
            {16.25f, 9.25f},
            {10.2f, 9.25f},
            {10.2f, 15.25f},
            {3.2f, 15.25f}

    };
    static float[][] path_2 = {
            {9.25f, 1.25f},
            {9.25f, 6.25f},
            {16.25f, 6.25f},
            {16.25f, 9.25f},
            {10.2f, 9.25f},
            {10.2f, 15.25f},
            {3.25f, 15.25f}

    };
    private float[][] path;
    private int tileSize;

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public void loadMonster(PApplet p) {
        pApplet = p;
        gremlin = pApplet.loadImage("src/main/resources/WizardTD/gremlin.png");
        gremlin1 = pApplet.loadImage("src/main/resources/WizardTD/gremlin1.png");
        gremlin2 = pApplet.loadImage("src/main/resources/WizardTD/gremlin2.png");
        gremlin3 = pApplet.loadImage("src/main/resources/WizardTD/gremlin3.png");
        gremlin4 = pApplet.loadImage("src/main/resources/WizardTD/gremlin4.png");
        gremlin5 = pApplet.loadImage("src/main/resources/WizardTD/gremlin5.png");

    }

    public Monster(String type, double hp, float speed, double armour, double mana_gained_on_kill) {
        this.type = type;
        this.hp = hp;
        this.speed = speed;
        this.armour = armour;
        this.mana_gained_on_kill = mana_gained_on_kill;

    }

    public void PathSelector(float[][] selectedPath) {
        this.path = selectedPath;
        x = path[0][0] * tileSize + pApplet.random(-5, 5);;
        y = path[0][1] * tileSize + pApplet.random(-5, 5);;
        currentIndex = 0;
    }

    public void move() {
        if (currentIndex < path.length - 1) {
            PVector currentPos = new PVector(x, y);
            PVector targetPos = new PVector(path[currentIndex + 1][0] * tileSize, path[currentIndex + 1][1] * tileSize);
            PVector direction = PVector.sub(targetPos, currentPos).normalize().mult(speed);

            // Update Monster position
            x += direction.x;
            y += direction.y;

            // Check if Monster has reached the target point
            if (PVector.dist(currentPos, targetPos) < speed) {
                currentIndex++;
            }
        } else {
            // Monster has reached the end of the path
            gremlin = null;
            isdead = true;
        }
    }

    public void draw(PApplet pApplet) {
        if (gremlin != null && !isdead) {
            PImage currentImage = gremlin; // Choose appropriate image based on Monster state
            pApplet.image(currentImage, x, y);
        }
    }



    public void Isdead(boolean dead) {
        isdead = dead;
        //death_count++;
    }

    public void lastfourframes() {
        //death_count++;
    }

    public void draw() {

    }




//    private PImage chooseimage() {
//        int count = 0;
//        int frame = 1;
//        PImage selectedImage = null;
//        // if monster alive use gremlin1
//        if (!isdead) {
//            selectedImage = gremlin;
//        } else {
//            //choose images when gremlin was dead
//            int frameIndex = count / frame % 4;
//            switch (frameIndex) {
//                case 0:
//                    selectedImage = gremlin1;
//                    break;
//                case 1:
//                    selectedImage = gremlin2;
//                    break;
//                case 2:
//                    selectedImage = gremlin3;
//                    break;
//                case 3:
//                    selectedImage = gremlin4;
//                    break;
//                case 4:
//                    selectedImage = gremlin5;
//            }
//            frame++;
//        }
//
//        return selectedImage;
//
//    }
}

