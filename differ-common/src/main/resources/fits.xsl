<xsl:stylesheet
  version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:jhove="http://hul.harvard.edu/ois/xml/ns/jhove"
  xmlns:mix="http://www.loc.gov/mix/v20"
  xmlns:fits="http://hul.harvard.edu/ois/xml/ns/fits/fits_output"
  exclude-result-prefixes="jhove mix fits"
  >

  <xsl:output method="xml" indent="yes" encoding="iso-8859-1"/>

  <xsl:template match="text()"/>

  <xsl:template match="/fits:fits">
      <properties>
	<property name = "File last modified"><xsl:value-of select="fits:fileinfo/fits:lastmodified"/></property>
	<property name = "File name"><xsl:value-of select="fits:fileinfo/fits:filename"/></property>
	<property name = "File path"><xsl:value-of select="fits:fileinfo/fits:filepath"/></property>
	<property name = "File size"><xsl:value-of select="fits:fileinfo/fits:size"/></property>
	<property name = "Image width"><xsl:value-of select="fits:metadata/fits:image/fits:imageWidth"/></property>
	<property name = "Image height"><xsl:value-of select="fits:metadata/fits:image/fits:imageHeight"/></property>
	<property name= "Compression"><xsl:value-of select="fits:metadata/fits:image/fits:compressionScheme"/></property>
	<property name= "ICC profile"><xsl:value-of select="fits:metadata/fits:image/fits:iccProfileName"/></property>
	<property name= "ICC profile version"><xsl:value-of select="fits:metadata/fits:image/fits:iccProfileVersion"/></property>
	<xsl:apply-templates/>
      </properties>  
  </xsl:template>
  <xsl:template match="fits:identification">
    <property name="Type of format"><xsl:value-of select="fits:identity/@format"/></property>
    <property name="MIME type"><xsl:value-of select="fits:identity/@mimetype"/></property>
    <property name="Version"><xsl:value-of select="concat(fits:identity/@toolname,' ',fits:identity/@toolversion)"/></property>
  </xsl:template>
</xsl:stylesheet>
