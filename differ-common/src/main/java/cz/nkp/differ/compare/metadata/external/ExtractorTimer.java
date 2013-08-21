package cz.nkp.differ.compare.metadata.external;

import cz.nkp.differ.compare.metadata.ImageMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import java.util.ArrayList;

/**@author Jonatan Svensson
 * @date 21/08/2013
 *
 * Starts timer before all metadata extraction and stops right after.
 * Saves the timer into the corresponding list.
 * If there is no list computed for the extractor then do nothing.
 */

public class ExtractorTimer {
    Logger logger = LogManager.getLogger(ExtractorTimer.class.getName());

    public Object startAndStopTimer(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.nanoTime();
        logger.debug("Starting timer, time is: "+startTime);
        Object o = pjp.proceed();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;

        try{
            ArrayList retVal = (ArrayList<ImageMetadata>) o;
            ImageMetadata i= (ImageMetadata) retVal.get(0);
            ImageMetadata timerProperty = new ImageMetadata("Elapsed Time of Extraction",duration,i.getSource());
            timerProperty.setUnit("ms");
            retVal.add(timerProperty);
            return retVal;
        }
        catch(Exception e){
            logger.error("Error: There is no element in metadata in order to fetch source name");
        }
        return o;
    }
}
