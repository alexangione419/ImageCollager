## Decoupling The View
* Our view is fully decoupled from the rest of the project. For the Text View (interface and
implementation) you only need to include the model state interface. This interface provides 
functionality for the view to observe the current state of the model. For the GUI View 
(interface and implementation), you need to includes the same model state interface, as well 
as a controller features interface. This features interface allows the GUI to make changes to 
the model indirectly, asking the controller to do something not worrying about how it gets done.


## Decoupling the Controller
* For our controller, in order to compile, it requires a few things from within its own controller
package
  * We need the controller Interface and Implementation for both our Text and Gui, 2 separate sets
    of files
  * We need the features interface that allows the Gui to talk to the controller
  * We need the Command interface and all the implementations of that. This is a part of the command
    design pattern, keeping our controller cluttered.
  * An enumeration containing all the valid file extension types.
  * A file utility class so that the model does not have to interact with files, only the controller
* We needed to add the following things from our model
  * ImageProject and ImageProjectState, two interfaces from our model that essentially 
    differentiate the amount of methods the created object is able to access in the place it is
    created.
  * The Filter, Layer, and Pixel interfaces were needed. This was only because our ImageProject 
    interface has methods that return/take in these types, so this was needed simply to compile the 
    code. There was only one instance of an implementation of these classes actually being used in
    the controller code, but it was very quickly and easily removed.
* We needed to add the following things from our view
  * The two interfaces for the text and gui view

