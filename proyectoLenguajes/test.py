import tweepy

consumer_key = "foqnsIqPJQGEPORMrSnZFJNf9"
consumer_secret = "U24MokUDXHPEIr9tv1VBs41cR2BdrXYigdbP6hfiiSvtqxHgmE"
access_token = "319374042-8hXz2cOs6IeoZ5414xwyeIgE6Lni6ceVX3HkCjnJ"
access_token_secret = "qB4w6ll75aa6fmKkxE2nFRGB109Chyf2JSZJn9PoQoGNJ"

auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)

api = tweepy.API(auth)

query = '%science%congres%'
max_tweets = 200
searched_tweets = [status for status in tweepy.Cursor(api.search, q=query).items(max_tweets)]

dicTweets = {}
listaTuits = []

for status in searched_tweets:
    texto = status.text

    if(texto.startswith("RT")):
        lista = texto.split(":")
        lista.pop(0)
        texto = "".join(lista)

    textoArreglado = (texto.encode("ascii", errors="ignore").decode()).strip()
    textoArreglado = textoArreglado.replace("\n"," ")
    if textoArreglado in dicTweets:
        dicTweets[textoArreglado] += 1
    else:
        listaTuits.append(textoArreglado)
        dicTweets[textoArreglado] = 1

archivo = open("tweets.csv","w+")

for tweet,cantidad in dicTweets.items():
    linea = tweet + "|" + str(cantidad)
    archivo.write(linea+"\n")

archivo.close()