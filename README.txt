Xian Chen
383 HW2

This folder contains the code for the waterfall method with backtracking and arc consistency and consistency propagation. 

Also, the added inference method is "doublesCheck()", which is described:

If there are two cells in a row, column or box that can only take the same two values, then you know those two cells have those two values, even though you don't know which is which; therefore, those two values cannot be anywhere else in the same row, column or box. Similarly, if there are two values that can only be placed in the same two cells of a row, column or box, then those cells will have those two values; therefore, those cells cannot take any other values.