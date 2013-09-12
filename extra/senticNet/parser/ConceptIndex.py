#! /usr/bin/env python

from nltk.corpus import stopwords

f = open("Concepts.txt","r")
lines = f.readlines()
f.close()

for i in range(len(lines)):
   lines[i] = lines[i].replace("\n","")

stopwords = list( stopwords.words('english') )

f = open('concept.index','w')


for concept in lines:
   words = concept.split()
   if( len(words) > 1 ):
      words_normalized = [x for x in words if x not in stopwords ]
      new_string = " ".join(words_normalized)
      print >> f, concept, "\t", new_string
      f.flush()
   else:
      print >> f, concept, "\t", concept


f.close()
