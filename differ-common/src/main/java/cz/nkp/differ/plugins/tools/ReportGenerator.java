package cz.nkp.differ.plugins.tools;


import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.awt.*;
import java.util.ArrayList;

import cz.nkp.differ.compare.io.ImageProcessorResult;
import cz.nkp.differ.compare.io.pure.PureImageProcessorResult;
import cz.nkp.differ.compare.metadata.ImageMetadata;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.dynamicreports.jasper.builder.export.JasperPdfExporterBuilder;

/**
 * @author Jonatan Svensson
 *         v. 12/09/2013
 */

public class ReportGenerator {

    private PureImageProcessorResult p;
    private ImageProcessorResult[] i;

    public ReportGenerator() {

    }

    private void build() {
        String path = System.getProperty("user.dir") + "/report.pdf";
        JasperPdfExporterBuilder pdfExporter = export.pdfExporter(path);
        System.out.println("Saving to " + path);
        StyleBuilder boldStyle = stl.style().bold();
        StyleBuilder boldCenteredStyle = stl.style(boldStyle).setHorizontalAlignment(HorizontalAlignment.CENTER);
        StyleBuilder columnTitleStyle = stl.style(boldCenteredStyle)
                .setBorder(stl.pen1Point())
                .setBackgroundColor(Color.LIGHT_GRAY);

        //TODO: Coloring of rows
        TextColumnBuilder<String> propertyColumn = col.column("Property", "property", type.stringType()).setStyle(boldStyle);
        TextColumnBuilder<String> sourceColumn = col.column("Source", "source", type.stringType());
        TextColumnBuilder<String> valueColumn = col.column("Value A", "valuea", type.stringType());
        TextColumnBuilder<String> valueTwoColumn = col.column("Value B", "valueb", type.stringType());

        try {
            report()
                    .setColumnTitleStyle(columnTitleStyle)
                    .highlightDetailEvenRows()
                    .columns(propertyColumn, sourceColumn, valueColumn, valueTwoColumn)
                    .title(cmp.text("DIFFER PDF Report").setStyle(boldCenteredStyle))//shows report title
                    .pageFooter(cmp.pageXofY().setStyle(boldStyle))//shows number of page at page footer
                    .setDataSource(createDataSource())
                    .toPdf(pdfExporter);
        } catch (DRException e) {
            e.printStackTrace();
        }
        // Reset data source
        i = null;
        p = null;
    }


    private JRDataSource createDataSource() {
        DRDataSource dataSource = null;
        // one image
        if (p != null) {
            ArrayList<ImageMetadata> metadata = (ArrayList<ImageMetadata>) p.getMetadata();
            dataSource = new DRDataSource("property", "valuea", "source");
            for (int i = 0; i < metadata.size(); i++) {
                ImageMetadata j = metadata.get(i);
                dataSource.add(j.getKey(), j.getValue().toString(), j.getSource().getSourceName());
            }
        }
        // two images
        else if (i != null) {
            // TODO: Group in properties and compare values
            ArrayList<ImageMetadata> metadataA = (ArrayList<ImageMetadata>) i[0].getMetadata();
            ArrayList<ImageMetadata> metadataB = (ArrayList<ImageMetadata>) i[1].getMetadata();

            dataSource = new DRDataSource("property", "valuea", "valueb", "source");
            for (int i = 0; i < metadataA.size(); i++) {
                ImageMetadata j = metadataA.get(i);
                dataSource.add(j.getKey(), j.getValue().toString(), "", j.getSource().getSourceName());
            }
            for (int i = 0; i < metadataB.size(); i++) {
                ImageMetadata j = metadataA.get(i);
                dataSource.add(j.getKey(), "", j.getValue().toString(), j.getSource().getSourceName());
            }

        }
        return dataSource;
    }

    public void buildAndExport() {
        build();
    }

    public void getThumbnail() {
        // Extract thumbnail/adjusted-size image in some clever way to put in report
    }

    public void setDataSource(PureImageProcessorResult p) {
        this.p = p;
    }

    public void setDataSource(ImageProcessorResult[] i) {
        this.i = i;
    }

}