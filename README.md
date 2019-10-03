To run the application use `sbt run`

The objective is to distort the left half of the image by randomizing the RGB component of each pixel by n-3 to n.
Where n is the integer representation for each RBG component of the pixel.

To test the logic. I am using a 2x2 pixel image to check the image distortion:
- comparing that the RGB component of left half is distorted by 3.
- the right half is unchanged