package cz.nkp.differ.cmdline;

import com.beust.jcommander.JCommander;
import cz.nkp.differ.compare.metadata.external.ImagemagickInChainTransformer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import cz.nkp.differ.compare.io.ImageProcessor;
import cz.nkp.differ.compare.io.ImageProcessorResult;
import cz.nkp.differ.compare.io.SerializableImageProcessorResult;
import cz.nkp.differ.compare.io.SerializableImageProcessorResults;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.stream.StreamResult;

/**
  mvn -fn -o -Dmaven.compiler.showWarnings=true -f pom.xml compile test-compile
*/
public class Main {
	private static CommandArgs commandArgs = new CommandArgs();

	/**
	 *
	 * This function validates an image and writes all reports regarding to arguments.
	 * @throws Exception
	 */
	public static String processFile(ApplicationContext context, File file, ImageProcessorResult result, Boolean isComparison) throws Exception {
		Jaxb2Marshaller marshaller = (Jaxb2Marshaller) context.getBean("jaxb2Marshaller");
		TextResultTransformer textResultTransformer = new TextResultTransformer(
                context,
                new TheSameNameOutputNamer(),
				commandArgs.saveOutputs,
				commandArgs.saveReport,
				commandArgs.saveProperties,
				false
		);
		String output = textResultTransformer.transform(file, result);
		if ( commandArgs.saveReport ) {
			FileWriter writer = new FileWriter(textResultTransformer.outputNamer.textName(file,result).toString());
			writer.write(output);
			writer.close();

			writer = new FileWriter(textResultTransformer.outputNamer.reportName(file,result));
			SerializableImageProcessorResult resultForSerialization = SerializableImageProcessorResult.create(result, false);
			StreamResult streamResult = new StreamResult(writer);
			marshaller.marshal(resultForSerialization, streamResult);
			writer.close();
            if( !isComparison && commandArgs.sendReportToWeb) {
                ReportHTTPSender httpSender = (ReportHTTPSender) context.getBean("reportHTTPSender");
                httpSender.sendReport(textResultTransformer.outputNamer.reportName(file,result));
            }
    	}
		return output;
	}

	/**
	 * This function calls validator and comparator on two files.
	 * And will report averything it did.
	 */
	public static String processFiles(ApplicationContext context, File file[], ImageProcessorResult results[]) throws Exception {
		String output = "";
		Jaxb2Marshaller marshaller = (Jaxb2Marshaller) context.getBean("jaxb2Marshaller");
		TextCompareResultTransformer textCompareResultTransformer =	new TextCompareResultTransformer(context,
                new TheSameNameOutputNamer(),
																									  commandArgs.saveOutputs,
																									  commandArgs.saveReport,
																									  commandArgs.saveProperties,
																									  false
																									  );
		output += textCompareResultTransformer.transform(file, results);
		if ( commandArgs.saveReport ) {
			FileWriter writer = new FileWriter(textCompareResultTransformer.outputNamer.textCompareName(file[0], file[1], results).toString());
			writer.write(output);
			writer.close();
			
			writer = new FileWriter(textCompareResultTransformer.outputNamer.reportCompareName(file[0], file[1],
                            results));

            List<SerializableImageProcessorResult> resultsForSerialization = new ArrayList<SerializableImageProcessorResult>();
            for (ImageProcessorResult result : results) {
                if( result != null ){
                    resultsForSerialization.add(SerializableImageProcessorResult.create(result, false));
                };
            }
            SerializableImageProcessorResults serializableResults = new SerializableImageProcessorResults(resultsForSerialization);
            StreamResult streamResult = new StreamResult(writer);
            marshaller.marshal(serializableResults, streamResult);
            writer.close();
            if( commandArgs.sendReportToWeb ) {
                ReportHTTPSender sender = (ReportHTTPSender) context.getBean("reportHTTPSender");
                sender.sendReport(textCompareResultTransformer.outputNamer.reportCompareName(file[0], file[1], results));
            }
		}
		return output;
	}

	/**
	 * Iterates through directories.
	 * It finds file pairs to compare.
	 * @param files
	 * @return
	 */
	public static ArrayList<File[]> getPairsToCompare(File [] files){
		ArrayList<File[]> pairs = new ArrayList<File[]> ();
		HashMap<String,File[]> pairsByName = new HashMap<String,File[]>();
		for( File file: files[0].listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return !file.isDirectory();
			}
		})){
			String name = file.getName();
			String nameWithoutExtension = name.replaceFirst("[.][^.]+$", "");
			File [] pair = new File[2];
			pair[0] = file;
			pair[1] = null;
			pairsByName.put(nameWithoutExtension,pair);
		}
		for( File file: files[1].listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return !file.isDirectory();
			}
		})){
			String name = file.getName().replaceFirst("[.][^.]+$", "");
			if( pairsByName.containsKey(name)){
				pairsByName.get(name)[1] = file;
			};
		}
		// use just pairs that do have all elemenents filled.
		for(String name: pairsByName.keySet()){
			File[] pair = pairsByName.get(name);
			if( pair[0] != null && pair[1] != null){
				pairs.add(pair);
			}
		}
		Collections.sort(pairs, new Comparator<File[]>() {
			@Override
			public int compare(File[] files, File[] files2) {
				return files[0].toString().compareToIgnoreCase(files2[0].toString());
			}
		});
		return pairs;
	}
		
	public static void main(String[] args) throws Exception {
		
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"appCtx-differ-cmdline.xml","appCtx-differ-common.xml"});
        ImageProcessor processor = (ImageProcessor) context.getBean("imageProcessor");

        JCommander jcommander = new JCommander(commandArgs,args);
		jcommander.setAcceptUnknownOptions(true);
		if( commandArgs.files.size() > 1){
			File files[] = new File[2];
			files[0] = new File(commandArgs.files.get(0));
			files[1] = new File(commandArgs.files.get(1));
			if( files[0].isDirectory() && files[1].isDirectory()){
				ArrayList<File[]> pairs = getPairsToCompare(files);
				for( File[] fpair: pairs){
                    System.out.println(String.format("processing: \t%s\n\t\t%s", fpair[0], fpair[1]));
                    ImageProcessorResult[] results = processor.processImages(fpair[0], fpair[1]);
					System.out.println(String.format("   process results: \t%s",fpair[0]));
                    processFile(context, fpair[0], results[0], true);
                    System.out.println(String.format("   process results: \t%s",fpair[1]));
                    processFile(context, fpair[1], results[1], true);
                    System.out.println(String.format("   process compare results"));
                    processFiles(context, fpair, results);
 				}
				System.out.println("Done\n");
			} else {
                ImageProcessorResult[] results = processor.processImages(files[0], files[1]);
                System.out.println(processFiles(context, files, results));
			}
		} else {
			File file = new File(commandArgs.files.get(0));
			if( file.isDirectory()){
                System.out.println("argument is the only directory. I do not know what to do with it.");
			} else {
                ImageProcessorResult result = processor.processImage(file);
                System.out.println(processFile(context, file, result, false));
			}
		}
   }
}
