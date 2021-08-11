# Import libraries
from bs4 import BeautifulSoup
# import HTMLSession from requests_html
from requests_html import HTMLSession
import modules.Banks as Banks

# Make sure to use the same session
session = HTMLSession()

otp = Banks.OtpBanka('https://www.otpbanka.hr/hr/tecajna-lista', session)
otp.getRates()

zaba = Banks.ZABA('https://www.zaba.hr/home/tecajna', session)
zaba.getRates('foreign')
zaba = Banks.ZABA('https://www.zaba.hr/home/tecajna#pan2', session)
zaba.getRates('cash')
