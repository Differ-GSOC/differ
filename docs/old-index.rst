The Image Validator - DIFFER
============================

.. contents::
   :local:

.. toctree::
   :maxdepth: 2

Introduction
------------

The Image Validator helps an user with transformation of an image 
from one format to another one.

Sometimes it is necessary to convert an image from one format into another format. 
Some distorsions or error effects can occur when transforming of an image.

The Image Validator gives user the information whether a graphic information that 
an image carries was destroyed or not after the transformation.

The Image Validator collects significant properties from various image data extractors,
process them and shows them to a user so that he or she can easily compare two images and controls 
a tranformation process.

.. include:: introduction.rst

Links related to the project
............................

.. list-table::  
   :header-rows: 0

   * - project webpage
     - http://differ.nkp.czdiffer/
   * - project webpage BETA
     - http://differ.nkp.cz:8080/differ/ (BETA)
   * - project source code
     - http://github.com/jstavel/differ
   * - presentation
     - http://www.youtube.com/watch?v=2u0MxhOZ5h8
   * - presentation Lighting talk at GSOC2012
     - https://docs.google.com/folder/d/0B-c5jwGzSQyPOC1JWjhnXzZKSXM/edit?docId=0Bw7eDY6ebeEdcGdyc2luSXNsTkE
   * - project poster
     - https://docs.google.com/file/d/0B9Ah7Og9gY_ORi1kandLZVJ2NEU/edit?usp=sharing
   * - last year project’s topics GSOC 2012
     - https://github.com/moravianlibrary/differ/wiki/GSoC

Important parts of a project
----------------------------

.. include:: important_parts.rst

Recognized Extractor Outputs
----------------------------

.. include:: exiftool.rst

.. include:: exiv2.rst

.. include:: jhove.rst

.. include:: imagemagick.rst

.. include:: fits.rst

.. include:: kdu.rst

.. include:: jpylyzer.rst

.. include:: daitss.rst

.. include:: djvudump.rst

Installation
------------

.. include:: installation.rst

Command Line Application
------------------------

.. include:: cmdline.rst

Examples of validation results
------------------------------

.. include:: examples.rst

Tests
-----

.. include:: tests.rst
