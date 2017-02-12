from datetime import datetime

def str_to_datetime(datetime_str):
    """ dump string of datetime to datetime object"""
    try:
        return datetime.strptime(datetime_str, "%Y/%d/%m %H:%M:%S")
    except:
        return None
