tweets <- read.csv(file="C:/Users/alixi/Documents/GitHub/ProyectoLenguaje/Obtencion de Datos/tweets.csv", sep = "|", nrows = 34)
class(tweets)
tweets
tabla_tweets<- table(tweets)

barplot(tabla_tweets,
        main = "Resultados de la encuesta",
        ylab = "Numero de veces",
        xlab = "Id del tweet",
        width = 0.5, ylim = c(0, 2.5),
        horiz = T)

