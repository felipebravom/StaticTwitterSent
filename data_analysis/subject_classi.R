

bay_neu_full_pred_san<-mycv(10,neu_data_san,"neutral_label",naiveBayes)
bay_neu_full_perf_san<-my_performance(bay_neu_full_pred_san$predicted,
                                     bay_neu_full_pred_san$neutral_label)

bay_neu_full_pred_ts<-mycv(10,neu_data_ts,"neutral_label",naiveBayes)
bay_neu_full_perf_ts<-my_performance(bay_neu_full_pred_ts$predicted,
                                     bay_neu_full_pred_ts$neutral_label)



bay_neu_cons_pred_san<-mycv(10,neu_data_cons_san,"neutral_label",naiveBayes)
bay_neu_cons_perf_san<-my_performance(bay_neu_cons_pred_san$predicted,
                                      bay_neu_cons_pred_san$neutral_label)


bay_neu_cons_pred_ts<-mycv(10,neu_data_cons_ts,"neutral_label",naiveBayes)
bay_neu_cons_perf_ts<-my_performance(bay_neu_cons_pred_ts$predicted,
                                     bay_neu_cons_pred_ts$neutral_label)


bay_neu_cfs_pred_san<-mycv(10,neu_data_cfs_san,"neutral_label",naiveBayes)
bay_neu_cfs_perf_san<-my_performance(bay_neu_cfs_pred_san$predicted,
                                      bay_neu_cfs_pred_san$neutral_label)

bay_neu_cfs_pred_ts<-mycv(10,neu_data_cfs_ts,"neutral_label",naiveBayes)
bay_neu_cfs_perf_ts<-my_performance(bay_neu_cfs_pred_ts$predicted,
                                     bay_neu_cfs_pred_ts$neutral_label)


bay_neu_strength_pred_san<-mycv(10,neu_data_strength_san,"neutral_label",naiveBayes)
bay_neu_strength_perf_san<-my_performance(bay_neu_strength_pred_san$predicted,
                                          bay_neu_strength_pred_san$neutral_label)

bay_neu_strength_pred_ts<-mycv(10,neu_data_strength_ts,"neutral_label",naiveBayes)
bay_neu_strength_perf_ts<-my_performance(bay_neu_strength_pred_ts$predicted,
                                         bay_neu_strength_pred_ts$neutral_label)


bay_neu_polarity_pred_san<-mycv(10,neu_data_polarity_san,"neutral_label",naiveBayes)
bay_neu_polarity_perf_san<-my_performance(bay_neu_polarity_pred_san$predicted,
                                          bay_neu_polarity_pred_san$neutral_label)

bay_neu_polarity_pred_ts<-mycv(10,neu_data_polarity_ts,"neutral_label",naiveBayes)
bay_neu_polarity_perf_ts<-my_performance(bay_neu_polarity_pred_ts$predicted,
                                         bay_neu_polarity_pred_ts$neutral_label)

bay_neu_emotion_pred_san<-mycv(10,neu_data_emotion_san,"neutral_label",naiveBayes)
bay_neu_emotion_perf_san<-my_performance(bay_neu_emotion_pred_san$predicted,
                                         bay_neu_emotion_pred_san$neutral_label)

bay_neu_emotion_pred_ts<-mycv(10,neu_data_emotion_ts,"neutral_label",naiveBayes)
bay_neu_emotion_perf_ts<-my_performance(bay_neu_emotion_pred_ts$predicted,
                                        bay_neu_emotion_pred_ts$neutral_label)


bay_pol_full_pred_san<-mycv(10,pol_data_san,"label",naiveBayes)
bay_pol_full_perf_san<-my_performance(bay_pol_full_pred_san$predicted,
                                     bay_pol_full_pred_san$label)

bay_pol_full_pred_ts<-mycv(10,pol_data_ts,"label",naiveBayes)
bay_pol_full_perf_ts<-my_performance(bay_pol_full_pred_ts$predicted,
                                     bay_pol_full_pred_ts$label)

bay_pol_cons_pred_san<-mycv(10,pol_data_cons_san,"label",naiveBayes)
bay_pol_cons_perf_san<-my_performance(bay_pol_cons_pred_san$predicted,
                                      bay_pol_cons_pred_san$label)

bay_pol_cons_pred_ts<-mycv(10,pol_data_cons_ts,"label",naiveBayes)
bay_pol_cons_perf_ts<-my_performance(bay_pol_cons_pred_ts$predicted,
                                     bay_pol_cons_pred_ts$label)


bay_pol_cfs_pred_san<-mycv(10,pol_data_cfs_san,"label",naiveBayes)
bay_pol_cfs_perf_san<-my_performance(bay_pol_cfs_pred_san$predicted,
                                     bay_pol_cfs_pred_san$label)

bay_pol_cfs_pred_ts<-mycv(10,pol_data_cfs_ts,"label",naiveBayes)
bay_pol_cfs_perf_ts<-my_performance(bay_pol_cfs_pred_ts$predicted,
                                    bay_pol_cfs_pred_ts$label)




bay_pol_strength_pred_san<-mycv(10,pol_data_strength_san,"label",naiveBayes)
bay_pol_strength_perf_san<-my_performance(bay_pol_strength_pred_san$predicted,
                                          bay_pol_strength_pred_san$label)

bay_pol_strength_pred_ts<-mycv(10,pol_data_strength_ts,"label",naiveBayes)
bay_pol_strength_perf_ts<-my_performance(bay_pol_strength_pred_ts$predicted,
                                         bay_pol_strength_pred_ts$label)


bay_pol_polarity_pred_san<-mycv(10,pol_data_polarity_san,"label",naiveBayes)
bay_pol_polarity_perf_san<-my_performance(bay_pol_polarity_pred_san$predicted,
                                          bay_pol_polarity_pred_san$label)

