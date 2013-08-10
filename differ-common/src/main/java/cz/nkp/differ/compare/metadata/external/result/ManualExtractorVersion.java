package cz.nkp.differ.compare.metadata.external.result;

import cz.nkp.differ.compare.metadata.external.ResultTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Sets manual value for extractor whenever the information is missing from raw output
 * @author Jonatan Svensson
 * @version 09/08/2013
 */
public class ManualExtractorVersion implements MetadataResultTransformer {
    private String version;

    public List<ResultTransformer.Entry> transform(List<ResultTransformer.Entry> metadataList){
        ResultTransformer.Entry e = new ResultTransformer.Entry();
        e.setKey("Version of Extractor");
        e.setValue(version);
        metadataList.add(e);
        return metadataList;
    }

    public void setVersion(String value){
        version=value;
    }
    public String getVersion(){
        return version;
    }
}
