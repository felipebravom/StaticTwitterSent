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
    

    # My features accumulated
    pos=0
    neg=0
    pleasan=0
    atten=0
    sensi=0
    apti=0
    
    
    
    for concept in to_search:
       try:
     #     print concept
     #     print "---------------------------"
     #     print sn.concept( concept)
          pol=sn.polarity( concept )
          if(pol>0):
              pos += pol
          else:
              neg += pol    
            

          
     #     print  sn.semantics( concept )
     #     print  sn.sentics(concept)
          sentics = sn.sentics(concept)

          
          pleasan += sentics.get("pleasantness")
          atten += sentics.get("attention")
          sensi += sentics.get("sensitivity")
          apti += sentics.get("aptitude")
             
          
          
      #    print "==================================================================================="
    
       except:
          continue
    
    

        
 
    
    result = {"positivity" : pos, "negativity": neg,  "pleasantness" : pleasan, 
              "attention" : atten, "sensitivity" : sensi, 
              "aptitude" : apti}
    
    
    
    return result
  
      
def process_file(input_file,output_file):
    f=open(input_file, "rb")
    lines=f.readlines()
    f.close()
    
    out_file = open(output_file, 'w')
    
    # Add the new features
    header=lines[0].rstrip('\n')+"\tSNpos\tSNneg\tSNpleas\tSNatten\tSNsensi\tSNapt\n"
    print header
    out_file.write(header)
   
    
    for line in lines[1:]:
        values=line.rstrip('\n').split("\t")
        print values[0]
        # proceso el tweet
        res=process_sentence(values[0])
        print res
        out_line=str(res.get("positivity"))+"\t"+str(res.get("negativity"))+"\t"+str(res.get("pleasantness"))+"\t"+str(res.get("attention"))+"\t"+str(res.get("sensitivity"))+"\t"+str(res.get("aptitude"))+"\n"
        out_file.write(line.rstrip('\n')+"\t"+out_line)
        print out_line
    
    out_file.close()



if __name__ == '__main__':     
    process_file("salida.csv","procesado.csv")
    
    #res=process_sentence("I hate but I love you a lot")
    #print res
    
