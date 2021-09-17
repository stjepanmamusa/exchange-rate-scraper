# Currency Exchange Rate Scraper for Croatian Banks
[![License: MIT](https://img.shields.io/github/license/smamusa/exchange-rate-scraper)
[![Code style: black](https://img.shields.io/badge/code%20style-black-000000.svg)](https://github.com/psf/black)


## Screenshot

![Screenshot](https://github.com/smamusa/banke-tecaj-python/blob/master/Screenshot.png?raw=true)

## Banks

- [x] OTP
- [x] ZABA
- [ ] Erste
- [ ] HPB
- [ ] PBZ

## To-Do

- [ ] Save rates in a txt or csv file
- [ ] Correct HTML not being parsed in time
- [ ] Add other rates

### Installation

:warning: Note, the first time you ever run the render() method, it will download Chromium into your home directory (e.g. ~/.pyppeteer/).

:exclamation: pyppeteer requires Python >= 3.6

:exclamation: This only happens once.

Clone the repo

```cmd
    git clone https://github.com/smamusa/exchange-rate-scraper.git
```

Switch into repo directory

```cmd
    cd exchange-rate-scraper
```

Create a new virtual environment

```cmd
   python -m venv myvenv
```

Run the venv activation script. Which one you run depends on your OS and terminal

```cmd
   cd myenv/Scripts
   activate.bat
```

Install all requirements

```cmd
    pip install -r requirements.txt
```

Finally run the app by calling scrape.py

```cmd
    python scrape.py
```
