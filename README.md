# ImageProcessor
Our Processor uses a Project and a Layer class for the bulk of the processor
The layer data is a list of pixels in the layer class


In A5 we changed how we represent pixels and colors. In A4, pixels were represented by an
integer array of 4 elements. These elements represented the red, green, blue, and alpha 
components of a Pixel. While this design works, it became too unclear for us and we saw that
it could have limitations. With the Pixel interface/class, we can now much more easily
expand the functionality of our code.