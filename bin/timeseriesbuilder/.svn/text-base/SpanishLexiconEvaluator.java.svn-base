package timeseriesbuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.stemmers.SnowballStemmer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author fbravo
 */
public class SpanishLexiconEvaluator extends LexiconEvaluator {

    public SpanishLexiconEvaluator(String file) {
        super(file);
    }

    @Override
    public void processDict() {
        try {
            // Uso Stemming
            SnowballStemmer ste = new SnowballStemmer();
            ste.setStemmer("spanish");

            // first, we open the file
            Scanner sc = new Scanner(new File(this.path));
            sc.useDelimiter("\n");
            for (String line = sc.next(); sc.hasNext(); line = sc.next()) {
                String pair[] = line.split("\t");

                this.dict.put(ste.stem(pair[0]), pair[1]);



            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SpanishLexiconEvaluator.class.getName()).log(Level.SEVERE, null, ex);
        }




    }

    static public void main(String args[]) {
        LexiconEvaluator l = new SpanishLexiconEvaluator("anewspanish.csv");
        l.processDict();
        System.out.println(l.retrieveValue("feliz"));
        System.out.println(l.retrieveValue("triste"));
        System.out.println(l.retrieveValue("mierda"));



    }
}
