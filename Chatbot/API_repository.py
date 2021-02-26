import json
import requests

test_ok = json.loads('{"response": "ok"}')
test_fail = json.loads('{"response": "fail"}')

def _authorization(req:json):
    response = json.loads(requests.get('http://shipshon.fvds.ru/api').text)
    if response['status'] == 'OK':
        if response['response'] == 'OK':
            print('Так и быть, проходи')
            return True
    else:
        print('Сразу нахуй')
        return  False