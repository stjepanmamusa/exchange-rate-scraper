import datetime


class DateUtil:

    @staticmethod
    def getDate():
        date = datetime.datetime.now()
        return f"{date.day}.{date.month}.{date.year}"
