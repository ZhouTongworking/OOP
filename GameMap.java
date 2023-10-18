package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameMap {
    int[][] grid;
    PApplet pApplet;
    String mappath;

    PImage image0;
    PImage image1;
    PImage image2;
    PImage image3;
    PImage grass;
    PImage shrub;
    PImage wizard_house;
    PImage rotated_wizard_house;
    App App;

    private ArrayList<PVector> path_info = new ArrayList<>();
    public GameMap(PApplet p, String path) {
        pApplet = p;
        mappath = path;
        App = new App();

        // Converting BufferedImage to PImage
        image0 = pApplet.loadImage("src/main/resources/WizardTD/path0.png");
        image1 = pApplet.loadImage("src/main/resources/WizardTD/path1.png");
        image2 = pApplet.loadImage("src/main/resources/WizardTD/path2.png");
        image3 = pApplet.loadImage("src/main/resources/WizardTD/path3.png");
        grass = pApplet.loadImage("src/main/resources/WizardTD/grass.png");
        shrub = pApplet.loadImage("src/main/resources/WizardTD/shrub.png");
        wizard_house = pApplet.loadImage("src/main/resources/WizardTD/wizard_house.png");
        rotated_wizard_house = App.rotateImageByDegrees(wizard_house, 270);

        loadmap();
    }

    private void loadmap() {
        try {
            Scanner scanner = new Scanner(new File(mappath));
            List<String> lines = new ArrayList<>();
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }

            path_info = PathInfo(lines);

            int rows = lines.size();
            int cols = 0;

            // Find the maximum column count
            for(String line : lines) {
                cols = Math.max(cols, line.length());
            }

            // Initialize the grid with proper dimensions
            grid = new int[rows][cols];

            for (int r = 0; r < rows; r++) {
                char[] chars = lines.get(r).toCharArray();
                for (int c = 0; c < cols; c++) {
                    // Ensure that the grid is populated even if the character does not exist
                    if (c < chars.length) {
                        grid[r][c] = charToCode(chars[c]);
                    } else {
                        // Assign default code for spaces beyond the end of the line
                        grid[r][c] = charToCode(' ');
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<PVector> PathInfo(List<String> lines) {
        ArrayList<PVector> pathInfo = new ArrayList<>();

        for (int r = 0; r < lines.size(); r++) {
            char[] chars = lines.get(r).toCharArray();
            for (int c = 0; c < chars.length; c++) {
                if (chars[c] == 'X') {
                    pathInfo.add(new PVector(c, r));
                }
            }
        }
        for (PVector point : pathInfo) {
            System.out.println("(" + point.x + ", " + point.y + ")");
        }

        return pathInfo;
    }

    private int charToCode(char c) {
        switch (c) {
            case ' ':
                return 0; // Grass
            case 'X':
                return 1; // Path
            case 'S':
                return 2; // Shrub
            case 'W':
                return 3; // Wizard's House
            default:
                return -1; // Error code
        }
    }

    public void draw() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {  // Fixed the loop variable here
                PImage image = grass;
                pApplet.image(image, j * App.CELLSIZE , i * App.CELLSIZE + 40, App.CELLSIZE, App.CELLSIZE);
                switch (grid[i][j]) {
                    case 0: // Grass
                        image = grass;
                        break;
                    case 1: // Path
                        image = image0;
                        // check neighbor paths
                        int count = getCount(i, j);
                        // check if any path above or below and use path1
                        if (((i > 0 && grid[i - 1][j] == 1) &&
                                !(i < grid.length - 1 && grid[i + 1][j] == 1))
                                || (!(i > 0 && grid[i - 1][j] == 1) &&
                                (i < grid.length - 1 && grid[i + 1][j] == 1))) {
                            //left and above
                            if((j > 0 && grid[i][j - 1] == 1)&&
                                    ((i > 0 && grid[i - 1][j] == 1))){
                                image = App.rotateImageByDegrees(image1,90);
                            }
                            //above and right
                            else if ((i > 0 && grid[i - 1][j] == 1)&&
                                    ((j < grid[i].length - 1 && grid[i][j + 1] == 1))) {
                                image = App.rotateImageByDegrees(image1,180);
                            }
                            //right and below
                            else if((j < grid[i].length - 1 && grid[i][j + 1] == 1)&&
                                    ((i < grid.length - 1 && grid[i + 1][j] == 1))){
                                image = App.rotateImageByDegrees(image1,270);
                            }
                            //left and below
                            else{
                                image = image1;
                            }
                        }
                        // check there are any path above and below and use path0
//                        else if ((i > 0 && grid[i - 1][j] == 1) &&
//                                (i < grid.length - 1 && grid[i + 1][j] == 1)) {
//                            image = image0;
//                        }

                        if(count == 4){
                            image = image3;
                        }else if(count == 3) {
                            //left above right
                            if((j > 0 && grid[i][j - 1] == 1) &&
                                    ((i > 0 && grid[i - 1][j] == 1))&&
                                    ((j < grid[i].length - 1 && grid[i][j + 1] == 1))){
                                image = App.rotateImageByDegrees(image2,180);
                            }
                            //below left above
                            else if((i < grid.length - 1 && grid[i + 1][j] == 1)&&
                                    ((j > 0 && grid[i][j - 1] == 1))&&
                                    ((i > 0 && grid[i - 1][j] == 1))){
                                image = App.rotateImageByDegrees(image2,90);
                            }
                            //below right above
                            else if((i < grid.length - 1 && grid[i + 1][j] == 1)&&
                                    ((j < grid[i].length - 1 && grid[i][j + 1] == 1))&&
                                    ((i > 0 && grid[i - 1][j] == 1))){
                                image = App.rotateImageByDegrees(image2,270);
                            }
                            //left below right
                            else{
                                image = image2;
                            }

                        }else if(count ==2){
                          if((i > 0 && grid[i - 1][j] == 1) &&
                                (i < grid.length - 1 && grid[i + 1][j] == 1)){
                              image = App.rotateImageByDegrees(image0,90);
                          }
                        } else if(count ==1){
                            //below shrub and wizard_house
                            if((i < grid.length - 1 && grid[i + 1][j] == 2) ||(i < grid.length - 1 && grid[i + 1][j] == 3)){
                                image = App.rotateImageByDegrees(image0,90);
                            }
                            //above shrub
                            else if ((i > 0 && grid[i - 1][j] == 2)){
                                image = App.rotateImageByDegrees(image0,90);
                            }
                            //check above boundary
                            else if((i == 0 && grid[i + 1][j] == 1)){
                                image = App.rotateImageByDegrees(image0, 90);
                            }
                            //check below boundary
                            else if((i == grid.length - 1 && grid[i - 1][j] == 1)){
                                image = App.rotateImageByDegrees(image0, 90);
                            }else{
                                image = image0;
                            }

                        }
                        break;
                    case 2: // Shrub
                        image = shrub;
                        break;
//                    case 3: // Wizard's House
//                        if((i > 0 && grid[i - 1][j] == 1)){
//                            image = App.rotateImageByDegrees(wizard_house,270);
//                        }else{
//                            image = wizard_house;
//                        }
//                        break;
                    default: // Error code, pink color for visibility
                        break;
                }
                pApplet.image(image, j * App.CELLSIZE, i * App.CELLSIZE + 40, App.CELLSIZE, App.CELLSIZE);
            }

            for (int j = 0; j < grid[i].length; j++) {
                // Wizard's House
                if (grid[i][j] == 3) {
                    if((i > 0 && grid[i - 1][j] == 1)){
                        pApplet.image(rotated_wizard_house, (j * App.CELLSIZE) - (48 - App.CELLSIZE) / 2.5f, (i * App.CELLSIZE) - (48 - App.CELLSIZE) + 3.9f + 40, 48, 48);
                    }
                    else{
                        pApplet.image(wizard_house,  (j * App.CELLSIZE) - (48 - App.CELLSIZE) / 2.5f, (i * App.CELLSIZE) - (48 - App.CELLSIZE) + 3.9f+ 40, 48, 48);
                    }
                }
            }
        }
    }

    private int getCount(int i, int j) {
        int count = 0;
        //check above
        if (i > 0 && grid[i - 1][j] == 1) {
            count++;
        }
        // check below
        if (i < grid.length - 1 && grid[i + 1][j] == 1) {
            count++;
        }
        // check left
        if (j > 0 && grid[i][j - 1] == 1) {
            count++;
        }
        // check right
        if (j < grid[i].length - 1 && grid[i][j + 1] == 1) {
            count++;
        }
        return count;
    }


}