bay_pol_polarity_pred_ts<-mycv(10,pol_data_polarity_ts,"label",naiveBayes)
bay_pol_polarity_perf_ts<-my_performance(bay_pol_polarity_pred_ts$predicted,
                                         bay_pol_polarity_pred_ts$label)

bay_pol_emotion_pred_san<-mycv(10,pol_data_emotion_san,"label",naiveBayes)
bay_pol_emotion_perf_san<-my_performance(bay_pol_emotion_pred_san$predicted,
                                         bay_pol_emotion_pred_san$label)

bay_pol_emotion_pred_ts<-mycv(10,pol_data_emotion_ts,"label",naiveBayes)
bay_pol_emotion_perf_ts<-my_performance(bay_pol_emotion_pred_ts$predicted,
                                        bay_pol_emotion_pred_ts$label)



svm_neu_full_san <- tune(method=svm, neutral_label~., 
                         data = neu_data_san,ranges = list(gamma = 2^(-7:3), 
                                                                cost = 2^(-3:5)),
                         tunecontrol = tune.control(sampling = "cross"),cross=10)


svm_neu_cfs_san <- tune(method=svm, neutral_label~., 
                        data = neu_data_cfs_san,ranges = list(gamma = 2^(-7:3), 
                                                              cost = 2^(-3:5)),
                        tunecontrol = tune.control(sampling = "cross"),cross=10)

svm_neu_cons_san <- tune(method=svm, neutral_label~., 
                         data = neu_data_cons_san,ranges = list(gamma = 2^(-7:3), 
                                                                cost = 2^(-3:5)),
                         tunecontrol = tune.control(sampling = "cross"),cross=10)


svm_neu_polarity_san <- tune(method=svm, neutral_label~., 
                             data = neu_data_polarity_san,ranges = list(gamma = 2^(-7:3), 
                                                                        cost = 2^(-3:5)),
                             tunecontrol = tune.control(sampling = "cross"),cross=10)

svm_neu_strength_san <- tune(method=svm, neutral_label~., 
                             data = neu_data_strength_san,ranges = list(gamma = 2^(-7:3), 
                                                                        cost = 2^(-3:5)),
                             tunecontrol = tune.control(sampling = "cross"),cross=10)


svm_neu_emotion_san <- tune(method=svm, neutral_label~., 
                            data = neu_data_emotion_san,ranges = list(gamma = 2^(-7:3), 
                                                                      cost = 2^(-3:5)),
                            tunecontrol = tune.control(sampling = "cross"),cross=10)



svm_pol_full_san <- tune(method=svm, label~., 
                         data = pol_data_san,ranges = list(gamma = 2^(-7:3), 
                                                           cost = 2^(-3:5)),
                         tunecontrol = tune.control(sampling = "cross"),cross=10)

svm_pol_cfs_san <- tune(method=svm, label~., 
                        data = pol_data_cfs_san,ranges = list(gamma = 2^(-7:3), 
                                                              cost = 2^(-3:5)),
                        tunecontrol = tune.control(sampling = "cross"),cross=10)

svm_pol_cons_san <- tune(method=svm, label~., 
                         data = pol_data_cons_san,ranges = list(gamma = 2^(-7:3), 
                                                                cost = 2^(-3:5)),
                         tunecontrol = tune.control(sampling = "cross"),cross=10)


svm_pol_polarity_san <- tune(method=svm, label~., 
                         data = pol_data_polarity_san,ranges = list(gamma = 2^(-7:3), 
                                                                cost = 2^(-3:5)),
                         tunecontrol = tune.control(sampling = "cross"),cross=10)


svm_pol_strength_san <- tune(method=svm, label~., 
                             data = pol_data_strength_san,ranges = list(gamma = 2^(-7:3), 
                                                                        cost = 2^(-3:5)),
                             tunecontrol = tune.control(sampling = "cross"),cross=10)

svm_pol_emotion_san <- tune(method=svm, label~., 
                             data = pol_data_emotion_san,ranges = list(gamma = 2^(-7:3), 
                                                                        cost = 2^(-3:5)),
                             tunecontrol = tune.control(sampling = "cross"),cross=10)



svm_neu_full_ts <- tune(method=svm, neutral_label~., 
                        data = neu_data_ts,ranges = list(gamma = 2^(-7:3), 
                                                              cost = 2^(-3:5)),
                        tunecontrol = tune.control(sampling = "cross"),cross=10)


svm_neu_cfs_ts <- tune(method=svm, neutral_label~., 
                       data = neu_data_cfs_ts,ranges = list(gamma = 2^(-7:3), 
                                                            cost = 2^(-3:5)),
                       tunecontrol = tune.control(sampling = "cross"),cross=10)

svm_neu_cons_ts <- tune(method=svm, neutral_label~., 
                        data = neu_data_cons_ts,ranges = list(gamma = 2^(-7:3), 
                                                              cost = 2^(-3:5)),
                        tunecontrol = tune.control(sampling = "cross"),cross=10)


svm_neu_polarity_ts <- tune(method=svm, neutral_label~., 
                            data = neu_data_polarity_ts,ranges = list(gamma = 2^(-7:3), 
                                                                      cost = 2^(-3:5)),
                            tunecontrol = tune.control(sampling = "cross"),cross=10)

svm_neu_strength_ts <- tune(method=svm, neutral_label~., 
                            data = neu_data_strength_ts,ranges = list(gamma = 2^(-7:3), 
                                                                      cost = 2^(-3:5)),
                            tunecontrol = tune.control(sampling = "cross"),cross=10)


svm_neu_emotion_ts <- tune(method=svm, neutral_label~., 
                           data = neu_data_emotion_ts,ranges = list(gamma = 2^(-7:3), 
                                                                    cost = 2^(-3:5)),
                           tunecontrol = tune.control(sampling = "cross"),cross=10)




