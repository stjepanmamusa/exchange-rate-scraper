# Import libraries
from bs4 import BeautifulSoup
from requests_html import HTMLSession
import logging
# Custom imports
import modules.Banks as Banks
import modules.Utils as Utils

# Setup logging
formatter = logging.Formatter(
    '%(asctime)s.%(msecs)03d %(levelname)s:%(name)s:%(funcName)s():%(message)s', '%d.%m.%Y %H:%M:%S')
file_handler = logging.FileHandler(
    f'logs/log_{Utils.DateUtil.getDate()}.log')
file_handler.setFormatter(formatter)
stream_handler = logging.StreamHandler()
logger = logging.getLogger(__name__)
logger.addHandler(file_handler)
logger.addHandler(stream_handler)
logger.setLevel(logging.DEBUG)

# Make sure to use the same session
session = HTMLSession()
logger.info('CREATE HTML SESSION COMPLETED')

otp = Banks.OtpBanka('https://www.otpbanka.hr/hr/tecajna-lista', session)
otp.getRates()

zaba = Banks.ZABA('https://www.zaba.hr/home/tecajna', session)
zaba.getRates('foreign')
zaba = Banks.ZABA('https://www.zaba.hr/home/tecajna#pan2', session)
zaba.getRates('cash')
