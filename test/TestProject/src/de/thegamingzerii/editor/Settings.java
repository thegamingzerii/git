package de.thegamingzerii.editor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import de.thegamingzerii.maingame.Game;

public class Settings {
	
	public static ArrayList<String> readSettingsFile() {
		// The name of the file to open.
        String fileName = "Assets/settings.txt";

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
	
	
	public static ArrayList<Object> loadSettings() {

		ArrayList<String> lines = readSettingsFile();
		ArrayList<Object> objects = new ArrayList<Object>();
		for(int i = 0; i < lines.size(); i++) {
			String[] splited = lines.get(i).split("\\s+");
        	
    		
    		if(splited[0].equals("drawHitBoxes")) {
    			Game.drawHitBoxes = Boolean.valueOf(splited[1]);
    		}

		}
		return objects;
            	
            		
            		
            	
                
	}
	
	
	
	public static void saveSettings() {

        // The name of the file to open.
        String fileName = "Assets/settings.txt";

        
        ArrayList<String> args = new ArrayList<String>();
        args.add("drawHitBoxes " + Game.drawHitBoxes);
        
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