svm_pol_full_ts <- tune(method=svm, label~., 
                        data = pol_data_ts,ranges = list(gamma = 2^(-7:3), 
                                                         cost = 2^(-3:5)),
                        tunecontrol = tune.control(sampling = "cross"),cross=10)

svm_pol_cfs_ts <- tune(method=svm, label~., 
                       data = pol_data_cfs_ts,ranges = list(gamma = 2^(-7:3), 
                                                            cost = 2^(-3:5)),
                       tunecontrol = tune.control(sampling = "cross"),cross=10)

svm_pol_cons_ts <- tune(method=svm, label~., 
                        data = pol_data_cons_ts,ranges = list(gamma = 2^(-7:3), 
                                                              cost = 2^(-3:5)),
                        tunecontrol = tune.control(sampling = "cross"),cross=10)


svm_pol_polarity_ts <- tune(method=svm, label~., 
                            data = pol_data_polarity_ts,ranges = list(gamma = 2^(-7:3), 
                                                                      cost = 2^(-3:5)),
                            tunecontrol = tune.control(sampling = "cross"),cross=10)


svm_pol_strength_ts <- tune(method=svm, label~., 
                            data = pol_data_strength_ts,ranges = list(gamma = 2^(-7:3), 
                                                                      cost = 2^(-3:5)),
                            tunecontrol = tune.control(sampling = "cross"),cross=10)

svm_pol_emotion_ts <- tune(method=svm, label~., 
                           data = pol_data_emotion_ts,ranges = list(gamma = 2^(-7:3), 
                                                                    cost = 2^(-3:5)),
                           tunecontrol = tune.control(sampling = "cross"),cross=10)





svm_neu_full_data_san<-mycv(10,neu_data_san,"neutral_label",svm, kernel="radial", 
          gamma= 0.25, cost=16)
svm_neu_full_perf_san<-my_performance(svm_neu_full_data_san$predicted,
                                     svm_neu_full_data_san$neutral_label)


svm_neu_cons_data_san<-mycv(10,neu_data_cons_san,"neutral_label",svm, kernel="radial", 
                            gamma= 0.25, cost=2)
svm_neu_cons_perf_san<-my_performance(svm_neu_cons_data_san$predicted,
                                      svm_neu_cons_data_san$neutral_label)

svm_neu_cfs_data_san<-mycv(10,neu_data_cfs_san,"neutral_label",svm, kernel="radial", 
                            gamma= 8, cost=0.5)
svm_neu_cfs_perf_san<-my_performance(svm_neu_cfs_data_san$predicted,
                                      svm_neu_cfs_data_san$neutral_label)


svm_neu_polarity_data_san<-mycv(10,neu_data_polarity_san,"neutral_label",svm, kernel="radial", 
                           gamma= 0.015625 , cost=16)
svm_neu_polarity_perf_san<-my_performance(svm_neu_polarity_data_san$predicted,
                                     svm_neu_polarity_data_san$neutral_label)


svm_neu_strength_data_san<-mycv(10,neu_data_strength_san,"neutral_label",svm, kernel="radial", 
                                gamma= 8 , cost=32)
svm_neu_strength_perf_san<-my_performance(svm_neu_strength_data_san$predicted,
                                          svm_neu_strength_data_san$neutral_label)


svm_neu_emotion_data_san<-mycv(10,neu_data_emotion_san,"neutral_label",svm, kernel="radial", 
                                gamma= 0.25 , cost=0.125)
svm_neu_emotion_perf_san<-my_performance(svm_neu_emotion_data_san$predicted,
                                          svm_neu_emotion_data_san$neutral_label)


svm_pol_full_data_san<-mycv(10,pol_data_san,"label",svm, kernel="radial", 
                            gamma= 0.0078125, cost=4)
svm_pol_full_perf_san<-my_performance(svm_pol_full_data_san$predicted,
                                      svm_pol_full_data_san$label)


svm_pol_cons_data_san<-mycv(10,pol_data_cons_san,"label",svm, kernel="radial", 
                            gamma=0.015625, cost=2)
svm_pol_cons_perf_san<-my_performance(svm_pol_cons_data_san$predicted,
                                      svm_pol_cons_data_san$label)

svm_pol_cfs_data_san<-mycv(10,pol_data_cfs_san,"label",svm, kernel="radial", 
                           gamma= 0.015625, cost=4)
svm_pol_cfs_perf_san<-my_performance(svm_pol_cfs_data_san$predicted,
                                     svm_pol_cfs_data_san$label)

svm_pol_polarity_data_san<-mycv(10,pol_data_polarity_san,"label",svm, kernel="radial", 
                           gamma=0.125, cost=8)
svm_pol_polarity_perf_san<-my_performance(svm_pol_polarity_data_san$predicted,
                                     svm_pol_polarity_data_san$label)

svm_pol_strength_data_san<-mycv(10,pol_data_strength_san,"label",svm, kernel="radial", 
                                gamma=0.0625, cost=0.125)
svm_pol_strength_perf_san<-my_performance(svm_pol_strength_data_san$predicted,
                                          svm_pol_strength_data_san$label)

svm_pol_emotion_data_san<-mycv(10,pol_data_emotion_san,"label",svm, kernel="radial", 
                               gamma=0.125, cost=2)
svm_pol_emotion_perf_san<-my_performance(svm_pol_emotion_data_san$predicted,
                                         svm_pol_emotion_data_san$label)





svm_neu_full_data_ts<-mycv(10,neu_data_ts,"neutral_label",svm, kernel="radial", 
                           gamma= 0.03125, cost=1)
svm_neu_full_perf_ts<-my_performance(svm_neu_full_data_ts$predicted,
                                     svm_neu_full_data_ts$neutral_label)


