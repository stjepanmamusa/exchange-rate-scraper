from requests.models import Response
from requests_html import HTMLSession

session = HTMLSession()
res = session.get("http://www.python.org/")

print(res.raw)
