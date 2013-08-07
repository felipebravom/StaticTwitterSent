package timeseriesbuilder;


import java.io.File;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fbravo
 */

// Toma todos los archivos de una carpeta y crea una nueva version pero sin duplicados
public class FolderDuplicateRemover {
    private String inputFolder;
    private String outputFolder;
    
    public FolderDuplicateRemover(String inp,String out){
        this.inputFolder=inp;
        this.outputFolder=out;
    }
    
    public void process(){
        File inpFolder=new File(inputFolder);
        File[] listOfFiles = inpFolder.listFiles();
        
        for (File f:listOfFiles){
            System.out.println(f.getName());
            DuplicateRemover dr=new DuplicateRemover(f.getAbsolutePath(),this.outputFolder+"/"+f.getName());
            dr.process();
        }
        
        
        
        
        
        
    }
    
    
    static public void main(String args[]){
        String inp="/home/felipe/Tweets2/norh_korea";
        FolderDuplicateRemover fdr=new FolderDuplicateRemover(inp,"north_korea");
        fdr.process();
        
        
    }
    
}