svm_neu_cons_data_ts<-mycv(10,neu_data_cons_ts,"neutral_label",svm, kernel="radial", 
                           gamma= 0.125, cost=0.125)
svm_neu_cons_perf_ts<-my_performance(svm_neu_cons_data_ts$predicted,
                                     svm_neu_cons_data_ts$neutral_label)

svm_neu_cfs_data_ts<-mycv(10,neu_data_cfs_ts,"neutral_label",svm, kernel="radial", 
                          gamma= 0.25, cost=0.25)
svm_neu_cfs_perf_ts<-my_performance(svm_neu_cfs_data_ts$predicted,
                                    svm_neu_cfs_data_ts$neutral_label)

svm_neu_polarity_data_ts<-mycv(10,neu_data_polarity_ts,"neutral_label",svm, kernel="radial", 
                          gamma= 0.0625, cost=0.25)
svm_neu_polarity_perf_ts<-my_performance(svm_neu_polarity_data_ts$predicted,
                                    svm_neu_polarity_data_ts$neutral_label)

svm_neu_strength_data_ts<-mycv(10,neu_data_strength_ts,"neutral_label",svm, kernel="radial", 
                               gamma= 0.0625, cost=0.25)
svm_neu_strength_perf_ts<-my_performance(svm_neu_strength_data_ts$predicted,
                                         svm_neu_strength_data_ts$neutral_label)

svm_neu_emotion_data_ts<-mycv(10,neu_data_emotion_ts,"neutral_label",svm, kernel="radial", 
                               gamma= 0.25, cost=0.25)
svm_neu_emotion_perf_ts<-my_performance(svm_neu_emotion_data_ts$predicted,
                                         svm_neu_emotion_data_ts$neutral_label)



svm_pol_full_data_ts<-mycv(10,pol_data_ts,"label",svm, kernel="radial", 
                           gamma=0.015625, cost=0.125)
svm_pol_full_perf_ts<-my_performance(svm_pol_full_data_ts$predicted,
                                     svm_pol_full_data_ts$label)


svm_pol_cons_data_ts<-mycv(10,pol_data_cons_ts,"label",svm, kernel="radial", 
                           gamma=0.03125, cost=2)
svm_pol_cons_perf_ts<-my_performance(svm_pol_cons_data_ts$predicted,
                                     svm_pol_cons_data_ts$label)

svm_pol_cfs_data_ts<-mycv(10,pol_data_cfs_ts,"label",svm, kernel="radial", 
                          gamma=0.03125, cost=16)
svm_pol_cfs_perf_ts<-my_performance(svm_pol_cfs_data_ts$predicted,
                                    svm_pol_cfs_data_ts$label)

svm_pol_polarity_data_ts<-mycv(10,pol_data_polarity_ts,"label",svm, kernel="radial", 
                          gamma=0.125, cost=4)
svm_pol_polarity_perf_ts<-my_performance(svm_pol_polarity_data_ts$predicted,
                                    svm_pol_polarity_data_ts$label)

svm_pol_strength_data_ts<-mycv(10,pol_data_strength_ts,"label",svm, kernel="radial", 
                               gamma=0.0078125, cost=0.5)
svm_pol_strength_perf_ts<-my_performance(svm_pol_strength_data_ts$predicted,
                                         svm_pol_strength_data_ts$label)

svm_pol_emotion_data_ts<-mycv(10,pol_data_emotion_ts,"label",svm, kernel="radial", 
                               gamma=0.03125, cost=16)
svm_pol_emotion_perf_ts<-my_performance(svm_pol_emotion_data_ts$predicted,
                                         svm_pol_emotion_data_ts$label)



rpart_neu_full_san <- tune.rpart(neutral_label~.,data = neu_data_san,
                             cp = c(0.002,0.005,0.01,0.015,0.02,0.03),
                             minsplit=seq(0,100,10))

rpart_neu_cfs_san <- tune.rpart(neutral_label~.,data = neu_data_cfs_san,
                                 cp = c(0.002,0.005,0.01,0.015,0.02,0.03),
                                 minsplit=seq(0,100,10))

rpart_neu_cons_san <- tune.rpart(neutral_label~.,data = neu_data_cons_san,
                                cp = c(0.002,0.005,0.01,0.015,0.02,0.03),
                                minsplit=seq(0,100,10))

rpart_neu_polarity_san <- tune.rpart(neutral_label~.,data = neu_data_polarity_san,
                                cp = c(0.002,0.005,0.01,0.015,0.02,0.03),
                                minsplit=seq(0,100,10))

rpart_neu_strength_san <- tune.rpart(neutral_label~.,data = neu_data_strength_san,
                                cp = c(0.002,0.005,0.01,0.015,0.02,0.03),
                                minsplit=seq(0,100,10))


rpart_neu_emotion_san <- tune.rpart(neutral_label~.,data = neu_data_emotion_san,
                                     cp = c(0.002,0.005,0.01,0.015,0.02,0.03),
                                     minsplit=seq(0,100,10))


rpart_neu_full_ts <- tune.rpart(neutral_label~.,data = neu_data_ts,
                                 cp = c(0.002,0.005,0.01,0.015,0.02,0.03),
                                 minsplit=seq(0,100,10))

rpart_neu_cfs_ts <- tune.rpart(neutral_label~.,data = neu_data_cfs_ts,
                               cp = c(0.002,0.005,0.01,0.015,0.02,0.03),
                               minsplit=seq(0,100,10))

rpart_neu_cons_ts <- tune.rpart(neutral_label~.,data = neu_data_cons_ts,
                                cp = c(0.002,0.005,0.01,0.015,0.02,0.03),
                                minsplit=seq(0,100,10))

rpart_neu_polarity_ts <- tune.rpart(neutral_label~.,data = neu_data_polarity_ts,
                                    cp = c(0.002,0.005,0.01,0.015,0.02,0.03),
                                    minsplit=seq(0,100,10))

