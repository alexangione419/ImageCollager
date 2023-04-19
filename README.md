# Image Collager
## Description
This program allows you to create a collage of images and allows you to add filters
to each of the layers in the project. The program supports ppm, png, and jpg/jpeg file.
The program can be run via the terminal (see the USEME) or via our user interface window;
both versions of the program have the same features. 

For a more in-depth view on how to use the program and valid commands, please see the USEME file.

### Features

1. Saving and loading projects
2. Saving projects as images
3. Loading image files
4. Adding and naming layers
5. Applying filters (see USEME for list of supported filters) to layers


## Dependencies

To run our program, you'll need the following:
1. Java 11 or higher JRE 
2. JUnit 4 for running the tests



## Design
Our project class is the main class making up our model. This class implements the majority of the 
functionality required for our Image Processor, not including file operations. This class contains
a list of Layers, which make up the image representation of the project. This list is made up of 
Layer objects.

The layer class mainly consists of a 2D array of Pixels that represents the pixel makeup of the 
image on that layer. It also stores a version of the image with no filters added to it. It contains
a reference back to the project it is held in. 



## Changes
### From A4 to A5
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
### From A5 to A6
* We did not have to alter our existing design in order to implement the features asked for in A6. 
Since the ppm format is not an accepted format by the java ImageIO class, we kept our 
logic for adding and saving ppm images and added new logic for the formats jpeg, png, and jpg.

## Bug Fixes
* The loadProject() method now works properly
* The new-project and add-image-to-layer methods would throw an exception if given a double
  for their additional inputs. These commands now check to make sure that they are only given
  integers.
* The controller will respond accordingly when given bad input, thus not causing the program to end.

## Known Issues
* The Save Project method takes extremely long for larger projects.

### Image Citation
* The Google Chrome logo used in our project was a png we converted to a P6 PPM file. We then 
converted that P6 PPM file to a P3 PPM file. The logo was used in accordance with Google's policy
for school projects found [here](https://about.google/brand-resource-center/guidance/#go-for-it).
We used the website [here](https://anyconv.com/png-to-ppm-converter/) to convert from a png to a
P6 PPM, then we used [this](https://github.com/thomasebsmith/ppm-converter) repository to convert
from P6 to P3. 
