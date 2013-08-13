/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feature_extractor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

import uk.ac.wlv.sentistrength.SentiStrength;

/**
 *
 * @author felipe Controls an Entry, extract features
 */
public class EntryController {

    private Entry entry;
    private String data;
    private String format;
    private String clean_message;
    private String[] words;

    public EntryController(String d) {
        this.data = d;
    }

    // crea una entrada segun el formato del dato
    public void createEntry() {
        try {
            Class c = Class.forName("feature_extractor." + format + "EntryFactory");
            Constructor EntryFactoryConstructor = c.getConstructor(String.class); // Creamos la instancia
            EntryFactory ef = (EntryFactory) EntryFactoryConstructor.newInstance(this.data); // Creamos la instancia

            this.entry = ef.createEntry();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EntryController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(EntryController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(EntryController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(EntryController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(EntryController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(EntryController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(EntryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void processWords() {

        this.clean_message = StaticOperations.clean(this.entry.getContent());
        this.words = StaticOperations.wordTokenizer(this.clean_message);
    }

    public void evaluatePolarityLexicon(LexiconEvaluator le) {


        int negativeness = 0;
        int positiveness = 0;

        for (String w : this.words) {

            String pol = le.retrieveValue(w);
            if (pol.equals("positive")) {
                positiveness++;
            } else if (pol.equals("negative")) {
                negativeness++;
            }
        }


        entry.getMetaData().put("lex_positive_words", positiveness);
        entry.getMetaData().put("lex_negative_words", negativeness);


    }

    public void evaluateAFINNLexicon(LexiconEvaluator le) {

        int pos_words = 0;
        int neg_words = 0;

        int negativeness = 0;
        int positiveness = 0;


        for (String w : this.words) {

            String pol = le.retrieveValue(w);
            if (!pol.equals("not_found")) {
                int value = Integer.parseInt(pol);
                if (value > 0) {
                    pos_words++;
                    positiveness += value;
                } else {
                    neg_words++;
                    negativeness += value;
                }

            }


        }


        entry.getMetaData().put("afinn_positive_words", pos_words);
        entry.getMetaData().put("afinn_negative_words", neg_words);

        entry.getMetaData().put("afinn_positiveness", positiveness);
        entry.getMetaData().put("afinn_negativeness", negativeness);


    }

    // evalutes the sentiment score using SentiWordnet
    public void evaluateSWN3(SWN3 swn3) {
        int swn3_positive_words = 0;
        int swn3_neutral_words = 0;
        int swn3_negative_words = 0;

        double swn3_positiveness = 0.0d;
        double swn3_negativeness = 0.0d;


        for (String word : this.words) {
            if (swn3.getDict().containsKey(word)) {
                double score = swn3.getDict().get(word).doubleValue();
                if (score == 0) {
                    swn3_neutral_words++;
                } else if (score < 0) {
                    swn3_negative_words++;
                    swn3_negativeness += score;
                } else {
                    swn3_positive_words++;
                    swn3_positiveness += score;
                }

            }
        }
        entry.getMetaData().put("swn3_positive_words", swn3_positive_words);
        entry.getMetaData().put("swn3_neutral_words", swn3_neutral_words);
        entry.getMetaData().put("swn3_negative_words", swn3_negative_words);
        entry.getMetaData().put("swn3_positiveness", swn3_positiveness);
        entry.getMetaData().put("swn3_negativeness", swn3_negativeness);




    }

    public void evaluateSentiStrength(SentiStrength sentiStrength) {

        String sentence = "";
        for (int i = 0; i < this.words.length; i++) {
            sentence += words[i];
            if (i < this.words.length - 1) {
                sentence += "+";
            }
        }

        String result = sentiStrength.computeSentimentScores(sentence);
        String[] values = result.split(" ");


        int pos = Integer.parseInt(values[0]);
        int neg = Integer.parseInt(values[1]);
        int neu = Integer.parseInt(values[2]);

        entry.getMetaData().put("sentiStrength_pos", pos);
        entry.getMetaData().put("sentiStrength_neg", neg);
        entry.getMetaData().put("sentiStrength_neu", neu);

    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
