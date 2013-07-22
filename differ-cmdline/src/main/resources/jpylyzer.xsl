<xsl:stylesheet
  version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:jhove="http://hul.harvard.edu/ois/xml/ns/jhove"
  xmlns:mix="http://www.loc.gov/mix/v20"
  exclude-result-prefixes="jhove mix"
  >

  <xsl:output method="xml" indent="yes" encoding="iso-8859-1"/>

  <xsl:template match="text()"/>

  <xsl:template match="/jpylyzer">
      <properties>
	<property name="Version"><xsl:value-of select="concat(toolInfo/toolName,' ',toolInfo/toolVersion)"/></property>
	<property name="Validation (well formed and valid)"><xsl:value-of select="isValidJP2"/></property>
	
	<property name="Contains signature box"><xsl:value-of select="tests/containsSignatureBox"/></property>
	<property name="Contains file type box"><xsl:value-of select="tests/containsFileTypeBox"/></property>
	<property name="Contains JP2 header box"><xsl:value-of select="tests/containsJP2HeaderBox"/></property>
	<property name="Contains contiguous codestream box"><xsl:value-of select="tests/containsContiguousCodestreamBox"/></property>
	<property name="First box is signature box"><xsl:value-of select="tests/firstBoxIsSignatureBox"/></property>
	<property name="Second box is file type box"><xsl:value-of select="tests/secondBoxIsFileTypeBox"/></property>
	<property name="Location JP2 header box is valid"><xsl:value-of select="tests/locationJP2HeaderBoxIsValid"/></property>
	<property name="No more than one signature box"><xsl:value-of select="tests/noMoreThanOneSignatureBox"/></property>
	<property name="No more than one file type box"><xsl:value-of select="tests/noMoreThanOneFileTypeBox"/></property>
	<property name="No more than one JP2 header box"><xsl:value-of select="tests/noMoreThanOneJP2HeaderBox"/></property>
	<property name="Height consistent with SIZ"><xsl:value-of select="tests/heightConsistentWithSIZ"/></property>
	<property name="Width consistent with SIZ"><xsl:value-of select="tests/widthConsistentWithSIZ"/></property>
	<property name="NC consistent with SIZ"><xsl:value-of select="tests/nCConsistentWithSIZ"/></property>
	<property name="bPC sign consistent with SIZ"><xsl:value-of select="tests/bPCSignConsistentWithSIZ"/></property>
	<property name="bPC depth consistent with SIZ"><xsl:value-of select="tests/bPCDepthConsistentWithSIZ"/></property>
	<xsl:apply-templates/>
      </properties>  
  </xsl:template>

  <xsl:template match="fileInfo">
    <property name = "File last modified"><xsl:value-of select="fileLastModified"/></property>
    <property name = "File name"><xsl:value-of select="fileName"/></property>
    <property name = "File path"><xsl:value-of select="filePath"/></property>
    <property name = "File size"><xsl:value-of select="fileSizeInBytes"/></property>
    <xsl:apply-templates/>
  </xsl:template>

  <xsl:template match="tests/signatureBox">
    <property name="Signature box length is valid"><xsl:value-of select="boxLengthIsValid"/></property>
    <property name="Signature is valid"><xsl:value-of select="signatureIsValid"/></property>
  </xsl:template>

  <xsl:template match="tests/fileTypeBox">
    <property name="Box length is valid"><xsl:value-of select="boxLengthIsValid"/></property>
    <property name="Brand is valid"><xsl:value-of select="brandIsValid"/></property>
    <property name="Minor version is valid"><xsl:value-of select="minorVersionIsValid"/></property>
    <property name="Compatibility list is valid"><xsl:value-of select="compatibilityListIsValid"/></property>
  </xsl:template>

  <xsl:template match="tests/jp2HeaderBox">
    <property name="Image header box length is valid"><xsl:value-of select="imageHeaderBox/boxLengthIsValid"/></property>
    <property name="Image header box height is valid"><xsl:value-of select="imageHeaderBox/heightIsValid"/></property>
    <property name="Image header box width is valid"><xsl:value-of select="imageHeaderBox/widthIsValid"/></property>
    <property name="nC is valid"><xsl:value-of select="imageHeaderBox/nCIsValid"/></property>
    <property name="bPC is valid"><xsl:value-of select="imageHeaderBox/bPCIsValid"/></property>
    <property name="C is valid"><xsl:value-of select="imageHeaderBox/cIsValid"/></property>
    <property name="unkC is valid"><xsl:value-of select="imageHeaderBox/unkCIsValid"/></property>
    <property name="iPR is valid"><xsl:value-of select="imageHeaderBox/iPRIsValid"/></property>
    <property name="Meth is valid"><xsl:value-of select="colourSpecificationBox/methIsValid"/></property>
    <property name="Prec is valid"><xsl:value-of select="colourSpecificationBox/precIsValid"/></property>
    <property name="Approximation is valid"><xsl:value-of select="colourSpecificationBox/approxIsValid"/></property>
    <property name="Enum CS is valid"><xsl:value-of select="colourSpecificationBox/enumCSIsValid"/></property>
    <property name="Capture resolution box length is valid"><xsl:value-of select="resolutionBox/captureResolutionBox/boxLengthIsValid"/></property>
    <property name="vRcN is valid"><xsl:value-of select="resolutionBox/captureResolutionBox/vRcNIsValid"/></property>
    <property name="vRcD is valid"><xsl:value-of select="resolutionBox/captureResolutionBox/vRcDIsValid"/></property>
    <property name="hRcN is valid"><xsl:value-of select="resolutionBox/captureResolutionBox/hRcNIsValid"/></property>
    <property name="hRcD is valid"><xsl:value-of select="resolutionBox/captureResolutionBox/hRcDIsValid"/></property>
    <property name="vRcE is valid"><xsl:value-of select="resolutionBox/captureResolutionBox/vRcEIsValid"/></property>
    <property name="hRcE is valid"><xsl:value-of select="resolutionBox/captureResolutionBox/hRcEIsValid"/></property>
    <property name="Contains capture or display resolution box"><xsl:value-of select="resolutionBox/containsCaptureOrDisplayResolutionBox"/></property>
    <property name="No more than one capture resolution box"><xsl:value-of select="resolutionBox/noMoreThanOneCaptureResolutionBox"/></property>
    <property name="No more than one display resolution box"><xsl:value-of select="resolutionBox/noMoreThanOneDisplayResolutionBox"/></property>
    <property name="Contains image header box" ><xsl:value-of select="containsImageHeaderBox"/></property>
    <property name="Contains colour specification box" ><xsl:value-of select="containsColourSpecificationBox"/></property>
    <property name="First jp2 header box is image header box" ><xsl:value-of select="firstJP2HeaderBoxIsImageHeaderBox"/></property>
    <property name="No more than one image header box" ><xsl:value-of select="noMoreThanOneImageHeaderBox"/></property>
    <property name="No more than one bits per component box" ><xsl:value-of select="noMoreThanOneBitsPerComponentBox"/></property>
    <property name="No more than one palette box" ><xsl:value-of select="noMoreThanOnePaletteBox"/></property>
    <property name="No more than one component mapping box" ><xsl:value-of select="noMoreThanOneComponentMappingBox"/></property>
    <property name="No more than one channel definition box" ><xsl:value-of select="noMoreThanOneChannelDefinitionBox"/></property>
    <property name="No more than one resolution box" ><xsl:value-of select="noMoreThanOneResolutionBox"/></property>
    <property name="Colour specification boxes are contiguous" ><xsl:value-of select="colourSpecificationBoxesAreContiguous"/></property>
    <property name="Palette and component mapping boxes only together" ><xsl:value-of select="paletteAndComponentMappingBoxesOnlyTogether"/></property>
  </xsl:template>

  <xsl:template match="tests/uuidBox">
    <property name="Box length is valid" ><xsl:value-of select="boxLengthIsValid"/></property>
  </xsl:template>

  <xsl:template match="tests/contiguousCodestreamBox">
    <property name="Codestream start with SOC marker" ><xsl:value-of select="codestreamStartsWithSOCMarker"/></property>
    <property name="Found SIZ marker" ><xsl:value-of select="foundSIZMarker"/></property>
    <property name="l size is valid"><xsl:value-of select="siz/lsizIsValid"/></property>
    <property name="r size is valid"><xsl:value-of select="siz/rsizIsValid"/></property>
    <property name="x size is valid"><xsl:value-of select="siz/xsizIsValid"/></property>
    <property name="y size is valid"><xsl:value-of select="siz/ysizIsValid"/></property>
    <property name="xO size is valid"><xsl:value-of select="siz/xOsizIsValid"/></property>
    <property name="yO size is valid"><xsl:value-of select="siz/yOsizIsValid"/></property>
    <property name="xT size is valid"><xsl:value-of select="siz/xTsizIsValid"/></property>
    <property name="yT size is valid"><xsl:value-of select="siz/yTsizIsValid"/></property>
    <property name="xTO size is valid"><xsl:value-of select="siz/xTOsizIsValid"/></property>
    <property name="yTO size is valid"><xsl:value-of select="siz/yTOsizIsValid"/></property>
    <property name="c size is valid"><xsl:value-of select="siz/csizIsValid"/></property>
    <property name="l size consistency with c size"><xsl:value-of select="siz/lsizConsistentWithCsiz"/></property>
    <property name="s size is valid"><xsl:value-of select="siz/ssizIsValid"/></property>
    <property name="xR size is valid"><xsl:value-of select="siz/xRsizIsValid"/></property>
    <property name="yR size is valid"><xsl:value-of select="siz/yRsizIsValid"/></property>
    <property name="s size is valid"><xsl:value-of select="siz/ssizIsValid"/></property>
    <property name="l cod is valid"><xsl:value-of select="cod/lcodIsValid"/></property>
    <property name="cod order is valid"><xsl:value-of select="cod/orderIsValid"/></property>
    <property name="Cod layers is valid"><xsl:value-of select="cod/layersIsValid"/></property>
    <property name="Multiple component transformation is valid"><xsl:value-of select="cod/multipleComponentTransformationIsValid"/></property>
    <property name="Cod levels is valid"><xsl:value-of select="cod/levelsIsValid"/></property>
    <property name="l cod consistent with levels precincts"><xsl:value-of select="cod/lcodConsistentWithLevelsPrecincts"/></property>
    <property name="Code block width exponent is valid"><xsl:value-of select="cod/codeBlockWidthExponentIsValid"/></property>
    <property name="Code block height exponent is valid"><xsl:value-of select="cod/codeBlockHeightExponentIsValid"/></property>
    <property name="Sum height width exponent is valid"><xsl:value-of select="cod/sumHeightWidthExponentIsValid"/></property>
    <property name="Cod transformation is valid"><xsl:value-of select="cod/transformationIsValid"/></property>
    <property name="l qcd is valid"><xsl:value-of select="qcd/lqcdIsValid"/></property>
    <property name="q style is valid"><xsl:value-of select="qcd/qStyleIsValid"/></property>
    <property name="Found COD marker"><xsl:value-of select="foundCODMarker"/></property>
    <property name="Found QCD marker"><xsl:value-of select="foundQCDMarker"/></property>
    <property name="Quantization consistent with levels"><xsl:value-of select="quantizationConsistentWithLevels"/></property>
    <property name="Found expected number of tiles"><xsl:value-of select="foundExpectedNumberOfTiles"/></property>
    <property name="Found expected number of tile parts"><xsl:value-of select="foundExpectedNumberOfTileParts"/></property>
    <property name="l sot is valid"><xsl:value-of select="tileParts/tilePart/sot/lsotIsValid"/></property>
    <property name="i sot is valid"><xsl:value-of select="tileParts/tilePart/sot/isotIsValid"/></property>
    <property name="p sot is valid"><xsl:value-of select="tileParts/tilePart/sot/psotIsValid"/></property>
    <property name="tp sot is valid"><xsl:value-of select="tileParts/tilePart/sot/tpsotIsValid"/></property>
    <property name="Found SOD marker"><xsl:value-of select="tileParts/tilePart/foundSODMarker"/></property>
    <property name="Fount next tile part or EOC"><xsl:value-of select="tileParts/tilePart/foundNextTilePartOrEOC"/></property>
    <property name="Found EOC marker"><xsl:value-of select="foundEOCMarker"/></property>
  </xsl:template>

  <xsl:template match="properties">
    <property name="Image width"><xsl:value-of select="jp2HeaderBox/imageHeaderBox/width"/></property>
    <property name="Image height"><xsl:value-of select="jp2HeaderBox/imageHeaderBox/height"/></property>
    <property name="Color depth"><xsl:value-of select="contiguousCodestreamBox/siz/ssizDepth"/></property>
    <property name="Number of channels"><xsl:value-of select="jp2HeaderBox/imageHeaderBox/nC"/></property>
    <property name="Color space"><xsl:value-of select="jp2HeaderBox/colourSpecificationBox/enumCS"/></property>
    <property name="Resolution vertical"><xsl:value-of select="jp2HeaderBox/resolutionBox/displayResolutionBox/vResdInPixelsPerInch"/></property>
    <property name="Resolution horizontal"><xsl:value-of select="jp2HeaderBox/resolutionBox/displayResolutionBox/hResdInPixelsPerInch"/></property>
    <property name="Resolution vertical"><xsl:value-of select="jp2HeaderBox/resolutionBox/captureResolutionBox/vRcN"/></property>
    <property name="Resolution horizontal"><xsl:value-of select="jp2HeaderBox/resolutionBox/captureResolutionBox/hRcN"/></property>
    <property name="Display resolution horizontal"><xsl:value-of select="jp2HeaderBox/resolutionBox/displayResolutionBox/hResdInPixelsPerInch"/></property>
    <property name="Display resolution vertical"><xsl:value-of select="jp2HeaderBox/resolutionBox/displayResolutionBox/vResdInPixelsPerInch"/></property>
    <property name="Type of format"><xsl:value-of select="jp2HeaderBox/imageHeaderBox/c"/></property>
    <property name="Universal unique identifier (UUID)"><xsl:value-of select="uuidBox/uuid"/></property>
    <property name="Commentary"><xsl:value-of select="contiguousCodestreamBox/com/comment"/></property>
    <property name="Number of tiles"><xsl:value-of select="contiguousCodestreamBox/siz/numberOfTiles"/></property>
    <property name="Transformation"><xsl:value-of select="contiguousCodestreamBox/cod/transformation"/></property>
    <property name="Compression"><xsl:value-of select="contiguousCodestreamBox/cod/transformation"/></property>
    <property name="Compression ratio"><xsl:value-of select="compressionRatio"/></property>
    <property name="Number of decomposition levels"><xsl:value-of select="contiguousCodestreamBox/cod/levels"/></property>
    <property name="Number of quality layers"><xsl:value-of select="contiguousCodestreamBox/cod/layers"/></property>
    <property name="Progression order"><xsl:value-of select="contiguousCodestreamBox/cod/order"/></property>
    <property name="Code block width"><xsl:value-of select="contiguousCodestreamBox/cod/codeBlockWidth"/></property>
    <property name="Code block height"><xsl:value-of select="contiguousCodestreamBox/cod/codeBlockHeight"/></property>
    <property name="Coding bypass"><xsl:value-of select="contiguousCodestreamBox/cod/codingBypass"/></property>
    <property name="Start of packet header"><xsl:value-of select="contiguousCodestreamBox/cod/sop"/></property>
    <property name="End of packet header"><xsl:value-of select="contiguousCodestreamBox/cod/eph"/></property>
    <property name="Precincts"><xsl:value-of select="contiguousCodestreamBox/cod/precincts"/></property>
    <xsl:apply-templates/>
  </xsl:template>

</xsl:stylesheet>
