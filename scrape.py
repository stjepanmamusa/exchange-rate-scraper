# Import libraries
from requests_html import HTMLSession
import logging
# Custom imports
import modules.Banks as Banks
from modules.Banks import Utils

# Set-up format strings
format = '%(asctime)s.%(msecs)03d '
format += '%(levelname)s:'
format += '%(name)s:'
format += '%(funcName)s():'
format += '%(lineno)d:'
format += '%(message)s'
format_date = '%d.%m.%Y %H:%M:%S'
formatter = logging.Formatter(format, format_date)
# Set-up logger
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
