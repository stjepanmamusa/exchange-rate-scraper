# Import needed utilities
import logging
import traceback
from requests_html import HTMLSession
from bs4 import BeautifulSoup
# Import custom utilities
import modules.Utils as Utils

# Set-up logging
formatter = logging.Formatter(
    '%(asctime)s.%(msecs)03d %(levelname)s:%(name)s:%(funcName)s():%(message)s', '%d.%m.%Y %H:%M:%S')
file_handler = logging.FileHandler(f'logs/log_{Utils.DateUtil.getDate()}.log')
file_handler.setFormatter(formatter)
logger = logging.getLogger(__name__)
logger.addHandler(file_handler)
logger.setLevel(logging.DEBUG)


class Bank:
    def __init__(self, url, session, name='noname'):
        self.url = url
        self.name = name
        self.session = session

    def getRates(self):
        pass

    def getResponse(self):
        # Output info
        logger.info(f'Creating session at URL : {self.url}')
        # Get response from exchange rates url
        try:
            response = self.session.get(self.url)
            logger.info(f'Session created at URL : {self.url}')
            return response
        except(Exception):
            logger.critical(f'Exception occurred : {Exception}')

    @staticmethod
    def renderHtml(response):
        # Run JavaScript code on webpage
        logger.info('Rendering HTML')
        try:
            response.html.render()
        except(Exception):
            logger.critical(f'Exception occurred : {Exception}')
        logger.info('HTML rendered')

    # If a web page has a different structure then a specific parse method must be implemented
    @staticmethod
    def parseHtml(response):
        logger.info('Parsing HTML')
        try:
            soup = BeautifulSoup(response.html.html, 'html.parser')
            td_tags = soup.find_all('td')
            td_text = [tag.text for tag in td_tags]
            return td_text
        except(Exception):
            logger.critical(f'Exception occurred while parsing {Exception}')
            traceback.print_stack()


class OtpBanka(Bank):

    def getRates(self):
        response = Bank.getResponse(self)
        # Run JavaScript code on webpage
        Bank.renderHtml(response)
        parsedText = Bank.parseHtml(response)
        currency = 'EUR'
        try:
            print(
                f'Midmarket rate is {parsedText[parsedText.index(currency) + 5]}')
            print(
                f'OTP Banka cash buy rate EUR to HRK : {parsedText[parsedText.index(currency) + 3]}')
            print(
                f'OTP Banka cash sell rate EUR to HRK : {parsedText[parsedText.index(currency) + 7]}')
            print(
                f'OTP Banka foreign buy rate EUR to HRK : {parsedText[parsedText.index(currency) + 5]}')
            print(
                f'OTP Banka foreign buy rate EUR to HRK : {parsedText[parsedText.index(currency) + 6]}')
        except:
            logger.exception('Parse exception')
            raise


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
                f'ZABA {type} buy rate EUR to HRK : {parsedText[eurIndex + 2]}')
            print(
                f'ZABA {type} buy sell EUR to HRK : {parsedText[eurIndex + 4]}')
        except(Exception):
            print(
                f'Eror occurred while getting ZABA rates : {Exception}')