rpart_neu_strength_ts <- tune.rpart(neutral_label~.,data = neu_data_strength_ts,
                                    cp = c(0.002,0.005,0.01,0.015,0.02,0.03),
                                    minsplit=seq(0,100,10))


rpart_neu_emotion_ts <- tune.rpart(neutral_label~.,data = neu_data_emotion_ts,
                                   cp = c(0.002,0.005,0.01,0.015,0.02,0.03),
                                   minsplit=seq(0,100,10))



rpart_neu_full_data_san<-mycv(10,neu_data_san,"neutral_label",rpart,minsplit=90,cp=0.002)
rpart_neu_full_perf_san<-my_performance(rpart_neu_full_data_san$predicted,
                                    rpart_neu_full_data_san$neutral_label)

rpart_neu_full_data_ts<-mycv(10,neu_data_ts,"neutral_label",rpart,minsplit=20,cp=0.002)
rpart_neu_full_perf_ts<-my_performance(rpart_neu_full_data_ts$predicted,
                                    rpart_neu_full_data_ts$neutral_label)


rpart_neu_cfs_data_san<-mycv(10,neu_data_cfs_san,"neutral_label",rpart,minsplit=0,cp=0.015)
rpart_neu_cfs_perf_san<-my_performance(rpart_neu_cfs_data_san$predicted,
                                      rpart_neu_cfs_data_san$neutral_label)

rpart_neu_cfs_data_ts<-mycv(10,neu_data_cfs_ts,"neutral_label",rpart,minsplit=20,cp=0.002)
rpart_neu_cfs_perf_ts<-my_performance(rpart_neu_cfs_data_ts$predicted,
                                       rpart_neu_cfs_data_ts$neutral_label)


rpart_neu_cons_data_san<-mycv(10,neu_data_cons_san,"neutral_label",rpart,minsplit=60,cp=0.02)
rpart_neu_cons_perf_san<-my_performance(rpart_neu_cons_data_san$predicted,
                                        rpart_neu_cons_data_san$neutral_label)

rpart_neu_cons_data_ts<-mycv(10,neu_data_cons_ts,"neutral_label",rpart,minsplit=90,cp=0.001)
rpart_neu_cons_perf_ts<-my_performance(rpart_neu_cons_data_ts$predicted,
                                       rpart_neu_cons_data_ts$neutral_label)

rpart_neu_polarity_data_san<-mycv(10,neu_data_polarity_san,"neutral_label",rpart,minsplit=0,cp=0.005)
rpart_neu_polarity_perf_san<-my_performance(rpart_neu_polarity_data_san$predicted,
                                            rpart_neu_polarity_data_san$neutral_label)

rpart_neu_polarity_data_ts<-mycv(10,neu_data_polarity_ts,"neutral_label",rpart,minsplit=90,cp=0.002)
rpart_neu_polarity_perf_ts<-my_performance(rpart_neu_polarity_data_ts$predicted,
                                           rpart_neu_polarity_data_ts$neutral_label)

rpart_neu_strength_data_san<-mycv(10,neu_data_strength_san,"neutral_label",rpart,minsplit=40,cp=0.002)
rpart_neu_strength_perf_san<-my_performance(rpart_neu_strength_data_san$predicted,
                                            rpart_neu_strength_data_san$neutral_label)

rpart_neu_strength_data_ts<-mycv(10,neu_data_strength_ts,"neutral_label",rpart,minsplit=50,cp=0.002)
rpart_neu_strength_perf_ts<-my_performance(rpart_neu_strength_data_ts$predicted,
                                           rpart_neu_strength_data_ts$neutral_label)

rpart_neu_emotion_data_san<-mycv(10,neu_data_emotion_san,"neutral_label",rpart,minsplit=0,cp=0.01)
rpart_neu_emotion_perf_san<-my_performance(rpart_neu_emotion_data_san$predicted,
                                           rpart_neu_emotion_data_san$neutral_label)

rpart_neu_emotion_data_ts<-mycv(10,neu_data_emotion_ts,"neutral_label",rpart,minsplit=70,cp=0.002)
rpart_neu_emotion_perf_ts<-my_performance(rpart_neu_emotion_data_ts$predicted,
                                          rpart_neu_emotion_data_ts$neutral_label)



rpart_pol_full_san <- tune.rpart(label~.,data = pol_data_san,
                                 cp = c(0.002,0.005,0.01,0.015,0.02,0.03),
                                 minsplit=seq(0,100,10))


rpart_pol_cfs_san <- tune.rpart(label~.,data = pol_data_cfs_san,
                                 cp = c(0.002,0.005,0.01,0.015,0.02,0.03),
                                 minsplit=seq(0,100,10))

rpart_pol_cons_san <- tune.rpart(label~.,data = pol_data_cons_san,
                                cp = c(0.002,0.005,0.01,0.015,0.02,0.03),
                                minsplit=seq(0,100,10))

rpart_pol_polarity_san <- tune.rpart(label~.,data = pol_data_polarity_san,
                                 cp = c(0.002,0.005,0.01,0.015,0.02,0.03),
                                 minsplit=seq(0,100,10))


rpart_pol_strength_san <- tune.rpart(label~.,data = pol_data_strength_san,
                                     cp = c(0.002,0.005,0.01,0.015,0.02,0.03),
                                     minsplit=seq(0,100,10))


rpart_pol_emotion_san <- tune.rpart(label~.,data = pol_data_emotion_san,
                                     cp = c(0.002,0.005,0.01,0.015,0.02,0.03),
                                     minsplit=seq(0,100,10))





rpart_pol_full_ts <- tune.rpart(label~.,data = pol_data_ts,
                                cp = c(0.002,0.005,0.01,0.015,0.02,0.03),
                                minsplit=seq(0,100,10))

