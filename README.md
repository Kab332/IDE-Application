# **IDE Application**

### **Created** 
- Kabilan Manogaran		
- Thomas Chapman
- Musabbir Ahmed Baki
- Yash Pandya

### **Notes**
- This project was created in April 2018, many changes have occurred since then including
  JavaFX no longer being automatically included with JDK instlattions. A new run method
  has been included to account for this. 

### **Requires**
- Java
- JavaFX (SDK is needed for Recommended Run Method)
- Gradle (Not required for Recommended Run method)

### **Recommended Run Method (UPDATED)**
- Download JavaFX SDK (https://gluonhq.com/products/javafx/)
- Clone the project and in the terminal navigate to IDE-Application
- Run the java command below, replace FX_PATH with the path to your JavaFX SDK's lib folder
	- To run multiple you can open another terminal and run the same command
- java --module-path FX_PATH --add-modules javafx.controls,javafx.fxml -jar CodeProject.jar

### **IntelliJ Run Method (OLD)**
- Go to the project's GitHub repository and clone or download as zip
- Open IntelliJ and open the project (make sure your Java installation has JavaFX)
- Run the project

### **Gradle Create Jar (OLD)** 
- git clone https://github.com/Kab332/IDE-Application
- cd IDE-Application/CodeProject/
- gradle copyTask build
- java -jar build/libs/CodeProject.jar

### **Instructions**				
- **To run as a Teacher/Server**
	- Run the IntelliJ project
	- Click Connections in the menu bar and then click Start Server
	- **Note**: This will start the server, the default port is set to 12888

- **To run as a Student/Client**
	- **Note**: Make sure a server is already up and running
	- Run the IntelliJ project			
	- Click Connections in the menu bar and click Start as Student
	- Type the Server’s IP and port and press OK
	- **Note**: This will connect the student to server and load the teacher’s tabs in the Left View

- **To Edit/Copy Teacher Code To User**
	- Click Edit in the menu bar, then click Copy Teacher Code To User
	- **Note**: This open a new tab with all the code that the teacher has typed out

- **To Save Files**
	- Click File then Save or alternatively press Ctrl-S on keyboard

- **To Run and Compile**	
	- Select the tab with the main method, Click Run then click Compile Program
	- **Note**: This IDE only compiles java programs

- **For a more complete overview of our program download our User Manual on the github page**






