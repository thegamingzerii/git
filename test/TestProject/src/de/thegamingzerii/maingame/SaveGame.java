package de.thegamingzerii.maingame;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import de.thegamingzerii.states.GameState;

public class SaveGame {
	
	public static ArrayList<String> readSaveGameFile() {
		// The name of the file to open.
        String fileName = "Assets/saveGame.txt";

        // This will reference one line at a time
        String line = null;
        ArrayList<String> lines = new ArrayList<String>();

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
	
	
	public static ArrayList<Object> loadSaveGame() {

		ArrayList<String> lines = readSaveGameFile();
		ArrayList<Object> objects = new ArrayList<Object>();
		for(int i = 0; i < lines.size(); i++) {
			String[] splited = lines.get(i).split("\\s+");
        	
    		
    		if(splited[0].equals("Position")) {
    			GameState.player.setX((int) Double.parseDouble(splited[1]));
    			GameState.player.setY((int) Double.parseDouble(splited[2]));
    		}
    		if(splited[0].equals("CheckPoint")) {
    			GameState.player.checkPoint = new Point2D.Double(Double.parseDouble(splited[1]), Double.parseDouble(splited[1]));

    		}

		}
		return objects;
            	
            		
            		
            	
                
	}
	
	
	
	public static void saveGame() {

        // The name of the file to open.
        String fileName = "Assets/saveGame.txt";

        
        ArrayList<String> args = new ArrayList<String>();
        args.add("Position " + GameState.player.getXAxis() + " " + GameState.player.getYAxis());
        args.add("CheckPoint " + GameState.player.checkPoint.x + " " + GameState.player.checkPoint.y);
        
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
}
