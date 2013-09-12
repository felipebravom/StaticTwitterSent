#! /usr/bin/env python

import cPickle as pickle
import networkx as nx
import senticnet
import SenticParser
 
G = nx.read_gpickle( "test.gpickle" )

sentence = raw_input("Enter your search sentence ===>> " ).lower()

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

print list_concepts

parserList = SenticParser.getOutputConcepts(sentence)
print parserList 

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

print sorted_by_length

print to_search



for concept in to_search:
   try:
      print concept
      print "---------------------------"
      print sn.concept( concept)
      print sn.polarity( concept )
      print  sn.semantics( concept )
      print  sn.sentics(concept)
      print "==================================================================================="

   except:
      continue
