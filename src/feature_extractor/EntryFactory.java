/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * Esqueleto de mi fabracia de entradas
 */
package feature_extractor;


/**
 *
 * @author felipe
 * 
 */
public abstract class EntryFactory {
    protected String entryData;
    
    public EntryFactory(String form){
        this.entryData=form;                
    }
    
    public abstract Entry createEntry();
    
}
