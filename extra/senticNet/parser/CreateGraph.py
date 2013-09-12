#! /usr/bin/env python

import networkx as nx
import GetConcepts
import nltk
import matplotlib.pyplot as plt
import pickle

class CreateGraph():

	def __init__(self):
		self.ConceptList = GetConcepts.GetConcepts()

	
	def GraphCreate(self, Concepts):
		if( Concepts ):
			ConceptsToGraph = Concepts
		else: 
			ConceptsToGraph = self.ConceptList

		NodeGraph = nx.DiGraph()
		
		for word in ConceptsToGraph:
			nodes = nltk.word_tokenize(word)
			if ( NodeGraph.has_edge( "root", nodes[0] )):
				for i in range(len(nodes) - 1):
					NodeGraph.add_edge(nodes[i], nodes[i+1])
			
			else:
				NodeGraph.add_edge("root", nodes[0])
				for i in range(len(nodes) - 1):
					NodeGraph.add_edge(nodes[i], nodes[i+1])


		return NodeGraph


	def CheckGraph(self, Graph, concepts):
		if(concepts):
			ToCheck = concepts
		else:
			print "Sorry ! The knowledge base does not contain any concept for your query"
		
		ConceptsToBeSearched = []

		for i in range(len(ToCheck)):
			tokens = nltk.word_tokenize(ToCheck[i])
			length = len(tokens)
			print length
			if ( length > 1):
				for j in range(len(tokens) - 1):
					if( G.has_edge(tokens[j], tokens[j+1])):
						print "Match Found in Database"
						s = tokens[j]+" "+tokens[j + 1] 
						ConceptsToBeSearched.append(s)
					
		
		return ConceptsToBeSearched

'''
f = open('Concepts.txt','r')
concepts = f.readlines()
f.close()

for i in range(len(concepts)):
   concepts[i] = concepts[i].replace("\n","")


G = CreateGraph()


graph = G.GraphCreate(concepts)

nx.write_gpickle(graph,"test.gpickle")
'''
