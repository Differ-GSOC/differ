<xsl:stylesheet
  version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:jhove="http://hul.harvard.edu/ois/xml/ns/jhove"
  xmlns:mix="http://www.loc.gov/mix/v20"
  xmlns:fits="http://hul.harvard.edu/ois/xml/ns/fits/fits_output"
  xmlns:premis="info:lc/xmlns/premis-v2"
  xmlns:str="http://exslt.org/strings"
  exclude-result-prefixes="jhove mix fits premis str"
  >

  <xsl:output method="xml" indent="yes" encoding="iso-8859-1"/>

  <xsl:template match="text()"/>

  <xsl:template match="/premis:premis">
      <properties>
	<property name = "File name"><xsl:value-of select="premis:object/premis:originalName"/></property>
	<property name="Validation (well formed and valid)"><xsl:value-of select="premis:event[premis:eventType='describe']/premis:eventDetail"/></property>
	<xsl:apply-templates/>
      </properties>
  </xsl:template>
  
  <xsl:template match="premis:agent">
    <property name="Version of Extractor"><xsl:value-of select="premis:agentIdentifier/premis:agentIdentifierValue"/></property>
    <xsl:apply-templates/>
  </xsl:template>

  <xsl:template match="premis:object/premis:objectCharacteristics">
    <property name = "Universal unique identifier (UUID)"><xsl:value-of select="premis:fixity/premis:messageDigest"/></property>
    <property name = "File size"><xsl:value-of select="premis:size"/></property>
    <property name = "Type of format"><xsl:value-of select="premis:format/premis:formatDesignation/premis:formatName"/></property>    
    <property name = "Version of format"><xsl:value-of select="premis:format/premis:formatDesignation/premis:formatVersion"/></property>    
    <xsl:apply-templates/>
  </xsl:template>

  <xsl:template match="premis:objectCharacteristicsExtension/mix:mix">
    <property name = "Compression"><xsl:value-of select="mix:BasicDigitalObjectInformation/mix:Compression/mix:compressionScheme"/></property>
    <property name = "Image width"><xsl:value-of select="mix:BasicImageInformation/mix:BasicImageCharacteristics/mix:imageWidth"/></property>
    <property name = "Image height"><xsl:value-of select="mix:BasicImageInformation/mix:BasicImageCharacteristics/mix:imageHeight"/></property>
    <property name = "Image height"><xsl:value-of select="mix:BasicImageInformation/mix:BasicImageCharacteristics/mix:imageHeight"/></property>
    <property name = "Color space"><xsl:value-of select="mix:BasicImageInformation/mix:BasicImageCharacteristics/mix:PhotometricInterpretation/mix:colorSpace"/></property>
    <property name = "Resolution horizontal"><xsl:value-of select="mix:ImageAssessmentMetadata/mix:SpatialMetrics/mix:xSamplingFrequency/mix:numerator"/></property>
    <property name = "Resolution vertical"><xsl:value-of select="mix:ImageAssessmentMetadata/mix:SpatialMetrics/mix:ySamplingFrequency/mix:numerator"/></property>
    <property name = "Color depth"><xsl:value-of select="mix:ImageAssessmentMetadata/mix:ImageColorEncoding/mix:BitsPerSample/mix:bitsPerSampleValue"/></property>
    <property name = "Samples per pixel"><xsl:value-of select="mix:ImageAssessmentMetadata/mix:ImageColorEncoding/mix:samplesPerPixel"/></property>
  </xsl:template>

</xsl:stylesheet>
