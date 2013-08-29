Introduction
------------------

New project was born
....................

The project ``The Image Validator`` is a successor of the project ``Differ``.
``Differ`` is short for ``Determinator of Image File Format properties``.

The project ``Differ`` was written in ``C++`` and ``PHP`` and it allowed to get
main properties for images in various image formats.

The main purpose of creating a succesor was the need to have a program running
on a various platforms. That is why new project ``The Image Validator`` is written in ``Java``.


About
..............

The Image Validator helps an user with transformation of an image 
from one format to another one.

Sometimes it is necessary to convert an image from one format into another format. 
Some distorsions, glitches or side effects can occur when transforming an image.

The Image Validator gives the user information whether the graphic information that 
an image carries was destroyed or not after the transformation.

The Image Validator also collects significant properties from various image data extractors,
processes them and shows them to a user so that he or she can easily compare two images and control 
the tranformation process.



The Image Validator Procedure
.............................

Input data
~~~~~~~~~~

One or two graphic images is the input of the program.

The program recognizes various graphic formats:

- ``tiff``
- ``jpeg``
- ``png``
- ``jpeg2000``
- ``djvu``
- ``pdf``


Identification
~~~~~~~~~~~~~~

The program identifies a format of an image by an extension used in a file name:

- ``tiff``: `*.tiff`, `*.tif`
- ``djvu``: `*.djvu`
- ``jpeg 2000``: `*.jp2000`, `*.jpf`, `*.jp2`, `*.jpp`
- ``jpeg``: `*.jpeg`, `*.jpg`
- ``pdf``: `*.pdf`

.. note::
   
   Users can add more extensions into the file ``differ-cmdline/src/main/resources/appCtx-differ-cmdline.xml`` and into the corresponding xml file 
   ``exiftoolMetadataExtractor.xml`` 
   or ``exiv2MetadataExtractor.xml`` 
   or ``fitsMetadataExtractor.xml``
   or ``imagemagickMetadataExtractor.xml``
   or ``jhoveMetadataExtractor.xml``
   or ``jpylyzerMetadataExtractor.xml``
   or ``kakaduMetadataExtractor.xml``

   The xml tag ``supportedFileExtensions`` holds information about which file name extensions the given metadata extractor recognizes.

.. important::

   Users must call ``mvn package`` in the directory ``differ-cmdline`` everytime he or she changes xml files.


Format
~~~~~~

The program can work with images in format:

- ``TIFF``
- ``JPEG``
- ``DjVu``
- ``JPEG 2000``
- ``PDF``

Validation and Characterization
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The program collects the information calling various image metadata extractors.

A type of the information can be:

- identification::

    the information tells basic information about image file or
    tells basic structural information about image format.

- characterization::

    the information describes image in detail.

- validation::

    the information tells that image format is valid and well formed.

The program calls various external programs to extract image metadata depending on image format:

- ``TIFF``

  - ``Exiftool``
  - ``Exiv2``
  - ``Fits``
  - ``Imagemagick``
  - ``Jhove``

- ``JPEG``
  
  - ``Exiftool``
  - ``Exiv2``
  - ``Fits``
  - ``Imagemagick``
  - ``Jhove``
  - ``Daitss``
    

- ``DjVu``
  
  - ``Exiftool``
  - ``Exiv2``
  - ``Fits``
  - ``Imagemagick``
  - ``Jhove``
  - ``Daitss``

- ``JPEG 2000``

  - ``Exiftool``
  - ``Exiv2``
  - ``Fits``
  - ``Imagemagick``
  - ``Jhove``
  - ``Daitss``
  - ``JPylyzer``
  - ``KDU_expand``


.. note:: Detailed information about each external program can be seen in `Recognized Extractor Outputs`_.
