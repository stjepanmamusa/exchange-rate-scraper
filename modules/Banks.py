# Import needed utilities
import logging
import modules.Utils as Utils
from requests_html import HTMLSession
from bs4 import BeautifulSoup

# Setup logging
logging.basicConfig(
    filename=f"logs/log_{Utils.DateUtil.getDate()}.log", encoding="utf-8", format='%(asctime)s %(levelname)s:%(message)s', level=logging.INFO)


class Bank:
    session = HTMLSession()

    def __init__(self, url, name="noname"):
        self.url = url
        self.name = name

    def getRates(self):
        pass


class OtpBanka(Bank):

    def getRates(self):
        # Output info
        logging.info(f"Creating session at URL : {self.url}")
        # Get response from exchange rates url
        try:
            response = Bank.session.get(self.url)
        except(Exception):
            logging.critical(f"Exception occurred : {Exception}")
        logging.info(f"Session created at URL : {self.url}")

        # Run JavaScript code on webpage
        logging.info("Rendering HTML")
        try:
            response.html.render()
        except(Exception):
            logging.critical(f"Exception occurred : {Exception}")
        logging.info("HTML rendered")

        logging.info("Parsing HTML")
        try:
            soup = BeautifulSoup(response.html.html, "html.parser")
            td_tags = soup.find_all("td")
            td_text = [tag.text for tag in td_tags]
        except(Exception):
            logging.critical(f"Exception occurred while parsing {Exception}")

        print(f"Midmarket rate is {td_text[td_text.index('EUR') + 5]}")

        print(
            f"OTP Banka buy rate (effective) EUR to HRK : {td_text[td_text.index('EUR') + 3]}")


class ZABA(Bank):
    def __init__(self, url):
        self.url = url
