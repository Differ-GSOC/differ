package cz.nkp.differ.compare.metadata;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author xrosecky
 */
@XmlRootElement(name="profile")
@XmlType(propOrder = {"id", "name", "kernel", "preccintSizes", "tileSizes", "decompositionLevels", "progressionOrders", "minQualityLayers", "maxQualityLayers"})
@XmlAccessorType(XmlAccessType.FIELD)
public class JP2Profile {
    
    @XmlElement(name = "id")
    private Long id;

    @XmlElement(name = "name")
    private String name;
    
    @XmlElement(name = "kernel")
    private JP2Kernel kernel;
    
    @XmlElementWrapper(name = "preccints")
    @XmlElement(name = "preccint")
    private List<JP2Size> preccintSizes;
    
    @XmlElementWrapper(name = "tiles")
    @XmlElement(name = "tile")
    private List<JP2Size> tileSizes;
    
    @XmlElementWrapper(name = "decomposition-levels")
    @XmlElement(name = "decomposition-level")
    private List<Integer> decompositionLevels;
    
    @XmlElementWrapper(name = "progression-orders")
    @XmlElement(name = "progression-order")
    private List<String> progressionOrders;
    
    @XmlElement(name = "min-quality-layers")
    private int minQualityLayers;
    
    @XmlElement(name = "max-quality-layers")
    private int maxQualityLayers;
    
    public JP2Profile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public JP2Kernel getKernel() {
        return kernel;
    }

    public void setKernel(JP2Kernel kernel) {
        this.kernel = kernel;
    }

    public List<JP2Size> getPreccintSizes() {
        return preccintSizes;
    }

    public void setPreccintSizes(List<JP2Size> preccintSizes) {
        this.preccintSizes = preccintSizes;
    }

    public List<JP2Size> getTileSizes() {
        return tileSizes;
    }

    public void setTileSizes(List<JP2Size> tileSizes) {
        this.tileSizes = tileSizes;
    }

    public List<Integer> getDecompositionLevels() {
        return decompositionLevels;
    }

    public void setDecompositionLevels(List<Integer> decompositionLevels) {
        this.decompositionLevels = decompositionLevels;
    }

    public List<String> getProgressionOrders() {
        return progressionOrders;
    }

    public void setProgressionOrders(List<String> progressionOrders) {
        this.progressionOrders = progressionOrders;
    }

    public int getMinQualityLayers() {
        return minQualityLayers;
    }

    public void setMinQualityLayers(int minQualityLayers) {
        this.minQualityLayers = minQualityLayers;
    }

    public int getMaxQualityLayers() {
        return maxQualityLayers;
    }

    public void setMaxQualityLayers(int maxQualityLayers) {
        this.maxQualityLayers = maxQualityLayers;
    }
    
}
