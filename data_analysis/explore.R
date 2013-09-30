
# Análisis del dataset y selección de atributos

# Calcula la informacion mutua entre las variables de un data.frame
mutual_info<-function(dataset){
  require("entropy")
  n<-length(dataset)
  mat<-matrix(nrow=n,ncol=n,dimnames=list(names(dataset),names(dataset)))
  variables<-names(dataset)
  for(i in 1:n){
    for(j in 1:i){
      mutual<-mi.empirical(rbind(data[[i]],data[[j]]))
      mat[i,j]<-mutual
      mat[j,i]<-mutual
    }
  }
  return(mat)  
}



#Feature Rankings

# La ganancia de informacion entre todos los atributos
feat_list<-paste(names(neutrality_dataset),"~.",sep="")
formulas<-sapply(feat_list,as.formula)
info.gains<-lapply(X=formulas,FUN=information.gain,data=neutrality_dataset)





neu_info_gain_san<-information.gain(neutral_label~.,data=neu_data_san)
neu_gain_ratio_san<-gain.ratio(neutral_label~.,data=neu_data_san)
neu_symme_uncer_san<-symmetrical.uncertainty(neutral_label~.,data=neu_data_san)
neu_chi_squared_san<-chi.squared(neutral_label~.,data=neu_data_san)


neu_features_info_san<-data.frame(feature=row.names(neu_info_gain_san),
                                  info.gain=neu_info_gain_san$attr_importance,
                                  gain.ratio=neu_gain_ratio_san$attr_importance,
                                  symme_uncertainty=neu_symme_uncer_san$attr_importance,
                                  chi.squared=neu_chi_squared_san$attr_importance)

xtable(neu_features_info_san,digits=3,caption="Feature Rankings for Subjectivity Classification Sanders Dataset")


neu_info_gain_ts<-information.gain(neutral_label~.,data=neu_data_ts)
neu_gain_ratio_ts<-gain.ratio(neutral_label~.,data=neu_data_ts)
neu_symme_uncer_ts<-symmetrical.uncertainty(neutral_label~.,data=neu_data_ts)
neu_chi_squared_ts<-chi.squared(neutral_label~.,data=neu_data_ts)


neu_features_info_ts<-data.frame(feature=row.names(neu_info_gain_ts),
                                 info.gain=neu_info_gain_ts$attr_importance,
                                 gain.ratio=neu_gain_ratio_ts$attr_importance,
                                 symme_uncertainty=neu_symme_uncer_ts$attr_importance,
                                 chi.squared=neu_chi_squared_ts$attr_importance)

xtable(neu_features_info_ts,digits=3,caption="Feature Rankings for Subjectivity Classification Twitter Sentiment Dataset")



pol_info_gain_san<-information.gain(label~.,data=pol_data_san)
pol_gain_ratio_san<-gain.ratio(label~.,data=pol_data_san)
pol_symme_uncer_san<-symmetrical.uncertainty(label~.,data=pol_data_san)
pol_chi_squared_san<-chi.squared(label~.,data=pol_data_san)

pol_features_info_san<-data.frame(feature=row.names(pol_info_gain_san),
                                  info.gain=pol_info_gain_san$attr_importance,
                              gain.ratio=pol_gain_ratio_san$attr_importance,
                              symme_uncertainty=pol_symme_uncer_san$attr_importance,
                              chi.squared=pol_chi_squared_san$attr_importance)

xtable(pol_features_info_san,digits=3,caption="Feature Rankings for Polarity Classification Sanders Dataset")



pol_info_gain_ts<-information.gain(label~.,data=pol_data_ts)
pol_gain_ratio_ts<-gain.ratio(label~.,data=pol_data_ts)
pol_symme_uncer_ts<-symmetrical.uncertainty(label~.,data=pol_data_ts)
pol_chi_squared_ts<-chi.squared(label~.,data=pol_data_ts)

pol_features_info_ts<-data.frame(feature=row.names(pol_info_gain_ts),
                                 info.gain=pol_info_gain_ts$attr_importance,
                                 gain.ratio=pol_gain_ratio_ts$attr_importance,
                                 symme_uncertainty=pol_symme_uncer_ts$attr_importance,
                                 chi.squared=pol_chi_squared_ts$attr_importance)

xtable(pol_features_info_ts,digits=3,caption="Feature Rankings for Polarity Classification Twitter Sentiment Dataset")



