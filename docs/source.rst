General Source Code Information
------------------------------------------------------------

Differ is split into three different packages. The differ-cmdline package is used to run differ in a command line environment with export in plaintext and drep (xml) format. The differ-webapp package creates a graphical web interface for the application. The differ-common package holds all classes and resources used by both applications to extract metadata and display it.

Each extractor tool is run with preset arguments and exported to XML. The application then transforms this data with regexp or xslt and maps it to defined properties.

Transformers of output from external programs 
......................

Main application context is 

	https://github.com/Differ-GSOC/differ/blob/master/differ-common/src/main/resources/appCtx-differ-common.xml	

Each specified output uses one of the transformers

	https://github.com/Differ-GSOC/differ/tree/master/differ-common/src/main/java/cz/nkp/differ/compare/metadata/external

Each tranformation has its own configuration

	https://github.com/Differ-GSOC/differ/tree/master/differ-common/src/main/resources

At the end of transformation, some values are normalized with these classes (defined in the configuration of each extractor)

	https://github.com/Differ-GSOC/differ/tree/master/differ-common/src/main/java/cz/nkp/differ/compare/metadata/external/result


Metadata
......................

Structure that hold all informations being extracted from all external image metadata extractors

	https://github.com/Differ-GSOC/differ/blob/master/differ-common/src/main/java/cz/nkp/differ/compare/io/ImageProcessorResult.java

All significant properties that are recognized

	https://github.com/Differ-GSOC/differ/blob/master/differ-common/src/main/resources/metadataSignificantProperties.xml

Data structure for metadata

	https://github.com/Differ-GSOC/differ/blob/master/differ-common/src/main/java/cz/nkp/differ/compare/metadata/ImageMetadata.java


Cmdline
......................

Main Class, takes arguments and sends request to extractors and generate report

	  https://github.com/Differ-GSOC/differ/blob/master/differ-cmdline/src/main/java/cz/nkp/differ/cmdline/Main.java

Text report output and various formating classes

	  https://github.com/Differ-GSOC/differ/tree/master/differ-cmdline/src/main/java/cz/nkp/differ/cmdline



Web application
......................

Main source for web application

   https://github.com/jstavel/differ/tree/master/differ-webapp/src/main/java/cz/nkp/differ



Documentation
......................

Documentation is written using Sphinx

   https://github.com/jstavel/differ/tree/master/docs

Examples of cmdline application outputs

   https://github.com/jstavel/differ/tree/master/docs/examples

A lot of files with outputs of used extractors

   https://github.com/jstavel/differ/tree/master/docs/outputs

Example scripts how the extractors were used

   https://github.com/jstavel/differ/tree/master/docs/bin
   

