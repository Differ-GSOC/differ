package cz.nkp.differ.util;

import com.vaadin.Application;
import com.vaadin.terminal.FileResource;
import com.vaadin.terminal.Resource;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

import cz.nkp.differ.DifferApplication;
import cz.nkp.differ.compare.io.SerializableImage;
import cz.nkp.differ.gui.components.HelpTooltip;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;

/**
 * Contains GUI Macros to simplify both code readability and future code
 * modifications. These methods are common actions for GUI components.
 *
 * @author Joshua Mabrey Jun 12, 2012
 */
public class GUIMacros {

    static Logger LOGGER = Logger.getLogger(GUIMacros.class);
    public static final Label ErrorLabel = new Label("There has been an error.");

    /**
     * Returns a Button.ClickListener that will open the Window passed to the
     * method whenever the component the listener is attached to fires the
     * ButtonClick event.
     *
     * @param target
     * @return
     */
    @SuppressWarnings("serial")
    public static final Button.ClickListener createWindowOpenButtonListener(final Window mainWindow, final Window target) {
        return new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                GeneralMacros.errorIfContainsNull(target);
                //DifferApplication.getCurrentApplication().getMainWindow().addWindow(target);
                mainWindow.addWindow(target);
            }
        };
    }
    private static final Map<String, Object> CloseVariableMap = new HashMap<String, Object>(1);

    static {
        CloseVariableMap.put("close", true);
    }

    /**
     * Returns a Button.ClickListener that will close the Window passed to the
     * method whenever the component the listener is attached to fires the
     * ButtonClick event. If target is null this method will fail with an error
     * that will end the application session.
     *
     * @param target
     * @return
     */
    @SuppressWarnings("serial")
    public static final Button.ClickListener createWindowCloseButtonListener(final Window target) {
        return new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                GeneralMacros.errorIfContainsNull(target);
                target.changeVariables(null, CloseVariableMap);//Workaround. May change in future releases of Vaadin.
                //Generally windows can't be closed by easy function call b/c the call is package local. However
                //this map insertion is the same way currently used by the window to close itself. 
            }
        };
    }

    public static void closeWindow(Window target) {
        GeneralMacros.errorIfContainsNull(target);
        target.changeVariables(null, CloseVariableMap);
    }

    /**
     * Returns a HorizontalLayout with the tooltip positioned after the
     * component.
     *
     * @param c
     * @param h
     * @return
     */
    public static final HorizontalLayout bindTooltipToComponent(Component c, String title, String helpText) {
        if (GeneralMacros.containsNull(c, title, helpText)) {
            return null;
        }

        HorizontalLayout layout = new HorizontalLayout();
        layout.addComponent(c);
        layout.addComponent(new HelpTooltip(title, helpText));

        return layout;
    }

    public static Resource imageToResource(Application application, Image img) {
        if (img == null) {
            return null;
        }
        if (img instanceof SerializableImage) {
            img = ((SerializableImage) img).getBufferedImage();
        }
        try {
            String FILE_EXT = "png";
            File temp = File.createTempFile("image", "." + FILE_EXT);
            OutputStream stream = new BufferedOutputStream(new FileOutputStream(temp));
            DifferApplication.getTemporaryFilesCleaner().addFile(temp);
            /* Write the image to a buffer. */
            BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics g = bimage.getGraphics();
            g.drawImage(img, 0, 0, null);
            g.dispose();
            ImageIO.setUseCache(false);
            ImageIO.write(bimage, FILE_EXT, stream);
            stream.flush();
            stream.close();
            return new FileResource(temp, application);
        } catch (IOException e) {
            return null;
        }
    }

    public static Resource imageToResource(Image img) {
        return imageToResource(DifferApplication.getCurrentApplication(), img);
    }
    
}
