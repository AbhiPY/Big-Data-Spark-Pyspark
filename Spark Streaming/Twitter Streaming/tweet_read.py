#!/usr/bin/env python
# coding: utf-8

# In[17]:


import pickle
import os


# In[18]:


if not os.path.exists('secret_twitter_crentials.pkl'):
    Twitter={}
    Twitter['Consumer Key']='055NVQQthAMtqjBO3bxZ80rer'
    Twitter['Consumer Secret']='FBhhgI4a6RxduiYQ6ONZwWCRKGbCf6woRCFJUoG2eKJXtG2ZOZ'
    Twitter['Access Token']='885422061740597248-bCm2NW80OcYFAmelnfbli63tF3DVvkk'
    Twitter['Access Token Secret']='nWPQTH2tzNeCjfYjqPoXPep4HXXFYMCkH78N3AGbR4T7X'
    with open('secret_twitter_crentials.pkl','wb') as f:
        pickle.dump(Twitter,f,protocol=3)
else:
    x=pickle.load(open('secret_twitter_crentials.pkl','rb'))


# In[ ]:


import tweepy


# In[ ]:


from tweepy import OAuthHandler,Stream


# In[ ]:


from tweepy.streaming import StreamListener
import socket
import json


# In[ ]:


consumer_key = x['Consumer Key']
consumer_secret = x['Consumer Secret']
access_token = x['Access Token']
access_secret = x['Access Token Secret']


# In[ ]:


class TweetListner(StreamListener):
    def __init__(self, csocket):
        self.client_socket = csocket
    
    def on_data(self, data):
        
        try:
            msg = json.loads(data)
            msg_send = msg['text'].encode('utf-8')
            print(msg_send)
            self.client_socket.send(msg_send)
            return True
        except BaseException as e:
            print("ERROR: ",e)
        return True
    
    def on_error(self, status):
        print(status)
        return True


# In[ ]:


def sendData(c_socket):
    auth = OAuthHandler(consumer_key,consumer_secret)
    auth.set_access_token(access_token,access_secret)
    
    twitterStream = Stream(auth, TweetListner(c_socket))
    twitterStream.filter(track=['soccer'])


# In[ ]:


if __name__ == "__main__":
    s = socket.socket()
    host = '127.0.0.1'
    port = 9995
    s.bind((host,port))
    
    print("Listening on port 9995")
    
    s.listen(5)
    c,addr = s.accept()
    print(str(addr))
    
    sendData(c)


# In[ ]:




