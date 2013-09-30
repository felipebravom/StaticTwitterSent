#Clasificador de Opiniones
# Basado en dos corpus

library("nnet")
library("rpart")
library("rpart.plot")
library("class")
library("e1071")
library("xtable")
library("RWeka")
library("FSelector")

#Entrena haciendo k-folds cross-validation
#data: el data frame, label: la variable objetivo en String, method: la funcion
# en ... deben ir los parámetros adicionales del método
# Devuelve el data.frame con una nueva columna que tiene los valores predichos
mycv<-function(k,data,label,method,...){
  results<-data.frame()
  splits <- runif(nrow(data))
  for(i in 1:k){
    test.idx <- (splits >= (i - 1) / k) & (splits < i / k)
    train.idx <- !test.idx
    test <- data[test.idx, , drop=FALSE]
    train <- data[train.idx, , drop=FALSE]
    fun <- method(as.formula(paste(label,"~.")),data=train,...)
    fitted<-cbind(test,predicted=predict(object=fun, newdata=test,type="c"))
    results<-rbind(results,fitted)
    
  }
  results<-results[order(as.numeric(rownames(results))),]
  results 
}

# Calcula las métricas Accuracy, Precision, Recall, F-Score
my_performance<-function(predicted,real){
  xTab=table(predicted,real)
  acc=sum(xTab[1,1],xTab[2,2])/sum(xTab)
  prec <- xTab[2,2]/sum(xTab[2,]) # Precision
  rec <- xTab[2,2]/sum(xTab[,2]) #  Recall
  f <- (2*prec*rec)/sum(prec,rec) # F
  list(confusion=xTab,perfomance=c(accuracy=acc,precision=prec,recall=rec,fscore=f))
}

#Recibe un data.frame con columnas y los hace votas
my_vote<-function(data){  
  apply(data,1,function(dat){
    tab<-table(dat)
    names(which.max(tab))
  })
}



setwd("/home/felipe/Dropbox/thesis_exp/")

file1<-"twittersentiment.csv"
file2<-"sanders.csv"
file3<-"sanders+twittersent.csv"

# Twitter Sentiment and Sanders datasets
data_ts<-read.csv(file=file1,header=T,sep="\t")
data_san<-read.csv(file=file2,header=T,sep="\t")

# descartamos las columnas inutiles
data_ts<-subset(data_ts, select=c(sentiStrength_pos,sentiStrength_neg,sentiStrength_neu,
                                  swn3_negativeness,swn3_positiveness,afinn_positiveness,
                                  afinn_negativeness,supervised_polarity,lex_positive_words,
                                  lex_negative_words,emo_joy,emo_trust,emo_sadness,emo_anger,
                                  emo_surprise,emo_fear,emo_anticipation,emo_disgust,label))


data_san<-subset(data_san, select=c(sentiStrength_pos,sentiStrength_neg,sentiStrength_neu,swn3_negativeness,
                                    swn3_positiveness,afinn_positiveness,
                                  afinn_negativeness,supervised_polarity,lex_positive_words,
                                  lex_negative_words,emo_joy,emo_trust,emo_sadness,emo_anger,
                                  emo_surprise,emo_fear,emo_anticipation,emo_disgust,label))


# Creamos un dataset de neutralidad 
neu_data_ts<-subset(data_ts, select=c(sentiStrength_pos,sentiStrength_neg,sentiStrength_neu,swn3_negativeness,
                                      swn3_positiveness,afinn_positiveness,
                                          afinn_negativeness,supervised_polarity,lex_positive_words,
                                          lex_negative_words,emo_joy,emo_trust,emo_sadness,emo_anger,
                                          emo_surprise,emo_fear,emo_anticipation,emo_disgust))

neu_data_ts<-cbind(neu_data_ts,
                          neutral_label=ifelse(data_ts$label=="neutral","neutral","non_neutral"))



neu_data_san<-subset(data_san, select=c(sentiStrength_pos,sentiStrength_neg,sentiStrength_neu,
                                        swn3_negativeness,swn3_positiveness,afinn_positiveness,
                                      afinn_negativeness,supervised_polarity,lex_positive_words,
                                      lex_negative_words,emo_joy,emo_trust,emo_sadness,emo_anger,
                                      emo_surprise,emo_fear,emo_anticipation,emo_disgust))

neu_data_san<-cbind(neu_data_san,
                   neutral_label=ifelse(data_san$label=="neutral","neutral","non_neutral"))

#Dataset balanceado
#Para ts hay 139 neutrales y 359 no neutrales
non_neu_ts<-neu_data_ts[neu_data_ts$neutral_label=="non_neutral",]
non_neu_ts<-non_neu_ts[sample(1:dim(non_neu_ts)[1],size=139,replace=F),]

neu_data_ts<-neu_data_ts[c(as.numeric(row.names(non_neu_ts)),
                  which(neu_data_ts$neutral_label=="neutral")),]

neu_data_ts<-neu_data_ts[order(as.numeric(rownames(neu_data_ts))),]

#Para Sa
neu_san<-neu_data_san[neu_data_san$neutral_label=="neutral",]
neu_san<-neu_san[sample(1:dim(neu_san)[1],size=1196,replace=F),]
neu_data_san<-neu_data_san[c(as.numeric(row.names(neu_san)),
                           which(neu_data_san$neutral_label=="non_neutral")),]
