package cz.nkp.differ.compare.metadata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author xrosecky
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"key", "value", "unit", "conflict", "source"})
public class ImageMetadata {

    private String key;
    private Object value;
    private String unit;
    private boolean conflict;
    @XmlIDREF
    private MetadataSource source;
    @XmlTransient
    private Object data;

    public ImageMetadata() {
	
    }
    
    public ImageMetadata(String key, Object value, MetadataSource source) {
        this.key = key;
        this.value = value;
        this.source = source;
    }
    
    public ImageMetadata(String key, Object value, MetadataSource source, String unit) {
        this(key, value, source);
        this.unit = unit;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public MetadataSource getSource() {
        return source;
    }

    public void setSource(MetadataSource source) {
        this.source = source;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean isConflict() {
        return conflict;
    }

    public void setConflict(boolean conflict) {
        this.conflict = conflict;
    }

    public Object getData() {
	return data;
    }

    public void setData(Object data) {
	this.data = data;
    }
    
}
