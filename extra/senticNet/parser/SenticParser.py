#! /usr/bin/env python

import nltk
import sqlite3
from nltk.stem.lancaster import LancasterStemmer
import enchant
from nltk.corpus import conll2000
from nltk.chunk.util import conlltags2tree
import string
from cPickle import load
from nltk.tag.stanford import POSTagger
from nltk.corpus import stopwords
import ParseBigrams
from itertools import groupby


class SenticParser:
	def __init__(self):
		self.st = POSTagger('stanford-postagger/models/english-bidirectional-distsim.tagger', 'stanford-postagger/stanford-postagger.jar')

	def TaggedSentenceSlashForm(self, sentence ):

		#print sentence.split()
		Tagged = self.st.tag(sentence.split())

		TaggedSentence = ""
		for i in Tagged:
			TaggedSentence = TaggedSentence+"/".join(i)+" "


		#print TaggedSentence
		return TaggedSentence


	def TaggedSentence(self, sentence ):
		Tagged = self.st.tag(sentence.split())
		return Tagged


	def FindStemmedVerb(self, word):
		st = LancasterStemmer()
		StemmedVerb = st.stem(word)
		
		dic = enchant.Dict("en_US")
		if( dic.check(StemmedVerb) ):
			return StemmedVerb
		else:
			return StemmedVerb+"e"			
	

	def FindSplit(self, sentence, TaggedSentence):
		TokenizedSentence = nltk.word_tokenize(sentence)

		SplitList = []
		SentAdded = ""
		split = 0 

		#print TaggedSentence

		for i in range(len(TaggedSentence)):
			if TaggedSentence[i][1].startswith("VB"):
				SplitList.append(SentAdded)
				try:
					if (TaggedSentence[i+1][1].startswith("VB")):
						SentAdded = ""
					else:
						SplitList.append(SentAdded)
						SentAdded = TaggedSentence[i][0]+" "
					#	print "split"
				except:
					SplitList.append(TaggedSentence[i][0]) 
				
			else:
				#print SentAdded
				SentAdded = SentAdded + TokenizedSentence[i] + " "
							
		SplitList.append(SentAdded)		
	

		Str_list = filter(None, SplitList)
		Str_list = list(set(Str_list))

		'''
		for i in range(len(Str_list)):
			Str_list[i] = Str_list[i][:-1].translate(string.maketrans("",""), string.punctuation)
		'''
		return Str_list



class ChunkParser(nltk.ChunkParserI):

    def __init__(self, train_sents):
        train_data = [[(t,c) for w,t,c in nltk.chunk.tree2conlltags(sent)]
                      for sent in train_sents]
        self.tagger = nltk.TrigramTagger(train_data)

    def parse(self, sentence):
        pos_tags = [pos for (word,pos) in sentence]
        tagged_pos_tags = self.tagger.tag(pos_tags)
        chunktags = [chunktag for (pos, chunktag) in tagged_pos_tags]
        conlltags = [(word, pos, chunktag) for ((word,pos),chunktag)
                     in zip(sentence, chunktags)]
        return conlltags2tree(conlltags)



class VerbChunk():
	
	def __init(self):
		pass

	def VerbParse(self, TaggedChunk):
		grammar = r"""
		  		VP: {<VB.*>+ } # Chunk verbs
				"""

		cp = nltk.RegexpParser(grammar)#,loop=2)
		vpTree = cp.parse(TaggedChunk)
		return vpTree

	


def getOutputConcepts(sentence):

   #sentence = raw_input("Enter your search sentence ===>> " ).lower() 

   Parse = SenticParser()
   TaggedSentence = Parse.TaggedSentence(sentence)

   ConceptChunks = []							

   # Chunking Phase
   test_sents = conll2000.chunked_sents('test.txt', chunk_types=['NP'])
   train_sents = conll2000.chunked_sents('train.txt', chunk_types=['NP'])

   # training the chunker
   NPChunker = ChunkParser(train_sents)


   pb = ParseBigrams.Parser()

   ChunkedSentences = Parse.FindSplit(sentence, TaggedSentence)

   events = []
   objects = []

   verb=""


   #print ChunkedSentences

   for sent in ChunkedSentences:

	   TaggedChunk = Parse.TaggedSentence(sent)
	   print TaggedChunk


	   PartsSentence = [l for l in [list(group) for key, group in groupby(TaggedChunk, key=lambda k: k[1]=="IN")]
                                   if l[0][1] != 0]		
	

	   #print PartsSentence
	
	   SplitByIN = []

	   for j in (range(0, len(PartsSentence), 2)):
		   SplitByIN.append( PartsSentence[j] )


	   #print "Split",SplitByIN


	   NounTree =  NPChunker.parse(SplitByIN[0])

	   nouns = []
	
	   for n in NounTree:
	      if isinstance(n, nltk.tree.Tree):               
	           if n.node == 'NP':
	               TaggedPhrase = n.leaves()
		       TagRemoved = " ".join(tup[0] for tup in TaggedPhrase)
		       nouns.append(TagRemoved)
		       nouns = nouns + pb.FindBigrams(TagRemoved) 
		       #print TagRemoved
              else:
      	         continue


	   nouns = list(set(nouns) - set(["i", "you","we", "our", "they", "their", "he", "she", "it", "her", "his"]) ) 

	   objects = objects + nouns

	   for j in range(1, len(SplitByIN)):
			
		   NounTree =  NPChunker.parse(SplitByIN[j])

		   other_nouns = []
	
		   for n in NounTree:
		      if isinstance(n, nltk.tree.Tree):               
		           if n.node == 'NP':
		               TaggedPhrase = n.leaves()
			       TagRemoved = " ".join(tup[0] for tup in TaggedPhrase)
			       other_nouns.append(TagRemoved)
			       other_nouns = other_nouns + pb.FindBigrams(TagRemoved) 
			       #print TagRemoved
	              else:
	      	         continue

		   objects = objects + other_nouns



	   #for i in NounTree:
	   #	print n.leaves()
	
	
	
	   vp = VerbChunk()

	
	   FindVerbTree = vp.VerbParse(TaggedChunk)

	   #print FindVerbTree		

	   for n in FindVerbTree:
            if isinstance(n, nltk.tree.Tree):               
               if n.node == 'VP':
                  verb = Parse.FindStemmedVerb(n.leaves()[-1][0])
                  #print verb
               else:
                  continue                     


	   if(verb not in stopwords.words('english')):
		   if(nouns):
			   #print nouns
			   #print verb
			   for noun in nouns:
				   events.append( verb+" "+noun)

		   else:
			   events.append(verb)

   events = list(set(events))
   objects = list(set(objects))	

   print events
   print objects

   outputList = list( set(events) | set(objects))
   print outputList

   return outputList

#if __name__ == "__main__":
#	main()