neu_data_san<-neu_data_san[order(as.numeric(rownames(neu_data_san))),]


#Perfomance de Twitter Sentiment sobre los datasets
neutral_feature_ts<-ifelse(neu_data_ts$supervised_polarity=="neutral","neutral","non_neutral")
super_neu_perf_ts<-my_performance(neutral_feature_ts,neu_data_ts$neutral_label)



neutral_feature_san<-ifelse(neu_data_san$supervised_polarity=="neutral","neutral","non_neutral")
super_neu_perf_san<-my_performance(neutral_feature_san,neu_data_san$neutral_label)

neutral_strength_ts<-ifelse(neu_data_ts$sentiStrength_neu=="neutral","neutral","non_neutral")
strength_neu_perf_ts<-my_performance(neutral_strength_ts,neu_data_ts$neutral_label)


neutral_strength_san<-ifelse(neu_data_san$sentiStrength_neu=="neutral","neutral","non_neutral")
strength_neu_perf_san<-my_performance(neutral_strength_san,neu_data_san$neutral_label)



                      
# Datasets de polaridad
pol_data_ts<-subset(data_ts,label!="neutral", select=c(sentiStrength_pos,sentiStrength_neg,sentiStrength_neu,
                                                       swn3_negativeness,swn3_positiveness,afinn_positiveness,
                                                       afinn_negativeness,supervised_polarity,lex_positive_words,
                                                       lex_negative_words,emo_joy,emo_trust,emo_sadness,emo_anger,
                                                       emo_surprise,emo_fear,emo_anticipation,emo_disgust,label))

pol_data_ts$label<-as.factor(as.character(pol_data_ts$label))

#Balanceamos
pos_ts<-pol_data_ts[pol_data_ts$label=="positive",]
pos_ts<-pos_ts[sample(1:dim(pos_ts)[1],size=177,replace=F),]
pol_data_ts<-rbind(pol_data_ts[pol_data_ts$label=="negative",],pos_ts)
pol_data_ts<-pol_data_ts[order(as.numeric(rownames(pol_data_ts))),]



pol_data_san<-subset(data_san,label!="neutral", select=c(sentiStrength_pos,sentiStrength_neg,sentiStrength_neu,
                                                         swn3_negativeness,swn3_positiveness,afinn_positiveness,
                                                       afinn_negativeness,supervised_polarity,lex_positive_words,
                                                       lex_negative_words,emo_joy,emo_trust,emo_sadness,emo_anger,
                                                       emo_surprise,emo_fear,emo_anticipation,emo_disgust,label))

pol_data_san$label<-as.factor(as.character(pol_data_san$label))


neg_san<-pol_data_san[pol_data_san$label=="negative",]
neg_san<-neg_san[sample(1:dim(neg_san)[1],size=560,replace=F),]
pol_data_san<-rbind(pol_data_san[pol_data_san$label=="positive",],neg_san)
pol_data_san<-pol_data_san[order(as.numeric(rownames(pol_data_san))),]



sentiStrength_pol_ts<-ifelse(pol_data_ts$sentiStrength_pos+pol_data_ts$sentiStrength_neg>0,
       "positive","negative")


sentiStrength_pol_san<-ifelse(pol_data_san$sentiStrength_pos+pol_data_san$sentiStrength_neg>0,
                              "positive","negative")

sentiStrength_pol_perf_ts<-my_performance(predicted=sentiStrength_pol_ts,
                                          real=pol_data_ts$label)

sentiStrength_pol_perf_san<-my_performance(predicted=sentiStrength_pol_san,
                                           real=pol_data_san$label)




afinn_pol_ts<-ifelse(pol_data_ts$afinn_positiveness+pol_data_ts$afinn_negativeness>0,
                             "positive","negative")

afinn_pol_san<-ifelse(pol_data_san$afinn_positiveness+pol_data_san$afinn_negativeness>0,
                     "positive","negative")

afinn_pol_perf_ts<-my_performance(predicted=afinn_pol_ts,
                                          real=pol_data_ts$label)

afinn_pol_perf_san<-my_performance(predicted=afinn_pol_san,
                                           real=pol_data_san$label)








table(predicted=pol_data_ts$supervised_polarity,real=pol_data_ts$label)
table(predicted=pol_data_san$supervised_polarity,real=pol_data_san$label)

#Análisis Exploratorio
source(file="explore.R",echo=T)

# Clasificador de objetividad-subjectividad
source(file="subject_classi.R",echo=T)



                      

                      
                      
                      
                      
#                      gamma = 10^(-6:-3), cost = 10^(1:2))


datos<-cbind(datos,is_neutral=as.factor(datos$label=="neutral"))
form<-formula(is_neutral~supervised_polarity+swn3_neutral_words+sentiStrength_neg+
  swn3_negativeness+sentiStrength_pos+afinn_positiveness+afinn_negativeness)

form2<-formula(label~sentiStrength_pos) 

arb<-rpart(form,data=datos)
rpart.plot(arb)




mod<-obj$best.model



objt <-tune.rpart(formula=form, 
                  data = datos, minsplit=c(3,5,10), 
                  cp=seq(from=0.001,to=2,by=0.1),maxdepth=c(1,5))

best.tree <-objt$best.model
rpart.plot(best.tree)
predt<-predict(best.tree)



