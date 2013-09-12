#! /usr/bin/env python

import nltk
import sys 
from nltk.corpus import stopwords
from nltk.tag.stanford import POSTagger
 


class Parser:

    def __init__(self):
        self.MatchList = []
        self.ConceptMatches = []
        self.st = POSTagger('stanford-postagger/models/english-bidirectional-distsim.tagger', 'stanford-postagger/stanford-postagger.jar')


    def SyntacticMatch(self, concept1, concept2 ):                      # Checks for syntactic similarity. Checks for matching words between two concepts. 
	TaggedConcept1 = self.st.tag(nltk.word_tokenize(concept1))
	TaggedConcept2 = self.st.tag(nltk.word_tokenize(concept2))     

	print TaggedConcept1
	print TaggedConcept2

	flag = 0 

	for i in TaggedConcept1:
		for j in TaggedConcept2:
			if (i == j):
				if i[1].startswith("NN"):
					flag = 1
	

	if ( flag == 1):
		return True
	else:
		return False






    def FindBigrams(self, concept):                                      # Finds All Bigrams associated with the concept
        #sentence = concept.split(" ")                     	         # Splits the Given concept into Bigrams     e.g) "a very special christmas gift" gets split as ["a very", "very special", "special 																	christmas", "christmas gift"]

	sentence = self.st.tag(nltk.word_tokenize(concept))        
 
	print sentence

	Bigrams = []										
  
	for i in range(len(sentence) - 1):
            if ( sentence[i][1] == "JJ"  and sentence[i+1][0] in stopwords.words('english') ):		# If the bigram is [ adj + stopword ] , ignore
                continue 									           # bigrams like "a very" are ignored
	    
	    elif ( sentence[i][0] in stopwords.words('english')  and sentence[i+1][0] in stopwords.words('english') ):		# If the bigram is [ adj + stopword ] , ignore
                continue              


            elif ( sentence[i+1][1] == "JJ"  and sentence[i][0] in stopwords.words('english') ):            # If the bigram is [ stopword + adj ] , ignore 
                continue									           # bigrams like "amazingly a" is ignored

            elif ( sentence[i][1] == "JJ" and sentence[i+1][1].startswith("NN") ):                       # If the bigram is [ adj + concept ] , then include [adj + concept] and [concept] to the list
                Bigrams.append(sentence[i+1][0])						 # e.g) "special christmas" --> concepts extracted will be "special christmas" and "christmas" are added
                Bigrams.append(sentence[i][0]+" "+ sentence[i+1][0])
                
            elif ( sentence[i][0] in stopwords.words("english") and sentence[i+1][1].startswith("NN") ):       # If the bigram is [ stopword + concept ], then inlcude only the concept w/ and w/o the concept 
                    Bigrams.append(sentence[i+1][0])                                                                 # e.g) "the christmas" --> concepts that will be extracted is "christmas" , "the christmas"
		    Bigrams.append(sentence[i][0]+" "+ sentence[i+1][0])						          
           
	    elif ( sentence[i][1].startswith("NN") and sentence[i+1][1] == "JJ" ):							       # If the bigram ends with adjective , then ignore the adjective. 
                Bigrams.append(sentence[i][0])    							              # e.g) "present amazing" --> concept that will be extracted is "present"
                
            elif ( sentence[i][1].startswith("NN") and sentence[i+1][0] in stopwords.words("english")):					# If the bigram ends with a stopword , then ignore the stopword
                    Bigrams.append(sentence[i][0])							              # e.g) "christmas the" --> concept that will be extracted is "christmas"
             
            else:	
                Bigrams.append(sentence[i][0]+ " "+ sentence[i+1][0])
                   
                     
        print Bigrams

        return Bigrams


'''
p = Parser()

print p.FindBigrams("a very beautiful christmas gift")

'''
