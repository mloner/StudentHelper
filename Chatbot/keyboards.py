import json
import vk_api

def get_but(text, color):
    return {
        "action": {
            "type": "text",
            "payload": "{\"button\": \"" + "1" + "\"}",
            "label": f"{text}"
        },
        "color": f"{color}"
    }

# Стартовая Авторизации
keyboard0 = {
    "one_time": True,
    "buttons": [
        [get_but('Преподаватель', 'primary'), get_but('Студент', 'primary')]
    ]
}
keyboard0 = json.dumps(keyboard0, ensure_ascii=False).encode('utf-8')
keyboard0 = str(keyboard0.decode('utf-8'))

# Клава Студент
keyboard1 = {
    "one_time": True,
    "buttons": [
        [get_but('ИВТ-363', 'primary'), get_but('ИВТ-365', 'primary')]
    ]
}
keyboard1 = json.dumps(keyboard1, ensure_ascii=False).encode('utf-8')
keyboard1 = str(keyboard1.decode('utf-8'))

# Клава Преподаватель
keyboard2 = {
    "one_time": True,
    "buttons": [
        [get_but('На сегодня', 'primary'), get_but('На завтра', 'primary'), get_but('На 2 недели', 'primary')],
        [get_but('В начало', 'negative')]
    ]
}
keyboard2 = json.dumps(keyboard2, ensure_ascii=False).encode('utf-8')
keyboard2 = str(keyboard2.decode('utf-8'))

# Пустая
keyboard3 = {
    "one_time": True,
    "buttons": [
        [get_but('В начало', 'negative')]
    ]
}
keyboard3 = json.dumps(keyboard3, ensure_ascii=False).encode('utf-8')
keyboard3 = str(keyboard3.decode('utf-8'))


# Клава Начало
keyboard4 = {
    "one_time": True,
    "buttons": [[get_but('Авторизация', 'positive')]]
}
keyboard4 = json.dumps(keyboard4, ensure_ascii=False).encode('utf-8')
keyboard4 = str(keyboard4.decode('utf-8'))

keyboards = [keyboard0, keyboard1, keyboard2, keyboard3, keyboard4]
