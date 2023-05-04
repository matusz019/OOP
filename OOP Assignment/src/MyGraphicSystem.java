import uk.ac.leedsbeckett.oop.LBUGraphics;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class MyGraphicSystem extends LBUGraphics{
    
   
    //Declaring variables needed for the main commands
    private final String[] Standard_Commands = 
	{ "reset", "about", "pendown", "penup", "clear", "help", "blue", "green", "red", "white" };
    
    private final String[] noParameter_Commands = 
	{"turnleft", "turnright", "forward", "backward","save"};
    private final String[] strParameter_Commands = 
	{"save", "load"};
    
    public void processCommand(String action) {
	
	String[] commands = action.split(" ");
	if (isCommandValid(commands[0]) == false) {
		displayMessage("Invalid Command - " + "please type help for all commands");
		return;
	}
	if (parameterValid(commands) == false) {
	    displayMessage("missing or incorect parameter please input a correct parameter and try again");
	    return;
	}
	
	switch (commands[0]) {
	case "about":
	    about();
	    break;
	case "pendown":
	    penDown();
	    break;
	case "penup":
	    penUp();
	    break;
	case "forward":
	    forward(commands[1]);
	    break;
	case "clear":
	    clear();
	    clearInterface();
	    break;
	case "help":
	    displayHelp();
	    break;
	case "reset":
	    reset();
	    break;
	case "blue":
	    setPenColour(Color.BLUE);
	    break;
	case "red":
	    setPenColour(Color.RED);
	    break;
	case "green":
	    setPenColour(Color.GREEN);
	    break;
	case "white":
	    setPenColour(Color.WHITE);
	    break;
	case "backward":
	    forward(-(Integer.parseInt(commands[1])));
	    break;
	case("turnleft"):
	    turnLeft(commands[1]);
	    break;
	case("turnright"):
	    turnRight(commands[1]);
	    break;
	case "save":
	    saveImage(commands[1]);
	    break;
	case "load":
	    loadImage(commands[1]);
	    break;
	}
}
    

	private void loadImage(String name) {
	try {
	    BufferedImage image = ImageIO.read(new File(name + ".png"));
	    setBufferedImage(image);
	}catch(IOException e) {
	    System.out.println("Error " + e.getMessage());
	}
	
    }


	private void saveImage(String name) {
	try {
	    BufferedImage image = getBufferedImage();
	    File output = new File(name + ".png");
	    ImageIO.write(image, "png", output);
	    
	}catch(IOException e) {
	    System.out.println("Error: " + e.getMessage());
	}
	
    }


	private void displayHelp() {
	String commands = "<html>Valid Commands Are:<br />";
	for (String str: Standard_Commands) {	
	    commands += (str + "<br />");
	}
	for(String str: noParameter_Commands) {
	    commands +=(str+ "<br />");
	}
	for(String str: strParameter_Commands) {
	    commands +=(str+ "<br />");
	}
	displayMessage(commands);

    }

    private boolean isCommandValid(String commands) {
	boolean valid_standard = Arrays.stream(Standard_Commands).anyMatch(commands::equals);
	boolean valid_noParameterCommand = Arrays.stream(noParameter_Commands).anyMatch(commands::equals);
	boolean valid_strParameterCommand = Arrays.stream(strParameter_Commands).anyMatch(commands::equals);
	if (valid_standard == true || valid_noParameterCommand == true || valid_strParameterCommand == true) {
	    return true;
	}
	else {
	    return false;
	}
    }
    
    private boolean parameterValid(String[] commands) {
	//checks if the command is a parameter command and then checks if the parameter is missing
	
	boolean missing_parameter = true;
	if(commands.length == 2 &&	//check if the command typed is a parameter command or not and if there is a missing parameter
		  (Arrays.stream(noParameter_Commands).anyMatch(commands[0]::equals)) == true ||
		  (Arrays.stream(strParameter_Commands).anyMatch(commands[0]::equals)) == true ||
		  (Arrays.stream(Standard_Commands).anyMatch(commands[0]::equals)) == true){
		    missing_parameter = false;
		}
		else {
		missing_parameter = true;
		}
	// Checks if the parameter is numeric
	
	boolean numeric_parameter = true;
	if(commands.length == 2 && (Arrays.stream(strParameter_Commands).anyMatch(commands[0]::equals)) == false) {
	    try {
		Integer.parseInt(commands[1]);
		numeric_parameter = true;
	    }catch(NumberFormatException e) {
		numeric_parameter = false;
	    }
	}
	//checks if parameter is correctly bound
	
	boolean appropriate_parameter = true;
	if((Arrays.stream(strParameter_Commands).anyMatch(commands[0]::equals)) == false)
	    if(commands.length == 2 && Integer.parseInt(commands[1]) <= 360 && Integer.parseInt(commands[1]) >= 0 ) {
		appropriate_parameter = true;
	    }
	    else {
		if(Arrays.stream(Standard_Commands).anyMatch(commands[0]::equals) == true) {
		    appropriate_parameter = true;
		}
		else {
		    appropriate_parameter = false;
		}
	    }
	
	if(missing_parameter == false && numeric_parameter == true && appropriate_parameter == true) {
	    return true;
	}
	else {
	    return false;
	}
	
    }


    public MyGraphicSystem()
    {
            JFrame MainFrame = new JFrame();                
            MainFrame.setLayout(new FlowLayout());  
            MainFrame.add(this);                                   
            MainFrame.pack();                                               
            MainFrame.setVisible(true);                           
                                                                      
    }
    
    
    

}