rpart_pol_cfs_ts <- tune.rpart(label~.,data = pol_data_cfs_ts,
                               cp = c(0.002,0.005,0.01,0.015,0.02,0.03),
                               minsplit=seq(0,100,10))

rpart_pol_cons_ts <- tune.rpart(label~.,data = pol_data_cons_ts,
                                cp = c(0.002,0.005,0.01,0.015,0.02,0.03),
                                minsplit=seq(0,100,10))

rpart_pol_polarity_ts <- tune.rpart(label~.,data = pol_data_polarity_ts,
                                    cp = c(0.002,0.005,0.01,0.015,0.02,0.03),
                                    minsplit=seq(0,100,10))


rpart_pol_strength_ts <- tune.rpart(label~.,data = pol_data_strength_ts,
                                    cp = c(0.002,0.005,0.01,0.015,0.02,0.03),
                                    minsplit=seq(0,100,10))


rpart_pol_emotion_ts <- tune.rpart(label~.,data = pol_data_emotion_ts,
                                   cp = c(0.002,0.005,0.01,0.015,0.02,0.03),
                                   minsplit=seq(0,100,10))



rpart_pol_full_data_san<-mycv(10,pol_data_san,"label",rpart,minsplit=90,cp=0.01)
rpart_pol_full_perf_san<-my_performance(rpart_pol_full_data_san$predicted,
                                        rpart_pol_full_data_san$label)

rpart_pol_full_data_ts<-mycv(10,pol_data_ts,"label",rpart,minsplit=0,cp=0.005)
rpart_pol_full_perf_ts<-my_performance(rpart_pol_full_data_ts$predicted,
                                       rpart_pol_full_data_ts$label)


rpart_pol_cfs_data_san<-mycv(10,pol_data_cfs_san,"label",rpart,minsplit=0,cp=0.002)
rpart_pol_cfs_perf_san<-my_performance(rpart_pol_cfs_data_san$predicted,
                                        rpart_pol_cfs_data_san$label)

rpart_pol_cfs_data_ts<-mycv(10,pol_data_cfs_ts,"label",rpart,minsplit=10,cp=0.005)
rpart_pol_cfs_perf_ts<-my_performance(rpart_pol_cfs_data_ts$predicted,
                                       rpart_pol_cfs_data_ts$label)

rpart_pol_cons_data_san<-mycv(10,pol_data_cons_san,"label",rpart,minsplit=100,cp=0.002)
rpart_pol_cons_perf_san<-my_performance(rpart_pol_cons_data_san$predicted,
                                        rpart_pol_cons_data_san$label)

rpart_pol_cons_data_ts<-mycv(10,pol_data_cons_ts,"label",rpart,minsplit=10,cp=0.002)
rpart_pol_cons_perf_ts<-my_performance(rpart_pol_cons_data_ts$predicted,
                                       rpart_pol_cons_data_ts$label)

rpart_pol_polarity_data_san<-mycv(10,pol_data_polarity_san,"label",rpart,minsplit=50,cp=0.002)
rpart_pol_polarity_perf_san<-my_performance(rpart_pol_polarity_data_san$predicted,
                                            rpart_pol_polarity_data_san$label)

rpart_pol_polarity_data_ts<-mycv(10,pol_data_polarity_ts,"label",rpart,minsplit=30,cp=0.002)
rpart_pol_polarity_perf_ts<-my_performance(rpart_pol_polarity_data_ts$predicted,
                                           rpart_pol_polarity_data_ts$label)

rpart_pol_strength_data_san<-mycv(10,pol_data_strength_san,"label",rpart,minsplit=70,cp=0.05)
rpart_pol_strength_perf_san<-my_performance(rpart_pol_strength_data_san$predicted,
                                            rpart_pol_strength_data_san$label)

rpart_pol_strength_data_ts<-mycv(10,pol_data_strength_ts,"label",rpart,minsplit=0,cp=0.005)
rpart_pol_strength_perf_ts<-my_performance(rpart_pol_strength_data_ts$predicted,
                                           rpart_pol_strength_data_ts$label)

rpart_pol_emotion_data_san<-mycv(10,pol_data_emotion_san,"label",rpart,minsplit=0,cp=0.002)
rpart_pol_emotion_perf_san<-my_performance(rpart_pol_emotion_data_san$predicted,
                                           rpart_pol_emotion_data_san$label)

rpart_pol_emotion_data_ts<-mycv(10,pol_data_emotion_ts,"label",rpart,minsplit=10,cp=0.005)
rpart_pol_emotion_perf_ts<-my_performance(rpart_pol_emotion_data_ts$predicted,
                                          rpart_pol_emotion_data_ts$label)



j48_neu_full_data_san<-mycv(10,neu_data_san,"neutral_label",J48)
j48_neu_full_perf_san<-my_performance(j48_neu_full_data_san$predicted,
                                  j48_neu_full_data_san$neutral_label)


j48_neu_cons_data_san<-mycv(10,neu_data_cons_san,"neutral_label",J48)
j48_neu_cons_perf_san<-my_performance(j48_neu_cons_data_san$predicted,
                                      j48_neu_cons_data_san$neutral_label)

j48_neu_cfs_data_san<-mycv(10,neu_data_cfs_san,"neutral_label",J48)
j48_neu_cfs_perf_san<-my_performance(j48_neu_cfs_data_san$predicted,
                                     j48_neu_cfs_data_san$neutral_label)

j48_neu_polarity_data_san<-mycv(10,neu_data_polarity_san,"neutral_label",J48)
j48_neu_polarity_perf_san<-my_performance(j48_neu_polarity_data_san$predicted,
                                          j48_neu_polarity_data_san$neutral_label)

