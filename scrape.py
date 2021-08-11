# Import libraries
import requests
from bs4 import BeautifulSoup
# import HTMLSession from requests_html
from requests_html import HTMLSession

# Set the URL you want to webscrape from
otp_url = 'https://www.otpbanka.hr/hr/tecajna-lista'
zaba_url = 'https://www.zaba.hr/home/tecajna'

# create an HTML Session object
session = HTMLSession()
 
# Use the object above to connect to needed webpage
response = session.get(otp_url)
 
# Run JavaScript code on webpage
response.html.render()

soup = BeautifulSoup(response.html.html, "html.parser")
td_tags = soup.find_all("td")
td_text = [tag.text for tag in td_tags]

try:
    print("OTP Banka srednji tečaj EUR prema HRK : {0}".format(td_text[td_text.index('EUR') + 5]))
except(ValueError):
    print("Program nije uspio dobaviti vrijednost na vrijeme. Pričekajte minut i pokušajte ponovno")

# Use the object above to connect to needed webpage
response = session.get(zaba_url)

# Run JavaScript code on webpage
response.html.render()

soup = BeautifulSoup(response.html.html, "html.parser")
td_tags = soup.find_all("td")
td_text = [tag.text for tag in td_tags]

print("ZABA srednji tečaj EUR prema HRK : {0}".format(td_text[td_text.index('\nEUR') + 3]))
