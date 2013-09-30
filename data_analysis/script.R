library("rpart")
library("rpart.plot")
library("class")
library("e1071")
library("xtable")
library("FSelector")

setwd("/home/fbravo/Dropbox/thesis_exp/")

file1<-"twittersentiment.csv"
file2<-"sanders.csv"

sentiment_Data<-read.csv(file=file1,header=T,sep="\t")
data<-read.csv(file=file1,header=T,sep="\t")

neutrality_dataset<-subset(data, select=c(sentiStrength_pos,sentiStrength_neg,swn3_negativeness,
                                          swn3_positiveness,swn3_neutral_words,afinn_positiveness,
                                          afinn_negativeness,supervised_polarity,lex_positive_words,
                                          lex_negative_words))


# Clasificador de objetividad-subjectividad
neutral_label<-ifelse(data$label=="neutral","neutral","opinion")
neutral_feature<-ifelse(data$supervised_polarity=="neutral","neutral","opinion")

neutrality_dataset<-subset(data, select=c(sentiStrength_pos,sentiStrength_neg,swn3_negativeness,
                          swn3_positiveness,swn3_neutral_words,afinn_positiveness,
                          afinn_negativeness,supervised_polarity,lex_positive_words,
                          lex_negative_words))



information.gain(neutral_label~.,data=neutrality_dataset)
gain.ratio(neutral_label~.,data=neutrality_dataset)
chi.squared(neutral_label~.,data=neutrality_dataset)
subset <- cfs(neutral_label~.,data=neutrality_dataset) # Hace best-first

arbol<-rpart(neutral_label~.,data=neutrality_dataset,method="class")




datos<-cbind(datos,is_neutral=as.factor(datos$label=="neutral"))
form<-formula(is_neutral~supervised_polarity+swn3_neutral_words+sentiStrength_neg+
  swn3_negativeness+sentiStrength_pos+afinn_positiveness+afinn_negativeness)

form2<-formula(label~sentiStrength_pos) 

arb<-rpart(form,data=datos)
rpart.plot(arb)

obj <- tune(method=svm, form, 
            data = datos,ranges = list(gamma = 2^(-1:1), 
                                      cost = 2^(2:4)),
            tunecontrol = tune.control(sampling = "cross"),cross=10)


mod<-obj$best.model



objt <-tune.rpart(formula=form, 
                  data = datos, minsplit=c(3,5,10), 
                  cp=seq(from=0.001,to=2,by=0.1),maxdepth=c(1,5))

best.tree <-objt$best.model
rpart.plot(best.tree)
predt<-predict(best.tree)



