#! /usr/bin/env python

import urllib2

def GetConcepts():

	response = urllib2.urlopen('http://sentic.net/api/en/concept/')
	html = response.readlines()

#	print html 

	Concepts = []


	for i in html:
		myString = i
		mySubString = myString[ myString.find("concept/")+8: myString.find('"/>')]
		Concepts.append(mySubString.replace("_"," "))



	del Concepts[0:2]
	del Concepts[-1]

	f = open('Concepts.txt','w')
	
	for i in Concepts:
		print >> f, i 
		f.flush()

	f.close()

	print "Number of Concepts: ", len(Concepts)

	return Concepts


