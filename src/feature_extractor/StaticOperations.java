/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feature_extractor;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

/**
 *
 * @author felipe
 */
public class StaticOperations {

    public static String[] convertLine(String line) {
        return line.split("\",\" ");

    }

    // Convierte distintas fechas en una sola
    public static String dateConverter(String strDate) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss zzz");


        Date dateStr = new Date();
        try {
            dateStr = formatter.parse(strDate);
        } catch (ParseException ex) {
            try {
                dateStr = formatter2.parse(strDate);
            } catch (ParseException ex1) {
                Logger.getLogger(StaticOperations.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        String formattedDate = formatter.format(dateStr);
        //System.out.println("yyyy-MM-dd date is ==>" + formattedDate);
        Date date1 = formatter.parse(formattedDate);

        formatter = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = formatter.format(date1);
        return formattedDate;

    }

    static public String[] wordTokenizer(String text) {
        try {
            InputStream modelIn = new FileInputStream("models-1.5/en-token.bin");
            TokenizerModel model = new TokenizerModel(modelIn);
            Tokenizer tokenizer = new TokenizerME(model);
            String[] tokens = tokenizer.tokenize(text);
            modelIn.close();
            return tokens;
        } catch (IOException ex) {
            Logger.getLogger(StaticOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    static public String clean(String text) {
        text = text.toLowerCase();
        Pattern specialCharacters = Pattern.compile("\\?|\\.|\\[|\\]|\\*|,|\\(|\\)|:|;|”|“|¿|\"|¡|\\{|\\}|#|%|\\‘||\\’");
        Matcher match = specialCharacters.matcher(text);
        text = match.replaceAll("");  // Removal of special characters
        return text.replaceAll("\n", " ");
    }

    // Creates time series by concatenig lines of the aggregated Features
    public static void createTimeSeries(String folderPath, String outFile) {
        PrintWriter pw = null;
        try {
            // List the files in the folder
            File inpFolder = new File(folderPath);
            File[] listOfFiles = inpFolder.listFiles();
            // We will map each date to a BufferedReader
            Map<Date, BufferedReader> fileMaper = new HashMap<Date, BufferedReader>();
            for (File f : listOfFiles) {
                if (f.isFile()) {
                    String fileName = f.getName();
                    System.out.println(fileName);
                    // check whether the file has the character : 
                    if (fileName.contains(":")) {
                        String parts[] = fileName.split(":");
                        try {
                            // we add BufferedReaders for each file using its date as key in the Hash
                            BufferedReader bf = new BufferedReader(new FileReader(f));
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                // Create a date object from the String
                                Date d = formatter.parse(parts[1]);
                                fileMaper.put(d, bf);

                            } catch (ParseException ex) {
                                Logger.getLogger(StaticOperations.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(StaticOperations.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            pw = new PrintWriter(new FileWriter(folderPath + "/" + outFile, true));
            // The files must be sorted by Date
            Date dates[] = fileMaper.keySet().toArray(new Date[0]);
            Arrays.sort(dates);
            for (int i = 0; i < dates.length; i++) {
                BufferedReader bf = fileMaper.get(dates[i]);
                if (i == 0) {
                    try {
                        String firstLine = bf.readLine();
                        pw.println(firstLine);
                    } catch (IOException ex) {
                        Logger.getLogger(StaticOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }


                String strLine = null, tmp;
                try {
                    while ((tmp = bf.readLine()) != null) {
                        strLine = tmp;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(StaticOperations.class.getName()).log(Level.SEVERE, null, ex);
                }

                String lastLine = strLine;
                try {
                    bf.close();
                } catch (IOException ex) {
                    Logger.getLogger(StaticOperations.class.getName()).log(Level.SEVERE, null, ex);
                }
                pw.println(lastLine);
            }
        } catch (IOException ex) {
            Logger.getLogger(StaticOperations.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pw.close();
        }


    }

    public static void printTimeStamp() {
        System.out.println(
                DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(new Date()));

    }

    static public void main(String args[]) throws InterruptedException, ParseException {
        String evalFile = "iphone:2012-01-02";
        String tweetsFile = "iphone:1-12-2011";
        String evalDate = evalFile.substring(evalFile.indexOf(":") + 1);
        String tweetDate = tweetsFile.substring(evalFile.indexOf(":") + 1);


        System.out.println(evalDate);
        System.out.println(tweetDate);


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat forma2 = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date d = formatter.parse("2012-01-02");
            Date d2 = forma2.parse("2-1-2012");
            System.out.println(d);
            System.out.println(d2);

            System.out.println(d2.equals(d));



        } catch (ParseException ex) {
            Logger.getLogger(StaticOperations.class.getName()).log(Level.SEVERE, null, ex);
        }


        System.out.println(dateConverter("Tue Apr 24 23:13:29 CLT 2012"));
        
        

    }
}
