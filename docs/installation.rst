Developers and users must install all extractor tools on their computer. The application does not come pre-packaged yet.


Exiftool
........

`home page <http://www.sno.phy.queensu.ca/~phil/exiftool/>`_

http://www.stahuj.centrum.cz/utility_a_ostatni/ostatni/exiftool/

::
   
   # for debian linux
   root@treebeard:~# aptitude install libimage-exiftool-perl 


set in file ``differ-common/common.properties``
a path to exiftool binary.

It depends on operating system.

::

   exiftool.binary=/usr/bin/exiftool
   
Exiv2
.....

`home page <http://www.exiv2.org/>`_

`download <http://www.exiv2.org/download.html>`_

::

   # for debian linux
   root@treebeard:~# aptitude install exiv2 
   

set in file ``differ-common/common.properties``
a path to exiv2 binary.

It depends on operating system.

::

   exiv2.binary=/usr/bin/exiv2


JHOVE
.....

`home page <http://jhove.sourceforge.net>`_

`download page <http://http://jhove.sourceforge.net/distribution.html>`_

::

   # for debian linux
   root@treebeard:~# aptitude install jhove


set in file ``differ-common/common.properties``
a path to jhove script. 
::

	#!/bin/bash
	eval "exec jhove -h xml $1 | grep -v "READBOX""


::

   jhove.binary=/lib/jhove/jhove.sh


Imagemagick
...........


`home page <http://www.imagemagick.org/script/index.php>`_

`download page <http://www.imagemagick.org/script/binary-releases.php>`_

::

   # for debian linux
   root@treebeard:~# aptitude install imagemagick


set in file ``differ-common/common.properties``
a path to imagemagick binary.

It depends on operating system.

::

   imagemagic.binary=/usr/bin/identify

FITS
....

`home page <http://code.google.com/p/fits/>`_
`download page <http://code.google.com/p/fits/downloads/list>`_


::
   
   stavel@treebeard:~/lib$ wget http://fits.googlecode.com/files/fits-0.6.1.zip
   stavel@treebeard:~/lib$ unzip fits-0.6.1.zip 
   stavel@treebeard:~/lib$ chmod 755 fits-0.6.1/fits.sh 


set in file ``differ-common/common.properties``
a path to fits script.

::

   fits.binary=/home/stavel/lib/fits-0.6.1/fits.sh

It depends on operating system.

There is necessary to fix incorrect string that invalidates xml output.

Please add into fits.sh something like:

:: 

   # for windows:
   java edu.harvard.hul.ois.fits.Fits %* | find /V "READBOX"

   # for linux:
   eval "exec $cmd" | grep -v "READBOX"


Kakadu
......

This library is necessary to handle jp2000 images.

`home page <http://www.kakadusoftware.com/>`_

Download binaries from their `download page <http://www.kakadusoftware.com/index.php?option=com_content&task=view&id=26&Itemid=22>`_

Do not forget to set in file ``differ-common/common.properties`` a path to kakadu binary.

To identify and load images ``kdu_expand`` binary is used.

::

   kakadu.binary=/home/stavel/bin/kdu_expand

JPylyzer
........

`JPylyzer home page <http://www.openplanetsfoundation.org/software/jpylyzer>`_

Download binary installation from home page. Install it as usual in your operating system.
:: 

   # for Ubuntu:
   sudo apt-get install jpylyzer


In order for scripts to run ensure that `xmlstarlet is also installed <http://xmlstar.sourceforge.net/>`_.

:: 

   # for Ubuntu:
   sudo apt-get install xmlstarlet

Daitss
.......

`Daitss Home Page <http://daitss.fcla.edu/>`_

You can use daitss as a web service or as local program.

See `Software Installation Guide <https://daitss.pubwiki.fcla.edu/wiki/index.php/DAITSS_2_Software_Installation_Guide>`_.

See `Getting started <https://share.fcla.edu/FDAPublic/DAITSS/Chapter_2_Getting_Started.pdf>`_.

set in file ``differ-common/common.properties``
an url for daitss web service.

It can be local or external.

::

   daitssHTTPExtractor.url=http://description.fcla.edu/description


.. important::

   En external web service is slow and it accepts files just smaller than 40MB.

   It is better to install local web service for huge amount of files to process.


DJVuDump
........

http://www.djvuzone.org/support/tutorial/index.html

http://djvu.sourceforge.net/doc/index.html

::

   # for debian linux
   root@treebeard:~# aptitude install djvulibre-bin
