# Image Collager
## Description
This project allows the user to create a collage of images. The project supportes creating multiple layers and adding PPM images anywhere on a layer. A set list of supported filters are also available to be used on each layer. 

## Design
Our project class is the main class making up our model. This class implements the majority of the 
functionality required for our Image Processor, not including file operations. This class contains
a list of Layers, which make up the image representation of the project. This list is made up of 
Layer objects.

The layer class mainly consists of a 2D array of Pixels that represents the pixel makeup of the 
image on that layer. It also stores a version of the image with no filters added to it. It contains
a reference back to the project it is held in. 

## Command Line Configurations
To use with a premade script

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

## Changes

* In A5 we changed how we represent pixels and colors. In A4, pixels were represented by an
integer array of 4 elements. These elements represented the red, green, blue, and alpha
components of a Pixel. While this design works, it became too unclear for us and we saw that
it could have limitations. With the Pixel interface/class, we can now much more easily
expand the functionality of our code.

* The saveImage(), saveProject(), loadProject() methods were moved from the model to the controller.

* As a result of moving the saveImage(), saveProject(), and loadProject() methods from the model
  to the controller, the code of these methods was moved into the SaveImage, SaveProject, and
  LoadProject functional objects (respectively). These objects now have an additional method
  (LoadProject has load() while the other two have save()) that does the actual saving
  and loading. These methods are made public so that both the text-based controller and the
  GUI controller can invoke them and to prevent code duplication (since regardless of the
  controller, these methods are going to do the same thing).

* Added command line arguments that determine which controller/view get used. 
  "Text" will use the text-based view and controller, while "GUI" will use the GUI view 
  and controller. 

* The currentCanvas method in our model was changed from creating a String representation of our 
current project image to creating a 2D list of Pixels, representing the image of our current 
project. This was done both to ensure that string representations of our project were not being
created in the model, and so that creating a BufferedImage of our project would be much easier.

## Bug Fixes

* The loadProject() method now works properly
* The new-project and add-image-to-layer methods would throw an exception if given a double
  for their additional inputs. These commands now check to make sure that they are only given
  integers.
* The controller will respond accordingly when given bad input, thus not causing the program to end.

## Known Issues
* The Save Project method takes extremely long for larger projects. 

## Decoupling The View
* Our view is fully decoupled from the rest of the project. For the Text View (interface and implementation) you only need to include the model state interface. This interface provides functionality for the view to observe the current state of the model. For the GUI View (interface and implementation), you need to inlcude the same model state interface, as well as a controller features interface. This features interface allows the GUI to make changes to the model indirectly, asking the controller to do something not worrying about how it gets done. 

### Image Citation
* The Google Chrome logo used in our project was a png we converted to a P6 PPM file. We then 
converted that P6 PPM file to a P3 PPM file. The logo was used in accordance with Google's policy
for school projects found [here](https://about.google/brand-resource-center/guidance/#go-for-it).
We used the website [here](https://anyconv.com/png-to-ppm-converter/) to convert from a png to a
P6 PPM, then we used [this](https://github.com/thomasebsmith/ppm-converter) repository to convert
from P6 to P3. 