j48_neu_strength_data_san<-mycv(10,neu_data_strength_san,"neutral_label",J48)
j48_neu_strength_perf_san<-my_performance(j48_neu_strength_data_san$predicted,
                                          j48_neu_strength_data_san$neutral_label)

j48_neu_emotion_data_san<-mycv(10,neu_data_emotion_san,"neutral_label",J48)
j48_neu_emotion_perf_san<-my_performance(j48_neu_emotion_data_san$predicted,
                                         j48_neu_emotion_data_san$neutral_label)




j48_neu_full_data_ts<-mycv(10,neu_data_ts,"neutral_label",J48)
j48_neu_full_perf_ts<-my_performance(j48_neu_full_data_ts$predicted,
                                  j48_neu_full_data_ts$neutral_label)

j48_neu_cons_data_ts<-mycv(10,neu_data_cons_ts,"neutral_label",J48)
j48_neu_cons_perf_ts<-my_performance(j48_neu_cons_data_ts$predicted,
                                     j48_neu_cons_data_ts$neutral_label)

j48_neu_cfs_data_ts<-mycv(10,neu_data_cfs_ts,"neutral_label",J48)
j48_neu_cfs_perf_ts<-my_performance(j48_neu_cfs_data_ts$predicted,
                                    j48_neu_cfs_data_ts$neutral_label)

j48_neu_polarity_data_ts<-mycv(10,neu_data_polarity_ts,"neutral_label",J48)
j48_neu_polarity_perf_ts<-my_performance(j48_neu_polarity_data_ts$predicted,
                                         j48_neu_polarity_data_ts$neutral_label)

j48_neu_strength_data_ts<-mycv(10,neu_data_strength_ts,"neutral_label",J48)
j48_neu_strength_perf_ts<-my_performance(j48_neu_strength_data_ts$predicted,
                                         j48_neu_strength_data_ts$neutral_label)

j48_neu_emotion_data_ts<-mycv(10,neu_data_emotion_ts,"neutral_label",J48)
j48_neu_emotion_perf_ts<-my_performance(j48_neu_emotion_data_ts$predicted,
                                        j48_neu_emotion_data_ts$neutral_label)



j48_pol_full_data_san<-mycv(10,pol_data_san,"label",J48)
j48_pol_full_perf_san<-my_performance(j48_pol_full_data_san$predicted,
                                      j48_pol_full_data_san$label)

j48_pol_cons_data_san<-mycv(10,pol_data_cons_san,"label",J48)
j48_pol_cons_perf_san<-my_performance(j48_pol_cons_data_san$predicted,
                                      j48_pol_cons_data_san$label)

j48_pol_cfs_data_san<-mycv(10,pol_data_cfs_san,"label",J48)
j48_pol_cfs_perf_san<-my_performance(j48_pol_cfs_data_san$predicted,
                                     j48_pol_cfs_data_san$label)

j48_pol_polarity_data_san<-mycv(10,pol_data_polarity_san,"label",J48)
j48_pol_polarity_perf_san<-my_performance(j48_pol_polarity_data_san$predicted,
                                          j48_pol_polarity_data_san$label)

j48_pol_strength_data_san<-mycv(10,pol_data_strength_san,"label",J48)
j48_pol_strength_perf_san<-my_performance(j48_pol_strength_data_san$predicted,
                                          j48_pol_strength_data_san$label)

j48_pol_emotion_data_san<-mycv(10,pol_data_emotion_san,"label",J48)
j48_pol_emotion_perf_san<-my_performance(j48_pol_emotion_data_san$predicted,
                                         j48_pol_emotion_data_san$label)


j48_pol_full_data_ts<-mycv(10,pol_data_ts,"label",J48)
j48_pol_full_perf_ts<-my_performance(j48_pol_full_data_ts$predicted,
                                     j48_pol_full_data_ts$label)

j48_pol_cons_data_ts<-mycv(10,pol_data_cons_ts,"label",J48)
j48_pol_cons_perf_ts<-my_performance(j48_pol_cons_data_ts$predicted,
                                     j48_pol_cons_data_ts$label)

j48_pol_cfs_data_ts<-mycv(10,pol_data_cfs_ts,"label",J48)
j48_pol_cfs_perf_ts<-my_performance(j48_pol_cfs_data_ts$predicted,
                                    j48_pol_cfs_data_ts$label)

j48_pol_polarity_data_ts<-mycv(10,pol_data_polarity_ts,"label",J48)
j48_pol_polarity_perf_ts<-my_performance(j48_pol_polarity_data_ts$predicted,
                                         j48_pol_polarity_data_ts$label)

j48_pol_strength_data_ts<-mycv(10,pol_data_strength_ts,"label",J48)
j48_pol_strength_perf_ts<-my_performance(j48_pol_strength_data_ts$predicted,
                                         j48_pol_strength_data_ts$label)

j48_pol_emotion_data_ts<-mycv(10,pol_data_emotion_ts,"label",J48)
j48_pol_emotion_perf_ts<-my_performance(j48_pol_emotion_data_ts$predicted,
                                        j48_pol_emotion_data_ts$label)


log_neu_full_data_san<-mycv(10,neu_data_san,"neutral_label",Logistic)
log_neu_full_perf_san<-my_performance(log_neu_full_data_san$predicted,
                                  log_neu_full_data_san$neutral_label)


log_neu_cons_data_san<-mycv(10,neu_data_cons_san,"neutral_label",Logistic)
log_neu_cons_perf_san<-my_performance(log_neu_cons_data_san$predicted,
                                      log_neu_cons_data_san$neutral_label)

log_neu_cfs_data_san<-mycv(10,neu_data_cfs_san,"neutral_label",Logistic)
log_neu_cfs_perf_san<-my_performance(log_neu_cfs_data_san$predicted,
                                     log_neu_cfs_data_san$neutral_label)

