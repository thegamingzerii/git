package de.thegamingzerii.editor;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import de.thegamingzerii.logicParts.Gate;
import de.thegamingzerii.logicParts.Lever;
import de.thegamingzerii.logicParts.LogicTile;
import de.thegamingzerii.objects.BackgroundObject;
import de.thegamingzerii.objects.DeadlyBlock;
import de.thegamingzerii.objects.Jumper;
import de.thegamingzerii.objects.MovingPlatform;
import de.thegamingzerii.objects.Obstacle;
import de.thegamingzerii.objects.Rope;
import de.thegamingzerii.objects.Slope;
import de.thegamingzerii.objects.TextureBlock;
import de.thegamingzerii.spawners.Spawner;
import de.thegamingzerii.utility.Constantes.BackgroundType;
import de.thegamingzerii.utility.Constantes.BlockType;
import de.thegamingzerii.utility.Constantes.SpawnerType;

public class Map {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList<String> readMapFile() {
		// The name of the file to open.
        String fileName = "Assets/map.txt";

        // This will reference one line at a time
        String line = null;
        ArrayList<String> lines = new ArrayList();

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
            	lines.add(line);
            	
                
            } 

            // Always close files.
            bufferedReader.close();  
            return lines;
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
        return null;
	}
	
	public static ArrayList<Object> loadMap() {
		Obstacle.allObstacles.clear();
		Jumper.allJumpers.clear();
		Rope.allRopes.clear();
		DeadlyBlock.allDeadlyBlocks.clear();
		TextureBlock.allTextureBlocks.clear();
		Slope.allSlopes.clear();
		MovingPlatform.allMovingPlatforms.clear();
		BackgroundObject.allBackgroundObjects.clear();
		Gate.allGates.clear();
		Lever.allLevers.clear();
		Spawner.allSpawners.clear();
		ArrayList<String> lines = readMapFile();
		ArrayList<Object> objects = new ArrayList<Object>();
		for(int i = 0; i < lines.size(); i++) {
			String[] splited = lines.get(i).split("\\s+");
        	
			if(splited[0].equals("NextLogicId")) {
    			LogicTile.nextId = Integer.parseInt(splited[1]);
    		}
			
    		if(splited[0].equals("Obstacle")) {
    			objects.add(new Obstacle(new Point2D.Double(Double.parseDouble(splited[1]), Double.parseDouble(splited[2])), new Point2D.Double(Double.parseDouble(splited[3]), Double.parseDouble(splited[4]))));

    		}
    		
    		if(splited[0].equals("Jumper")) {
    			objects.add(new Jumper(Double.parseDouble(splited[1]), Double.parseDouble(splited[2])));
    		}
    		
    		if(splited[0].equals("Rope")) {
    			objects.add(new Rope(Double.parseDouble(splited[1]), Double.parseDouble(splited[2]), Double.parseDouble(splited[3])));
    		}
    		
    		if(splited[0].equals("DeadlyBlock")) {
    			objects.add(new DeadlyBlock(Double.parseDouble(splited[1]), Double.parseDouble(splited[2]), 
    					Double.parseDouble(splited[3]), Double.parseDouble(splited[4])));
    		}
    		if(splited[0].equals("TextureBlock")) {
    			BlockType type = null;
    			switch(Integer.parseInt(splited[3])) {
    			case 0:
    				type = BlockType.Ground;
    				break;
    			case 1:
    				type = BlockType.Grass;
    				break;
    			case 2:
    				type = BlockType.Ground2;
    				break;
    			case 3:
    				type = BlockType.Slope1;
    				break;
    			case 4:
    				type = BlockType.Slope2;
    				break;
    			case 5:
    				type = BlockType.Slope3;
    				break;
    			default:
    				break;
    			}
    			objects.add(new TextureBlock(Double.parseDouble(splited[1]), Double.parseDouble(splited[2]), type));
    		}
    		if(splited[0].equals("Slope")) {
    			objects.add(new Slope(new Point2D.Double(Double.parseDouble(splited[1]), Double.parseDouble(splited[2])), new Point2D.Double(Double.parseDouble(splited[3]), Double.parseDouble(splited[4]))));
    		}
    		if(splited[0].equals("MovingPlatform")) {
    			objects.add(new MovingPlatform(Double.parseDouble(splited[1]), Double.parseDouble(splited[2]), Double.parseDouble(splited[3]), Double.parseDouble(splited[4])));
    		}
    		if(splited[0].equals("BackgroundObject")) {
    			BackgroundType type = null;
    			switch(Integer.parseInt(splited[3])) {
    			case 0:
    				type = BackgroundType.Tree1;
    				break;
    			case 1:
    				type = BackgroundType.Bush1;
    				break;
    			}
    			objects.add(new BackgroundObject(Double.parseDouble(splited[1]), Double.parseDouble(splited[2]), type));
    		}
    		if(splited[0].equals("Gate")) {
    			objects.add(new Gate(Double.parseDouble(splited[1]), Double.parseDouble(splited[2]), Boolean.valueOf(splited[3]),
    					Integer.parseInt(splited[4]), Integer.parseInt(splited[5])));
    		}
    		if(splited[0].equals("Lever")) {
    			objects.add(new Lever(Double.parseDouble(splited[1]), Double.parseDouble(splited[2]), Boolean.valueOf(splited[3]),
    					Integer.parseInt(splited[4]), Integer.parseInt(splited[5])));
    		}
    		if(splited[0].equals("Spawner")) {
    			SpawnerType type = null;
    			switch(Integer.parseInt(splited[3])) {
    			case 0:
    				type = SpawnerType.Leaf;
    				break;
    			default:
    				break;
    			}
    			objects.add(new Spawner(Double.parseDouble(splited[1]), Double.parseDouble(splited[2]), type));
    		}
		}
		return objects;
            	
            		
            		
            	
                
	}
	
	
	
	
	public static void addToMap(String mapObject) {
		
		ArrayList<String> object = readMapFile();
		object.add(mapObject);
		writeToMap(object);
		loadMap();
	}
	
	
	public static void writeToMap(ArrayList<String> args) {

        // The name of the file to open.
        String fileName = "Assets/map.txt";

        try {
            // Assume default encoding.
            FileWriter fileWriter =
                new FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                new BufferedWriter(fileWriter);

            for(int i = 0; i < args.size(); i++) {
            	bufferedWriter.write(args.get(i));
            	bufferedWriter.newLine();
            }

            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + fileName + "'");
        }
		
	}
	
	
	public static void reWriteMap() {
		ArrayList<String> object = new ArrayList<String>();
		
		object.add("NextLogicId " + LogicTile.nextId);
		
		for(int i = 0; i < Jumper.allJumpers.size(); i++) {
			object.add(Jumper.allJumpers.get(i).toString());
		}
		for(int i = 0; i < Obstacle.allObstacles.size(); i++) {
			object.add(Obstacle.allObstacles.get(i).toString());
		}
		for(int i = 0; i < Rope.allRopes.size(); i++) {
			object.add(Rope.allRopes.get(i).toString());
		}
		for(int i = 0; i < DeadlyBlock.allDeadlyBlocks.size(); i++) {
			object.add(DeadlyBlock.allDeadlyBlocks.get(i).toString());
		}
		for(int i = 0; i < TextureBlock.allTextureBlocks.size(); i++) {
			object.add(TextureBlock.allTextureBlocks.get(i).toString());
		}
		for(int i = 0; i < Slope.allSlopes.size(); i++) {
			object.add(Slope.allSlopes.get(i).toString());
		}
		for(int i = 0; i < MovingPlatform.allMovingPlatforms.size(); i++) {
			object.add(MovingPlatform.allMovingPlatforms.get(i).toString());
		}
		for(int i = 0; i < BackgroundObject.allBackgroundObjects.size(); i++) {
			object.add(BackgroundObject.allBackgroundObjects.get(i).toString());
		}
		for(int i = 0; i < Gate.allGates.size(); i++) {
			object.add(Gate.allGates.get(i).toString());
		}
		for(int i = 0; i < Lever.allLevers.size(); i++) {
			object.add(Lever.allLevers.get(i).toString());
		}
		for(int i = 0; i < Spawner.allSpawners.size(); i++) {
			object.add(Spawner.allSpawners.get(i).toString());
		}
			
		writeToMap(object);
		
	}
}
