Command Line Application
---------------------------------------------------------------------

Arguments of the application
.............................

The application can validate one image or compare two images.
 ========================= ===== ===================================================================================
 Argument                        Description   
 ========================= ===== ===================================================================================
 --save-report               -s  Save report into file with the same name and with extension drep
 --load-report               -l  Load report from a given file and print it in a readable form at stdout
 --include-image-in-report   
 --save-raw-outputs          -r  save raw outputs from validators into files
 --load-properties               program will print just properties that have value 1 in the file defined by this argument
 --save-properties               program will print out all known properties
 --send-report-to-web        -w  program will send report to web
 ========================= ===== ===================================================================================

Compare report contains both validation and comparision report.

The property list can be added to choose which properties should be printed

Example of property list definition
...................................

::

   Image width;1
   Image height;0
   


Property Image width will be printed and Image height will not be printed.

Examples
.........

Program usage
~~~~~~~~~~~~~
  
::

   validates image, prints readable report to stdout and saves it into file image.drep
   c:\> differ.bat --save-report image.jpg

   loads report from file image.drep and prints it to stdout
   c:\> differ.bat --load-report image.drep

   validates images and compares it and prints report to stdout 
   and save it into file image1.drep and image2.drep
   c:\> differ.bat --save-report image1.jpg image2.jpg

   validates images and compares it and prints report readable way to stdout 
   The program will print properties regarding to a file properties.lst
   c:\> differ.bat --load-properties properties.lst image1.jpg image2.jpg

   print properties in a format that can be used t choose the properties to be printed in future export
   c:\> differ.bat --show-properties


Example output: One image validation
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

See :download:`this example export <outputs/cmdline.txt>`.


Example output: Compare two images and validate
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

See :download:`this example export <outputs/cmdline-compare.txt>`.


Example output: PDF Export
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The application also exports a simple PDF using DynamicReports library.
See :download:`this example export <outputs/report.pdf>`.

.. note::

   You can see many more examples in the docs/ folder 

.. note::
   
   Developers and unix users can use the helpful bash scripts in the folder differ-cmdline/ to run the application. The application must be compiled with Maven to the jar file before these can be run. Validation in run.sh and compare in run-two.sh. Edit the file and comment out the lines you don't want.

