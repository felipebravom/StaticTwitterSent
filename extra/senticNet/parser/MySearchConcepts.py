#! /usr/bin/env python

import cPickle as pickle
import networkx as nx
import senticnet
import SenticParser
import numpy



def process_sentence(sentence):
    G = nx.read_gpickle( "test.gpickle" )
    
    sentence = sentence.lower()
    
    bigrams = []
    
    words = sentence.split()
    
    list_concepts = []
    conc = []
    
    to_add = ""
    
    
    
    for word in words:
       if ( word in G ):
          conc.append(word)
          to_add = to_add+ word+" "
          #print to_add
    
       else:
          if( to_add != "" ):
           list_concepts.append(to_add[:-1])
          to_add = ""     
    
    
    if( to_add != "" ):
       list_concepts.append(to_add[:-1])
    
    #print "list of concepts"
    #print list_concepts
    
    parserList = SenticParser.getOutputConcepts(sentence)
    
    
    #print "parseList"
    #print parserList 
    
    list_concept = list( set(list_concepts) |  set(parserList) ) 
    
    list_concept = filter(bool, list_concept)
    
    list_concept = set(list(list_concepts))
    
    sn = senticnet.Senticnet()
    
    to_search = []
    
    
    for phrase in list_concepts:
       concepts = phrase.split()
       to_search = to_search + concepts
       for i in range(len(concepts) - 1):
          for j in range(i+1, len(concepts)):
             try:
                k = nx.dijkstra_path(G,concepts[i], concepts[j])
                #print k 
                if( len(k) == j-i+1 and k == concepts[i:j+1] ):  
                   to_search = list( set(to_search) - set(k) )      
                   word_to_add = "_".join(k)
                   to_search.append( word_to_add ) 
    
             except:
                continue            
    
    
    
    to_search = list( set(  to_search ) )
    
    sorted_by_length = sorted(to_search, key=lambda tup:len(tup.split("_")) )
    
    #print "sorted by length"
    #print sorted_by_length
    
    #print "to search"
    #print to_search
    
    pols=[]  # List with polarities
    pleasantness=[]
    attention=[]
    sensitivity=[]
    aptitude=[]
    
    
    sentics = {"pleasantness" : 0, "attention" : 0, "sensitivity" : 0, "aptitude" : 0}
    
    for concept in to_search:
       try:
     #     print concept
     #     print "---------------------------"
     #     print sn.concept( concept)
          pol=sn.polarity( concept )     
     #     print pol
          pols.append(pol)      
          
     #     print  sn.semantics( concept )
     #     print  sn.sentics(concept)
          sentics=sn.sentics(concept)
          pleasantness.append(sentics.get("pleasantness"))
          attention.append(sentics.get("attention"))
          sensitivity.append(sentics.get("sensitivity"))
          aptitude.append(sentics.get("aptitude"))
          
          
          
      #    print "==================================================================================="
    
       except:
          continue
    
    
    if len(pols)==0:
        pols.append(0)
    if len(pleasantness)==0:
        pleasantness.append(0)
    if len(attention)==0:
        attention.append(0)
    if len(sensitivity)==0:
        sensitivity.append(0)
    if len(aptitude)==0:
        aptitude.append(0)
     
        
    
    result = {"polarity" : numpy.mean(pols), "pleasantness" : numpy.mean(pleasantness), "attention" : numpy.mean(attention), "sensitivity" : numpy.mean(sensitivity), "aptitude" : numpy.mean(aptitude)}
    
    
    return result
  
      
def process_file(input_file,output_file):
    f=open(input_file, "rb")
    lines=f.readlines()
    f.close()
    
    out_file = open(output_file, 'w')
    
    # Add the new features
    header=lines[0].rstrip('\n')+"\tSNpol\tSNpleas\tSNatten\tSNsensi\tSNapt\n"
    print header
    out_file.write(header)
   
    
    for line in lines[1:]:
        values=line.rstrip('\n').split("\t")
        print values[0]
        # proceso el tweet
        res=process_sentence(values[0])
        print res
        out_line=str(res.get("polarity"))+"\t"+str(res.get("pleasantness"))+"\t"+str(res.get("attention"))+"\t"+str(res.get("sensitivity"))+"\t"+str(res.get("aptitude"))+"\n"
        out_file.write(line.rstrip('\n')+"\t"+out_line)
        print out_line
    
    out_file.close()



if __name__ == '__main__':     
    process_file("salida.csv","procesado.csv")
    
    #res=process_sentence("Loves twitter")
    #print res
    