package cz.nkp.differ.compare.io;

import com.vaadin.ui.Button;
import cz.nkp.differ.compare.metadata.MetadataSource;

/**
 *
 * @author Thomas Truax
 */
public class ComparedImagesMetadata {
    
    //metadata table columns
    private String key;
    private Button source;
    private String[] values;
    private String unit;
    
    //non-column properties
    private String id; //used for internal identification only
    private MetadataSource metadataSource;
    private String sourceName;
    private String version;
    private boolean conflict;
    
    public ComparedImagesMetadata(String id) {
	this.id = id;
    }

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Button getSource() {
        return source;
    }
    
    public void setSource(Button source) {
        this.source = source;
    }

    public MetadataSource getMetadataSource() {
        return metadataSource;
    }
    
    public void setMetadataSource(MetadataSource metadataSource) {
        this.metadataSource = metadataSource;
    }

    public String[] getValues() {
        return this.values;
    }

    public void setValues(String[] values) {
        this.values = values;
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
    
    public String getSourceName() {
        return sourceName;
    }
    
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
}
