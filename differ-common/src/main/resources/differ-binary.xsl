<xsl:stylesheet
  version="1.0"
  xmlns:str="http://exslt.org/strings"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="xml" indent="yes" encoding="iso-8859-1"/>

  <xsl:template match="/opencv_storage">
    <properties>
      <xsl:for-each select="Algorithm">
        <xsl:variable name="algorithm" select="text()" />
        <xsl:variable name="result" select="normalize-space(//Algorithm[text()=$algorithm]/following::Values/text())" />
        <property name="red">
          <xsl:attribute name="source"><xsl:value-of select="$algorithm"/></xsl:attribute>
          <xsl:value-of select="str:tokenize($result, ' ')[1]"/>
        </property>
        <property name="green">
          <xsl:attribute name="source"><xsl:value-of select="$algorithm"/></xsl:attribute>
          <xsl:value-of select="str:tokenize($result, ' ')[2]"/>
        </property>
        <property name="blue">
          <xsl:attribute name="source"><xsl:value-of select="$algorithm"/></xsl:attribute>
          <xsl:value-of select="str:tokenize($result, ' ')[3]"/>
        </property>
      </xsl:for-each>
    </properties>
  </xsl:template>

  <xsl:template match="text()"/>

</xsl:stylesheet>
