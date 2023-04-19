## Command Line Configurations
To use with a pre-made script

```
java -jar ImageCollager.jar -file script.txt
```

To use in text view mode
```
java -jar ImageCollager.jar -text
```

To use in GUI mode
```
java -jar ImageCollager.jar
```

## How to Use Text View

Start the program with the argument "Text".

Once this occurs, and the main method runs, the console will output
"Welcome to our Image Processor." and then "Awaiting command:".

From there the user can type in the following commands (case-sensitive):

1. **help**: Outputs every valid command the user can input along with their descriptions.
2. **?**: Does the same as the help command.
3. **quit**: Exits the program.
4. **q**: Exits the program.
3. **filter-list**: Outputs every supported filter that the user can apply to a layer.
4. **new-project**: Starts a new project once given two more additional inputs:
   two non-zero positive integers.
  * Example:
   ``` 
   new-project 32 32
   ```

### The following commands will fail if there is no project open:

5. **save-project**: Saves the current project as a .collage file once given an additional input:
   this input is the file name and must not contain a period or be just whitespace.
  * Example:
   ``` 
   new-project 2 2 
   save-project myProject
   ```
6. **add-layer**: Adds a new layer to an open project once given one additional input:
   a name for the layer. The layer name cannot contain any spaces or linebreaks.
  * Example:
   ``` 
   new-project 2 2 
   add-layer Layer2
   ```
7. **add-image-to-layer**: Adds an image from an existing .ppm image once given 3 additional inputs:
   the layer name to put the image on, the filepath location of the .ppm image, and two positive
   integers, representing coordinates on the canvas.
  * Example:
   ``` 
   new-project 2 2 
   add-image-to-layer Layer1 ./res/smol.ppm 0 0
   ```
8. **set-filter**: Sets a filter to a layer once given the filter name and then the layer name.
  * Example:
   ``` 
   new-project 2 2 
   set-filter red-component Layer1
   ```
9. **save-image**: Saves the image currently displayed on the canvas as a .ppm image. This removes
   the alpha component of every pixel present on the canvas.
  * Example:
   ``` 
   new-project 2 2 
   add-image-to-layer Layer1 ./res/smol.ppm 0 0
   set-filter red-component Layer1
   save-image redSmol
   ```




## How to use GUI View
* First, select either "New Project" or "Load Project" to either create a new project or load one, 
respectively.
  * For New Project, input the desired dimensions of the project when prompted
  * For Load Project, find the desired file to load through the file explorer
* On the top portion of the screen, you will see the current view on your project on the left, and 
the current layers in the project on the right.
* On the bottom portion of the screen, you will see all the available commands.
* To add a new layer, press the "Add Layer" button and input the desired name of the new layer
* To change between layers, simply press on the desired layer from the list
  * The highlighted button implies which layer is currently the active layer
* To add an image to a layer, select the layer you want to add the image to from the list. Then 
press the "Add Image to Current Layer" button, select the image from the file explorer, and input
the x and y coordinates you would like the top left of the image to reside at.
* To set a filter on the current layer, select the layer you would like to apply the filter to,
select the "Set Filter on Current Layer" button, and input one of the supported filters
* To create a new project, select the "New Project" button and input the height and width
parameters. This will overwrite any unsaved projects.
* To load a project, press the "Load Project" button. This will overwrite any unsaved projects.
* To save a project, press the "Save Project" button and enter the name you would like the project
saved as.
* To save the current project as an Image, press the "Save Image" button and enter the name you
would like to give the image. The image will be of what you currently see in the view window.
* To exit, select the "Exit" button or close out of the window. 