log_neu_polarity_data_san<-mycv(10,neu_data_polarity_san,"neutral_label",Logistic)
log_neu_polarity_perf_san<-my_performance(log_neu_polarity_data_san$predicted,
                                          log_neu_polarity_data_san$neutral_label)

log_neu_strength_data_san<-mycv(10,neu_data_strength_san,"neutral_label",Logistic)
log_neu_strength_perf_san<-my_performance(log_neu_strength_data_san$predicted,
                                          log_neu_strength_data_san$neutral_label)

log_neu_emotion_data_san<-mycv(10,neu_data_emotion_san,"neutral_label",Logistic)
log_neu_emotion_perf_san<-my_performance(log_neu_emotion_data_san$predicted,
                                         log_neu_emotion_data_san$neutral_label)

log_neu_full_data_ts<-mycv(10,neu_data_ts,"neutral_label",Logistic)
log_neu_full_perf_ts<-my_performance(log_neu_full_data_ts$predicted,
                                     log_neu_full_data_ts$neutral_label)


log_neu_cons_data_ts<-mycv(10,neu_data_cons_ts,"neutral_label",Logistic)
log_neu_cons_perf_ts<-my_performance(log_neu_cons_data_ts$predicted,
                                     log_neu_cons_data_ts$neutral_label)

log_neu_cfs_data_ts<-mycv(10,neu_data_cfs_ts,"neutral_label",Logistic)
log_neu_cfs_perf_ts<-my_performance(log_neu_cfs_data_ts$predicted,
                                    log_neu_cfs_data_ts$neutral_label)

log_neu_polarity_data_ts<-mycv(10,neu_data_polarity_ts,"neutral_label",Logistic)
log_neu_polarity_perf_ts<-my_performance(log_neu_polarity_data_ts$predicted,
                                         log_neu_polarity_data_ts$neutral_label)

log_neu_strength_data_ts<-mycv(10,neu_data_strength_ts,"neutral_label",Logistic)
log_neu_strength_perf_ts<-my_performance(log_neu_strength_data_ts$predicted,
                                         log_neu_strength_data_ts$neutral_label)

log_neu_emotion_data_ts<-mycv(10,neu_data_emotion_ts,"neutral_label",Logistic)
log_neu_emotion_perf_ts<-my_performance(log_neu_emotion_data_ts$predicted,
                                        log_neu_emotion_data_ts$neutral_label)



log_pol_full_data_san<-mycv(10,pol_data_san,"label",Logistic)
log_pol_full_perf_san<-my_performance(log_pol_full_data_san$predicted,
                                      log_pol_full_data_san$label)


log_pol_cons_data_san<-mycv(10,pol_data_cons_san,"label",Logistic)
log_pol_cons_perf_san<-my_performance(log_pol_cons_data_san$predicted,
                                      log_pol_cons_data_san$label)

log_pol_cfs_data_san<-mycv(10,pol_data_cfs_san,"label",Logistic)
log_pol_cfs_perf_san<-my_performance(log_pol_cfs_data_san$predicted,
                                     log_pol_cfs_data_san$label)

log_pol_polarity_data_san<-mycv(10,pol_data_polarity_san,"label",Logistic)
log_pol_polarity_perf_san<-my_performance(log_pol_polarity_data_san$predicted,
                                          log_pol_polarity_data_san$label)

log_pol_strength_data_san<-mycv(10,pol_data_strength_san,"label",Logistic)
log_pol_strength_perf_san<-my_performance(log_pol_strength_data_san$predicted,
                                          log_pol_strength_data_san$label)

log_pol_emotion_data_san<-mycv(10,pol_data_emotion_san,"label",Logistic)
log_pol_emotion_perf_san<-my_performance(log_pol_emotion_data_san$predicted,
                                         log_pol_emotion_data_san$label)

log_pol_full_data_ts<-mycv(10,pol_data_ts,"label",Logistic)
log_pol_full_perf_ts<-my_performance(log_pol_full_data_ts$predicted,
                                     log_pol_full_data_ts$label)


log_pol_cons_data_ts<-mycv(10,pol_data_cons_ts,"label",Logistic)
log_pol_cons_perf_ts<-my_performance(log_pol_cons_data_ts$predicted,
                                     log_pol_cons_data_ts$label)

log_pol_cfs_data_ts<-mycv(10,pol_data_cfs_ts,"label",Logistic)
log_pol_cfs_perf_ts<-my_performance(log_pol_cfs_data_ts$predicted,
                                    log_pol_cfs_data_ts$label)

log_pol_polarity_data_ts<-mycv(10,pol_data_polarity_ts,"label",Logistic)
log_pol_polarity_perf_ts<-my_performance(log_pol_polarity_data_ts$predicted,
                                         log_pol_polarity_data_ts$label)

log_pol_strength_data_ts<-mycv(10,pol_data_strength_ts,"label",Logistic)
log_pol_strength_perf_ts<-my_performance(log_pol_strength_data_ts$predicted,
                                         log_pol_strength_data_ts$label)

log_pol_emotion_data_ts<-mycv(10,pol_data_emotion_ts,"label",Logistic)
log_pol_emotion_perf_ts<-my_performance(log_pol_emotion_data_ts$predicted,
                                        log_pol_emotion_data_ts$label)


voter_pol_cfs<-data.frame(svm_pol_cfs_data_san$predicted,bay_pol_cfs_pred_san$predicted,
                  log_pol_cfs_data_san$predicted)
voter_cfs_perf_san<-my_performance(my_vote(voter_pol_cfs),pol_data_san$label)

voter_pol_cons<-data.frame(svm_pol_cons_data_san$predicted,bay_pol_cons_pred_san$predicted,
                          log_pol_cons_data_san$predicted)
voter_cons_perf_san<-my_performance(my_vote(voter_pol_cons),pol_data_san$label)



