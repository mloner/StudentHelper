import json
import requests

test_ok = json.loads('{"response": "ok"}')
test_fail = json.loads('{"response": "fail"}')

url = 'http://shipshon.fvds.ru/api'
headers = {'Content-type': 'application/json',  # Определение типа данных
           'Accept': 'text/plain',
           'Content-Encoding': 'utf-8'}

# POST
def _registerUser(idvk:str, role:str, arg:str, subGroup:str):
    url = 'http://shipshon.fvds.ru/api/registerUser'
    req = {}
    req['client_type'] = 'bot'
    req['command'] = 'registerUser'
    req['idvk'] = idvk
    req['role'] = role
    req['arg'] = arg
    req['subGroup'] = subGroup
    print(json.dumps(req))
    response = json.loads(requests.post(url, json.dumps(req), headers=headers).text)
    print(response)
    return response

def _setUserField(idvk:str, field:str, value:str):
    url = 'http://shipshon.fvds.ru/api/setUserField'
    req = {}
    req['client_type'] = 'bot'
    req['command'] = 'setUserField'
    req['idvk'] = idvk
    req['field'] = field
    req['value'] = value
    print(json.dumps(req))
    response = json.loads(requests.post(url, json.dumps(req), headers=headers).text)
    print(response)
    return response

def _getUserState(idvk:str):
    url = '  api/getUserState'
    req = {}
    req['client_type'] = 'bot'
    req['command'] = 'getUserState'
    req['idvk'] = idvk
    print(json.dumps(req))
    response = json.loads(requests.post(url, json.dumps(req), headers=headers).text)
    print(response)
    return response

def _getQuestion(idvk:str):
    url = 'http://shipshon.fvds.ru/api/getQuestion'
    req = {}
    req['client_type'] = 'bot'
    req['command'] = 'getQuestion'
    req['idvk'] = idvk
    print(json.dumps(req))
    response = json.loads(requests.post(url, json.dumps(req), headers=headers).text)
    print(response)
    return response

def _answerQuestion(idvk:str, answer:str):
    url = 'http://shipshon.fvds.ru/api/answerQuestion'
    req = {}
    req['client_type'] = 'bot'
    req['command'] = 'answerQuestion'
    req['idvk'] = idvk
    req['answer'] = answer
    print(json.dumps(req))
    response = json.loads(requests.post(url, json.dumps(req), headers=headers).text)
    print(response)
    return response


# GET
def _checkGroup(group:str):
    url = 'http://shipshon.fvds.ru/api/checkGroup'
    req = {}
    req['client_type'] = 'bot'
    req['command'] = 'checkGroup'
    req['group'] = group
    print(json.dumps(req))
    response = json.loads(requests.get(url, params = req, headers=headers).text)
    print(response)
    return response

def _checkFio(fio:str):
    url = 'http://shipshon.fvds.ru/api/checkFio'
    req = {}
    req['client_type'] = 'bot'
    req['command'] = 'checkFio'
    req['fio'] = fio
    print(json.dumps(req))
    response = json.loads(requests.get(url, params = req, headers=headers).text)
    print(response)
    return response

def _getScheduleStudent(role:str, group:str, schedule_type:str, date:str):
    url = 'http://shipshon.fvds.ru/api/getScheduleStudent'
    req = {}
    req['client_type'] = 'bot'
    req['command'] = 'getScheduleStudent'
    req['role'] = role
    req['group'] = group
    req['schedule_type'] = schedule_type
    req['date'] = date
    print(json.dumps(req))
    response = json.loads(requests.get(url, params = req, headers=headers).text)
    print(response)
    return response

def _getSchedulePrepod(role:str, fio:str, schedule_type:str, date:str):
    url = 'http://shipshon.fvds.ru/api/getSchedulePrepod'
    req = {}
    req['client_type'] = 'bot'
    req['command'] = 'getSchedulePrepod'
    req['role'] = role
    req['fio'] = fio
    req['schedule_type'] = schedule_type
    req['date'] = date
    print(json.dumps(req))
    response = json.loads(requests.get(url, params = req, headers=headers).text)
    print(response)
    return response

