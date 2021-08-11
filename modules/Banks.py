# Import needed utilities
import logging
import traceback
from requests_html import HTMLSession
from bs4 import BeautifulSoup
# Import custom utilities
import modules.Utils as Utils

# Setup logging
logging.basicConfig(
    filename=f"logs/log_{Utils.DateUtil.getDate()}.log", encoding="utf-8", format='%(asctime)s %(levelname)s:%(message)s', level=logging.INFO)


class Bank:
    def __init__(self, url, session, name="noname"):
        self.url = url
        self.name = name
        self.session = session

    def getRates(self):
        pass

    def getResponse(self):
        # Output info
        logging.info(f"Creating session at URL : {self.url}")
        # Get response from exchange rates url
        try:
            response = self.session.get(self.url)
            logging.info(f"Session created at URL : {self.url}")
            return response
        except(Exception):
            logging.critical(f"Exception occurred : {Exception}")

    @staticmethod
    def renderHtml(response):
        # Run JavaScript code on webpage
        logging.info("Rendering HTML")
        try:
            response.html.render()
        except(Exception):
            logging.critical(f"Exception occurred : {Exception}")
        logging.info("HTML rendered")

    # If a web page has a different structure then a specific parse method must be implemented
    @staticmethod
    def parseHtml(response):
        logging.info("Parsing HTML")
        try:
            soup = BeautifulSoup(response.html.html, "html.parser")
            td_tags = soup.find_all("td")
            td_text = [tag.text for tag in td_tags]
            return td_text
        except(Exception):
            logging.critical(f"Exception occurred while parsing {Exception}")
            traceback.print_stack()


class OtpBanka(Bank):

    def getRates(self):
        response = Bank.getResponse(self)
        # Run JavaScript code on webpage
        Bank.renderHtml(response)
        parsedText = Bank.parseHtml(response)
        try:
            print(
                f"Midmarket rate is {parsedText[parsedText.index('EUR') + 5]}")
            print(
                f"OTP Banka cash buy rate EUR to HRK : {parsedText[parsedText.index('EUR') + 3]}")
            print(
                f"OTP Banka cash sell rate EUR to HRK : {parsedText[parsedText.index('EUR') + 7]}")
            print(
                f"OTP Banka foreign buy rate EUR to HRK : {parsedText[parsedText.index('EUR') + 5]}")
            print(
                f"OTP Banka foreign buy rate EUR to HRK : {parsedText[parsedText.index('EUR') + 6]}")
        except:
            print("Errors occurred while parsing, check logs")


class ZABA(Bank):
    def getRates(self, type):
        """
        Get rates for ZABA bank.

        Parameters
        type (string) - Type of rate, can be foreign or cash
        """
        response = Bank.getResponse(self)
        # Run JavaScript code on webpage
        Bank.renderHtml(response)
        parsedText = Bank.parseHtml(response)
        try:
            eurIndex = parsedText.index('\nEUR')
            print(
                f"ZABA {type} buy rate EUR to HRK : {parsedText[eurIndex + 2]}")
            print(
                f"ZABA {type} buy sell EUR to HRK : {parsedText[eurIndex + 4]}")
        except(Exception):
            print(
                "Eror occurred while getting ZABA rates : {0}".format(Exception))
