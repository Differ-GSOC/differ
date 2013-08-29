.. raw:: html

    <style> .red {color:red} </style>
    <style> .green {color:green} </style>
    <style> .yellow {color:yellow} </style>
.. role:: red
.. role:: green
.. role:: yellow

Tests of Metadata Extractors
............................

Tests are important to make sure that the properties from metadata extractors are properly mapped into the application.

The most significant tests are:

- Verifying that all properties from metadata extractor are handled in one way or another.
- Checking that the output value after transformation matches the raw extractor metadata.

Each extractor is contained to its own test application context.
	src/test/resources/${extractor}UnitTest.xml=

The context includes:

- File with raw output from metadata extractor to run through transformer 
- Significant properties list recognized manually for a given image
- Ignored properties that extractor recognizes but that are not considered significant
- Special properties that pass tests even thought they don't match exactly with output from other extractors (such as file size differences)

.. list-table:: List of tests run
   :header-rows: 1
   :widths: 10 10 10 10 10
   
   * - Metadata extractor
     - Image
     - File with recognized significant properties
     - File with raw output
     - Verified	
   
   * - jpylyzerMetadataExtractor
     - examples/images_01/14.jpf
     - resources/tests/image14-significant-properties.xml
     - examples/images_01/14/output-jpylyzer.raw
     - :green:`OK`

   * - jhoveMetadataExtractor
     - examples/images_01/14.jpf
     - resources/tests/image14-significant-properties.xml
     - examples/images_01/14/output-jhove.raw
     - :green:`OK`

   * - jhoveMetadataExtractor
     - examples/images_01/01.jpg
     - resources/tests/image01-significant-properties.xml
     - examples/images_01/01/output-jhove.raw
     - :green:`OK`
     
   * - kakaduMetadataExtractor
     - examples/images_01/14.jpf
     - resources/tests/image14-significant-properties.xml
     - examples/images_01/14/output-kakadu.raw
     - :green:`OK`

   * - imagemagickMetadataExtractor
     - examples/images_01/01.jpg
     - resources/tests/image01-significant-properties.xml
     - examples/images_01/01/output-imagemagick.raw
     - :green:`OK`

   * - imagemagickMetadataExtractor
     - examples/images_14/14.jpf
     - resources/tests/image01-significant-properties.xml
     - examples/images_01/14/output-imagemagick.raw
     - :green:`OK`

   * - djvudumpMetadataExtractor
     - docs/examples/images/05.djvu
     - resources/tests/image05-significant-properties.xml
     - docs/examples/images/05/output-djvudump.raw
     - :green:`OK`

   * - exiftoolMetadataExtractor
     - images/images_01/01.jpg
     - resources/tests/image01-significant-properties.xml
     - images/images_02/01/output-exiftool.raw
     - :green:`OK`

   * - exiftoolMetadataExtractor
     - examples/images_01/14.jpf
     - resources/tests/image14-significant-properties.xml
     - examples/images_01/14/output-exiftool.raw
     - :green:`OK`

To create a new test for a new image:

1) Go to the appropriate test class
2) Create a new method copying the previous test with @Test annotation
3) Import the image properties with @Resource (these are the properties from context file). Change in the method all imports to point to this file.
4) Create resources in appropriate test context file in /resources by copying previous test already in the context file
5) Change the names of the copied data to reflect the import done in 3) with @Resource
5a) If it's the first time running the test, add a line in test method to fetch context metadata before running the test: TestHelper.printTransformedMetadata(List<ResultTransformer.Entry> transformedData, LinkedHashMap signProperties); Add the output to appropiate context file as created in 3). 
6) Run JUnit tests. Inspect results and move properties to ignored if they should pass even though they don't match with other extractors metadata. 
     
