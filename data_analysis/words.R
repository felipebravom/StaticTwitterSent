setwd("Dropbox/thesis_exp/")

metalex<-read.csv(file="metaLex.csv",header=T,sep="\t")
metalex<-metalex[metalex$OPFIND!="neutral"&metalex$OPFIND!="both",]
metalex<-metalex[metalex$SWN3!=0,]

NCRneutral<-metalex$Nanger==0&metalex$Nanticipation==0&metalex$Ndisgust==0&metalex$Nfear==0&metalex$Njoy==0&metalex$Nsadness==0&metalex$Nsurprise==0&metalex$Ntrust==0

metalex<-metalex[!NCRneutral,]


myframe<-data.frame(words=metalex$word,SWN3=ifelse(metalex$SWN3=="missing",0,1),
                    AFINN=ifelse(metalex$AFINN=="missing",0,1),
                    OPF=ifelse(metalex$OPFIND=="missing",0,1),
                    NCR=ifelse(metalex$Nanger=="missing",0,1))

AFINN.words<-myframe[myframe$SWN3==0&myframe$AFINN==1&myframe$OPF==0&myframe$NCR==0,]
AFINN_sample<-sample(1:dim(AFINN.words)[1],size=30,replace=F)
AFINN.words[AFINN_sample,1];

Opfinder.words<-myframe[myframe$SWN3==0&myframe$AFINN==0&myframe$OPF==1&myframe$NCR==0,]

NCR.words<-myframe[myframe$SWN3==0&myframe$AFINN==0&myframe$OPF==0&myframe$NCR==1,]


interact.words<-myframe[myframe$SWN3==1&myframe$AFINN==1&myframe$OPF==1&myframe$NCR==1,]
interact.sample<-sample(1:dim(interact.words)[1],size=30,replace=F)
interact.words[interact.sample,1]

interact.words2<-metalex[metalex$SWN3!="missing"&metalex$AFINN!="missing"&
                           metalex$OPF!="missing"&metalex$Nfear!="missing",]

#Palabras negativas afinn y positivas OPfind
neg.afinn<-as.numeric(as.character(interact.words2$AFINN))<0&interact.words2$OPFIND=="positive"
neg.opfind<-as.numeric(as.character(interact.words2$AFINN))>0&interact.words2$OPFIND=="negative"

interact.sample<-sample(1:dim(interact.words)[1],size=30,replace=F)
interact.words[interact.sample,1]

summary(metalex$SWN3!="missing")
summary(metalex$Nanger!="missing")
summary(metalex$AFINN!="missing")
summary(metalex$OPFIND!="missing")

summary(metalex$Nanticipation!="missing"&metalex$SWN3!="missing")
summary(metalex$AFINN!="missing"&metalex$SWN3!="missing")
summary(metalex$OPFIND!="missing"&metalex$SWN3!="missing")
summary(metalex$AFINN!="missing"&metalex$Nanger!="missing")
summary(metalex$OPFIND!="missing"&metalex$Nanger!="missing")
summary(metalex$OPFIND!="missing"&metalex$AFINN!="missing")
summary(metalex$AFINN!="missing"&metalex$Ndisgust!="missing")



summary(metalex$AFINN!="missing"&metalex$Ndisgust!="missing"&metalex$OPFIND!="missing")
summary(metalex$AFINN!="missing"&metalex$Ndisgust!="missing"&metalex$OPFIND!="missing"
        &metalex$SWN3!="missing")

summary(metalex$AFINN!="missing"&metalex$Ndisgust!="missing"&metalex$SWN3!="missing")
summary(metalex$Ndisgust!="missing"&metalex$OPFIND!="missing"&metalex$SWN3!="missing")

summary(metalex$Ndisgust!="missing"&metalex$OPFIND!="missing"&metalex$AFINN!="missing")


summary(metalex$OPFIND!="missing"&metalex$AFINN!="missing"&metalex$SWN3!="missing")






myframe<-data.frame(words=metalex$word,SWN3=ifelse(metalex$SWN3=="missing",0,1),
                   AFINN=ifelse(metalex$AFINN=="missing",0,1),
                   OPF=ifelse(metalex$OPFIND=="missing",0,1),
                   NCR=ifelse(metalex$Nanger=="missing",0,1))

options( java.parameters="-Xmx4G" )
library(venneuler)

mat<-as.matrix(myframe)
vd<-venneuler(mat)
plot(vd)

values<-c(SWN3=34257
,NCR=4031
,AFINN=2017
,OPFINDER=4869
, "SWN3&NCR"=3870
,"SWN3&AFINN"=1341
,"SWN3&OPFINDER"=4256
,"AFINN&NCR"=865
,"OPFINDER&NCR"=2207
,"OPFINDER&AFINN"=1025
,"SWN3&NCR&AFINN"=834
,"SWN3&NCR&OPFINDER"=2191
,"SWN3&AFINN&OPFINDER"=978 
,"NCR&AFINN&OPFINDER"=682
,"SWN3&NCR&AFINN&OPFINDER"=677
          
)

vd<-venneuler(values)
plot(vd)
