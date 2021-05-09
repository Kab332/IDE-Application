# IDE Application

### Created 
- Kabilan Manogaran		
- Thomas Chapman
- Musabbir Ahmed Baki
- Yash Pandya

### Notes
- This project was created in April 2018, many changes have occurred since then including
  JavaFX no longer being automatically included with JDK instlattions. A new run method
  has been included to account for this. 

### Requires
- Java
- JavaFX (SDK is needed for Recommended Run Method)
- Gradle (Not required for Recommended Run method)

### Recommended Run Method (UPDATED)
- Download JavaFX SDK (https://gluonhq.com/products/javafx/)
- Clone the project and in the terminal navigate to IDE-Application
- Run the java command below, replace FX_PATH with the path to your JavaFX SDK's lib folder
	- To run multiple you can open another terminal and run the same command
- java --module-path FX_PATH --add-modules javafx.controls,javafx.fxml -jar CodeProject.jar

### IntelliJ Run Method (OLD)
- Go to the project's GitHub repository and clone or download as zip
- Open IntelliJ and open the project (make sure your Java installation has JavaFX)
- Run the project

### Gradle Create Jar (OLD) 
- git clone https://github.com/Kab332/IDE-Application
- cd IDE-Application/CodeProject/
- gradle copyTask build
- java -jar build/libs/CodeProject.jar

### Instructions 				
1) To run as a Teacher/Server
	1.a) Run the IntelliJ project
	1.b) Click Connections in the menu bar and then click Start Server
Note: This will start the server, the default Port is set to 12888.

2) To run as a Student/Client
	Note: Make a Server is already up and running:
	2.a) Run the IntelliJ project			
	2.b) Click Connections in the menubar and click Start as Student,
	2.c) Type the Server’s IP and Port and press OK
	Note: This will connect the student to Server and Load the teacher’s tabs in the Left View.

3) To Edit/Copy Teacher Code To User
	3.a) Click Edit in the menu bar, then click Copy Teacher Code To User
	Note: This open a new tab with all the code that the teacher has typed out. 

4) To Save Files
	4.a) Click File then Save or alternatively press Ctrl-S on keyboard.

5) To Run  and Compile
	5.a) Select the tab with the main method, Click Run then click Compile Program.
Note: This IDE only compiles java programs.

6) For a more complete overview of our program download our User Manual on the github page.






