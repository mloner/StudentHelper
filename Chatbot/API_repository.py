import json
import requests

test_ok = json.loads('{"response": "ok"}')
test_fail = json.loads('{"response": "fail"}')

url = 'http://shipshon.fvds.ru/api'
headers = {'Content-type': 'application/json',  # Определение типа данных
           'Accept': 'text/plain',
           'Content-Encoding': 'utf-8'}

def _registerUser(idvk:str, role:str, arg:str):
    req = {}
    req['client_type'] = 'bot'
    req['command'] = 'registerUser'
    req['idvk'] = idvk
    req['role'] = role
    req['arg'] = arg
    print(json.dumps(req))
    response = json.loads(requests.post(url, json.dumps(req), headers=headers).text)
    print(response)
    return response

def _getAuthData(idvk:str):
    req = {}
    req['client_type'] = 'bot'
    req['command'] = 'getAuthData'
    req['idvk'] = idvk
    print(json.dumps(req))
    response = json.loads(requests.post(url, json.dumps(req), headers=headers).text)
    print(response)
    return response

def _getUserState(idvk:str):
    req = {}
    req['client_type'] = 'bot'
    req['command'] = 'getUserState'
    req['idvk'] = idvk
    print(json.dumps(req))
    response = json.loads(requests.post(url, json.dumps(req), headers=headers).text)
    print(response)
    return response

def _setUserState(idvk:str, state:str):
    req = {}
    req['client_type'] = 'bot'
    req['command'] = 'setUserState'
    req['idvk'] = idvk
    req['state'] = state
    print(json.dumps(req))
    response = json.loads(requests.post(url, json.dumps(req), headers=headers).text)
    print(response)
    return response

def _checkGroup(group:str):
    req = {}
    req['client_type'] = 'bot'
    req['command'] = 'checkGroup'
    req['group'] = group
    print(json.dumps(req))
    response = json.loads(requests.post(url, json.dumps(req), headers=headers).text)
    print(response)
    return response

def _checkFIO(FIO:str):
    req = {}
    req['client_type'] = 'bot'
    req['command'] = 'checkFIO'
    req['FIO'] = FIO
    print(json.dumps(req))
    response = json.loads(requests.post(url, json.dumps(req), headers=headers).text)
    print(response)
    return response
