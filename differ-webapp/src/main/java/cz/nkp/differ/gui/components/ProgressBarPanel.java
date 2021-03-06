package cz.nkp.differ.gui.components;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import cz.nkp.differ.DifferApplication;
import cz.nkp.differ.compare.io.CompareComponent;
import cz.nkp.differ.compare.io.ImageProcessorResult;
import cz.nkp.differ.gui.tabs.DifferProgramTab;
import cz.nkp.differ.listener.Message;
import cz.nkp.differ.listener.ProgressType;
import cz.nkp.differ.model.Image;
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;

public class ProgressBarPanel extends VerticalLayout implements WebProgressListener {
    
    private static final long serialVersionUID = -4597810967107465071L;
    private final ProgressIndicator progress = new ProgressIndicator();
    private final TextArea progressDetails = new TextArea();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
    private int numberOfTasks = 0;
    private final CompareComponent compareComponent;
    private final Object lock;
    private final DifferProgramTab tab;
    static Logger LOGGER = Logger.getLogger(ProgressBarPanel.class);

    public ProgressBarPanel(DifferProgramTab tab, Object lock, CompareComponent compareComponent, Image[] images) {
        this.tab = tab;
        this.compareComponent = compareComponent;
        compareComponent.setImages(images);
        progress.setWidth("300px");
        progress.setIndeterminate(false);
        progress.setImmediate(true);
        progress.setPollingInterval(750);
        progress.setCaption("Loading plugin...");
        progress.setValue(0f);
        this.addComponent(progress);
        progressDetails.setWidth("300px");
        this.addComponent(progressDetails);
        compareComponent.setPluginDisplayComponentCallback(this);
        this.lock = lock;
    }

    @Override
    public void onStart(String identifier, int numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
        synchronized (lock) {
            progressDetails.setRows(numberOfTasks);
        }
    }

    @Override
    public void onProgress(Message message) {
        if (message.getProgressType() == ProgressType.FINISH) {
            int finished = message.getNumberOfFinishedTaks();
            float doneInPercent = finished / (numberOfTasks * 1.0f);
            String time = dateFormat.format(message.getTime());
            String text = String.format("%s %s %s done\n", time, message.getEventType(),
                     message.getToolName());
            synchronized (lock) {
                progress.setValue(doneInPercent);
                if (((String) progressDetails.getValue()).isEmpty()) {
                    progressDetails.setValue(text);
                } else {
                    progressDetails.setValue(progressDetails.getValue() + text);
                }
            }
        }
    }

    @Override
    public void onFinish(String identifier, ImageProcessorResult[] results) {
    }

    @Override
    public void onReady(Object c) {
        synchronized (lock) {
            this.removeAllComponents();
            this.addComponent((Component) c);
            if (tab == null) {
            	return;
            }
            for (Button button : compareComponent.getButtons()) {
                this.tab.getTopPanelWithButtons().addComponent(button);
            }
        }
    }
    
    @Override 
    public void onFail(Exception ex) {
        synchronized (lock) {
            this.removeAllComponents();
            DifferApplication.getCurrentApplication().getMainWindow().showNotification("Results can not be generated.",
                            "<br/>", Window.Notification.TYPE_WARNING_MESSAGE);
        }
    }
}
