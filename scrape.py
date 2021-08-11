# Import libraries
import requests
from bs4 import BeautifulSoup
# import HTMLSession from requests_html
from requests_html import HTMLSession
import modules.Banks as Banks

otp = Banks.OtpBanka('https://www.otpbanka.hr/hr/tecajna-lista')
otp.getRates()

# Set the URL you want to webscrape from
zaba_url = 'https://www.zaba.hr/home/tecajna'
session = HTMLSession()
# Use the object above to connect to needed webpage
response = session.get(zaba_url)

# Run JavaScript code on webpage
try:
    response.html.render()

    soup = BeautifulSoup(response.html.html, "html.parser")
    td_tags = soup.find_all("td")
    td_text = [tag.text for tag in td_tags]

    print("ZABA midmarket rate EUR to HRK : {0}".format(
        td_text[td_text.index('\nEUR') + 3]))
except(Exception):
    print("Eror occurred while getting ZABA rates : {0}".format(Exception))
