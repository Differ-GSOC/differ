package cz.nkp.differ.profile;

import cz.nkp.differ.compare.metadata.JP2Kernel;
import cz.nkp.differ.compare.metadata.JP2Size;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author xrosecky
 */
public class JP2FixedTemplate implements JP2Template {

    @Override
    public List<JP2Kernel> getKernels() {
        return Arrays.asList(JP2Kernel.Irreversible9x7, JP2Kernel.Revesible5x3);
    }

    @Override
    public List<JP2Size> getPreccintSizes() {
        return Arrays.asList(new JP2Size(64, 64), new JP2Size(128, 128), new JP2Size(256, 256));
    }

    @Override
    public List<JP2Size> getTileSizes() {
        return Arrays.asList(new JP2Size(256, 256), new JP2Size(512, 512), new JP2Size(1024, 1024), new JP2Size(4096, 4096));
    }
    
    @Override
    public List<String> getProgressionOrders() {
        return Arrays.asList("RPCL", "PCRL", "LRCP", "CPRL", "RLCP");
    }

    @Override
    public List<Integer> getDecompositionLevels() {
        return Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }
    
    
}