info_gain_full<-data.frame(feature=row.names(neu_info_gain_san),
                           neu_info_gain_ts=neu_info_gain_ts$attr_importance,
                           neu_info_gain_san=neu_info_gain_san$attr_importance,
                           pol_info_gain_ts=pol_info_gain_ts$attr_importance,
                           pol_info_gain_san=pol_info_gain_san$attr_importance)
                                  
xtable(info_gain_full,digits=3,caption="Feature Information Gain for each Classification Task")


# conjunto de atributos usando consistency
neu_cons_feat_san<-consistency(neutral_label~.,data=neu_data_san)
neu_cfs_feat_san<- cfs(neutral_label~.,data=neu_data_san) # Hace best-first usando entropia y correlacion

neu_data_cons_san<-cbind(subset(neu_data_san,select=neu_cons_feat_san),neutral_label=neu_data_san$neutral_label)
neu_data_cfs_san<-cbind(subset(neu_data_san,select=neu_cfs_feat_san),neutral_label=neu_data_san$neutral_label)


neu_cons_feat_ts<-consistency(neutral_label~.,data=neu_data_ts)
neu_cfs_feat_ts<- cfs(neutral_label~.,data=neu_data_ts) # Hace best-first utsdo entropia y correlacion

neu_data_cons_ts<-cbind(subset(neu_data_ts,select=neu_cons_feat_ts),neutral_label=neu_data_ts$neutral_label)
neu_data_cfs_ts<-cbind(subset(neu_data_ts,select=neu_cfs_feat_ts),neutral_label=neu_data_ts$neutral_label)


pol_cons_feat_san<-consistency(label~.,data=pol_data_san)
pol_cfs_feat_san <- cfs(label~.,data=pol_data_san) # Hace best-first usando entropia y correlacion

pol_data_cons_san<-cbind(subset(pol_data_san,select=pol_cons_feat_san),
                          label=pol_data_san$label)
pol_data_cfs_san<-cbind(subset(pol_data_san,select=pol_cfs_feat_san),
                         label=pol_data_san$label)

pol_cons_feat_ts<-consistency(label~.,data=pol_data_ts)
pol_cfs_feat_ts <- cfs(label~.,data=pol_data_ts) # Hace best-first utsdo entropia y correlacion

pol_data_cons_ts<-cbind(subset(pol_data_ts,select=pol_cons_feat_ts),
                        label=pol_data_ts$label)
pol_data_cfs_ts<-cbind(subset(pol_data_ts,select=pol_cfs_feat_ts),
                       label=pol_data_ts$label)


# Creamos subconjuntos de atributos de Strength, de Polaridad y de Emocion

strength_feat<-c("sentiStrength_pos","sentiStrength_neg","afinn_positiveness",
                 "afinn_negativeness","swn3_negativeness","swn3_positiveness")

polarity_feat<-c("sentiStrength_neu","supervised_polarity","lex_positive_words",
                 "lex_negative_words")

emotion_feat<-c("emo_joy","emo_trust","emo_sadness",
                 "emo_anger","emo_surprise","emo_fear","emo_anticipation","emo_disgust")

neu_data_strength_san<-cbind(subset(neu_data_san,select=strength_feat),
                             neutral_label=neu_data_san$neutral_label)

neu_data_polarity_san<-cbind(subset(neu_data_san,select=polarity_feat),
                             neutral_label=neu_data_san$neutral_label)

neu_data_emotion_san<-cbind(subset(neu_data_san,select=emotion_feat),
                             neutral_label=neu_data_san$neutral_label)

neu_data_strength_ts<-cbind(subset(neu_data_ts,select=strength_feat),
                            neutral_label=neu_data_ts$neutral_label)

neu_data_polarity_ts<-cbind(subset(neu_data_ts,select=polarity_feat),
                            neutral_label=neu_data_ts$neutral_label)

neu_data_emotion_ts<-cbind(subset(neu_data_ts,select=emotion_feat),
                           neutral_label=neu_data_ts$neutral_label)

pol_data_strength_san<-cbind(subset(pol_data_san,select=strength_feat),
                         label=pol_data_san$label)

pol_data_polarity_san<-cbind(subset(pol_data_san,select=polarity_feat),
                             label=pol_data_san$label)

pol_data_emotion_san<-cbind(subset(pol_data_san,select=emotion_feat),
                             label=pol_data_san$label)

pol_data_strength_ts<-cbind(subset(pol_data_ts,select=strength_feat),
                            label=pol_data_ts$label)

pol_data_polarity_ts<-cbind(subset(pol_data_ts,select=polarity_feat),
                            label=pol_data_ts$label)

pol_data_emotion_ts<-cbind(subset(pol_data_ts,select=emotion_feat),
                           label=pol_data_ts$label)




