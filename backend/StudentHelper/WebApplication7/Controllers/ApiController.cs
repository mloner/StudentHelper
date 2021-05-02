using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using MongoDB.Driver;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using StudentHelper.Entities;
using StudentHelper.Models;
using StudentHelper.Repos;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using static StudentHelper.Entities.FBEntities;

namespace StudentHelper.Controllers
{
    [Route("api")]
    [Produces("application/json")]
    [ApiController]
    public class ApiController : ControllerBase
    {
        private readonly ILogger<ApiController> _logger;
        private readonly IConfiguration _config;

        private readonly ChatService _chatService;
        private PGRepo _PGRepo;
        private FBRepo _FBRepo;

        private int rnd;
        private DateTime now;

        public ApiController(ILogger<ApiController> logger,
                             IConfiguration config,
                             PGRepo PGRepo,
                             FBRepo FBRepo,
                             ChatService chatService)
        {
            _logger = logger;
            _config = config;
            _chatService = chatService;
            _PGRepo = PGRepo;
            _FBRepo = FBRepo;
            now = DateTime.Now.ToLocalTime().AddHours(3);
            rnd = (new Random(now.Millisecond)).Next();
        }

        [HttpGet]
        public IActionResult lbh()
        {

            return Content("ghj");
        }

        /// <summary>
        /// Получить список преподавателей
        /// </summary>
        [HttpGet("getPrepodList")]
        public IActionResult GetPrepodList()
        {
            _logger.LogInformation($"Req id:{rnd}\n Time: {now} Method: {System.Reflection.MethodInfo.GetCurrentMethod()}\n");

            dynamic resp = new JObject();

            resp.response = new JArray();

            var teachers = _PGRepo.getAllTeachers();
            foreach (var t in teachers)
            {
                resp.response.Add(new JValue(t.FIO));
            }
            resp.status = "OK";

            string respStr = resp.ToString();
            _logger.LogInformation($"Req id:{rnd}\n Time: {now}\n Resp: {respStr}\n");

            return Content(respStr);
        }

        /// <summary>
        /// Получить информацию о преподавателе
        /// </summary>
        [HttpGet("getPrepodInfo")]
        public IActionResult GetPrepodInfo([FromQuery(Name = "fio")] string fio)
        {
            _logger.LogInformation($"Req id:{rnd}\n Time: {now} Method: {System.Reflection.MethodInfo.GetCurrentMethod()}\n");


            dynamic resp = new JObject();
            resp.response = new JObject();

            var teacher = _PGRepo.getTeacherFullInfoByFio(fio);
            if (teacher != null)
            {
                resp.status = "OK";
                resp.response = new JObject(
                    new JProperty("faculty", teacher.Cathedra.Facultate.id),
                    new JProperty("cathedra", teacher.Cathedra.name),
                    new JProperty("location", teacher.Cathedra.location),
                    new JProperty("position", teacher.TeacherPosition.name),
                    new JProperty("phone", teacher.phone),
                    new JProperty("email", teacher.email),
                    new JProperty("degree", teacher.TeacherDegree.name)
                    );
            }
            else
            {
                resp.status = "FAIL";
                resp.response = new JValue("No such teacher");
            }

            string respStr = resp.ToString();
            _logger.LogInformation($"Req id:{rnd}\n Time: {now}\n Resp: {respStr}\n");

            return Content(respStr);
        }

        /// <summary>
        /// Авторизация с мобильного приложения
        /// </summary>
        [HttpGet("authorizationMobile")]
        public IActionResult AuthorizationMobile([FromQuery(Name = "client_type")] string client_type,
                                                 [FromQuery(Name = "role")] string role,
                                                 [FromQuery(Name = "pass")] string pass,
                                                 [FromQuery(Name = "arg")] string arg)
        {
            _logger.LogInformation($"Req id:{rnd}\n Time: {now} Method: {System.Reflection.MethodInfo.GetCurrentMethod()}\n");

            dynamic resp = new JObject();
            resp.status = "OK";
            resp.response = new JObject();

            switch (role)
            {
                case "student":
                    {
                        var group = _PGRepo.getGroupByName(arg);
                        if (group == null)
                        {
                            resp.status = "FAIL";
                            resp.response = "WRONG_LOGIN";
                        }
                        else
                        {
                            resp.status = "OK";
                            resp.response = "OK";
                        }
                        break;
                    }
                case "prepod":
                    {
                        var teacher = _PGRepo.getTeacherByFio(arg);
                        if (teacher == null)
                        {
                            resp.status = "FAIL";
                            resp.response = "WRONG_LOGIN";
                        }
                        else
                        {
                            // check password
                            if (teacher.password == pass)
                            {
                                resp.status = "OK";
                                resp.response = "OK";
                            }
                            else
                            {
                                resp.status = "FAIL";
                                resp.response = "WRONG_PASSWORD";
                            }
                        }
                        break;
                    }
            }

            string respStr = resp.ToString();
            _logger.LogInformation($"Req id:{rnd}\n Time: {now}\n Resp: {respStr}\n");

            return Content(respStr);
        }

        /// <summary>
        /// Проверить, есть ли такая группа
        /// </summary>
        [HttpGet("checkGroup")]
        public IActionResult CheckGroup([FromQuery(Name = "group")] string group)
        {
            _logger.LogInformation($"Req id:{rnd}\n Time: {now} Method: {System.Reflection.MethodInfo.GetCurrentMethod()}\n");


            dynamic resp = new JObject();
            resp.status = "OK";
            resp.response = new JObject();

            var reqGroup = _PGRepo.getGroupByName(group);
            if (reqGroup != null)
            {
                resp.status = "OK";
                resp.response = "";
            }
            else
            {
                resp.status = "FAIL";
                resp.response = "";
            }


            string respStr = resp.ToString();
            _logger.LogInformation($"Req id:{rnd}\n Time: {now}\n Resp: {respStr}\n");

            return Content(respStr);
        }

        /// <summary>
        /// Проверить, есть ли такой преподаватель
        /// </summary>
        [HttpGet("checkFio")]
        public IActionResult CheckFio([FromQuery(Name = "fio")] string fio)
        {
            _logger.LogInformation($"Req id:{rnd}\n Time: {now} Method: {System.Reflection.MethodInfo.GetCurrentMethod()}\n");


            dynamic resp = new JObject();
            resp.response = new JObject();

            var teacher = _PGRepo.getTeacherByFio(fio);
            if (teacher != null)
            {
                resp.status = "OK";
                resp.response = "";
            }
            else
            {
                resp.status = "FAIL";
                resp.response = "";
            }

            string respStr = resp.ToString();
            _logger.LogInformation($"Req id:{rnd}\n Time: {now}\n Resp: {respStr}\n");

            return Content(respStr);
        }

        /// <summary>
        /// Получить расписание на сегодня/завтра/две недели/любую дату для определенной группы
        /// </summary>
        [HttpGet("getScheduleStudent")]
        public IActionResult GetScheduleStudent([FromQuery(Name = "client_type")] string client_type,
                                                [FromQuery(Name = "group")] string group,
                                                [FromQuery(Name = "schedule_type")] string schedule_type,
                                                [FromQuery(Name = "date")] string date)
        {
            _logger.LogInformation($"Req id:{rnd}\n Time: {now} Method: {System.Reflection.MethodInfo.GetCurrentMethod()}\n");

            string role = "student";
            var dateSchedule = new DateTime();
            switch (schedule_type)
            {
                case "today":
                    dateSchedule = DateTime.Now;
                    dateSchedule = dateSchedule.AddHours(3);
                    break;
                case "tomorrow":
                    dateSchedule = DateTime.Now.AddDays(1);
                    dateSchedule = dateSchedule.AddHours(3);
                    break;
                case "custom":
                    dateSchedule = DateTime.ParseExact(date, "dd.MM.yyyy", CultureInfo.InvariantCulture);
                    break;
            }

            var fs = new DateTime(dateSchedule.Year, 9, 1);
            DateTime lastDay = new DateTime(dateSchedule.Year, 12, 31);
            var cal = new GregorianCalendar();
            bool weekNumChetn;
            int weekNumInt = 0;
            var todayFirstDayNum = cal.GetWeekOfYear(dateSchedule, CalendarWeekRule.FirstDay, DayOfWeek.Monday);
            var todayFullWeekNum = cal.GetWeekOfYear(dateSchedule, CalendarWeekRule.FirstFullWeek, DayOfWeek.Monday);
            var weekNumberToday = cal.GetWeekOfYear(dateSchedule, CalendarWeekRule.FirstDay, DayOfWeek.Monday);
            weekNumChetn = weekNumberToday % 2 == 0;
            if ((dateSchedule <= lastDay && dateSchedule >= fs) || (todayFirstDayNum != todayFullWeekNum))
            {
                weekNumInt = weekNumChetn ? 1 : 2;
            }
            else if ((dateSchedule < new DateTime(dateSchedule.Year, 9, 1) && (todayFirstDayNum == todayFullWeekNum)))
            {
                weekNumInt = weekNumChetn ? 1 : 2;
            }

            string RUmonth = dateSchedule.ToString("dddd", CultureInfo.GetCultureInfo("ru-ru"));

            dynamic resp = new JObject();
            resp.status = "OK";

            var schedule = _PGRepo.getScheduleByGroup(group);

            resp.response = new JArray();

            if (schedule_type == "two_weeks")
            {
                // num of week
                for (int i = 1; i <= 2; i++)
                {
                    var weekScheduleJson = new JArray();
                    var weekSchedule = schedule.Where(s => s.weekNum == i);
                    // days of week
                    for (int j = 1; j <= 6; j++)
                    {
                        var dayScheduleJson = new JArray();
                        var daySchedule = weekSchedule.Where(ws => ws.weekdayNum == j);
                        foreach (var lesson in daySchedule)
                        {
                            dayScheduleJson.Add(new JObject(
                                        new JProperty("subjectName", lesson.subjectName),
                                        new JProperty("prepodName", lesson.prepodName),
                                        new JProperty("className", lesson.className),
                                        new JProperty("lessonType", lesson.lessonType),
                                        new JProperty("lessonStart", lesson.lessonStart),
                                        new JProperty("lessonEnd", lesson.lessonEnd),
                                        new JProperty("groupName", lesson.groupName),
                                        new JProperty("subGroup", lesson.subGroup),
                                        new JProperty("isRemote", lesson.isRemote),
                                        new JProperty("description", lesson.description),
                                        new JProperty("weekDayName", lesson.weekDayName)
                                ));
                        }
                        weekScheduleJson.Add(dayScheduleJson);
                    }
                    resp.response.Add(weekScheduleJson);
                }
            }
            else
            {
                schedule = schedule.Where(s =>  s.weekNum == weekNumInt &&
                                                s.weekDayName == RUmonth)
                                   .OrderBy(s => s.lessonStart).ToList();

                resp.status = "OK";

                foreach (var si in schedule)
                {
                    resp.response.Add(new JObject(
                        new JProperty("subjectName", si.subjectName),
                        new JProperty("prepodName", si.prepodName),
                        new JProperty("className", si.className),
                        new JProperty("lessonType", si.lessonType),
                        new JProperty("lessonStart", si.lessonStart),
                        new JProperty("lessonEnd", si.lessonEnd),
                        new JProperty("groupName", si.groupName),
                        new JProperty("subGroup", si.subGroup),
                        new JProperty("isRemote", si.isRemote),
                        new JProperty("description", si.description),
                        new JProperty("weekDayName", si.weekDayName)
                        ));
                }
            }

            string respStr = resp.ToString();
            _logger.LogInformation($"Req id:{rnd}\n Time: {now}\n Resp: {respStr}\n");

            return Content(respStr);
        }

        /// <summary>
        /// Получить расписание на сегодня/завтра/две недели/любую дату для определенного преподавателя
        /// </summary>
        [HttpGet("getSchedulePrepod")]
        public IActionResult GetSchedulePrepod([FromQuery(Name = "client_type")] string client_type,
                                               [FromQuery(Name = "fio")] string fio,
                                               [FromQuery(Name = "schedule_type")] string schedule_type,
                                               [FromQuery(Name = "date")] string date)
        {
            _logger.LogInformation($"Req id:{rnd}\n Time: {now} Method: {System.Reflection.MethodInfo.GetCurrentMethod()}\n");


            var dateSchedule = new DateTime();
            switch (schedule_type)
            {
                case "today":
                    dateSchedule = DateTime.Now;
                    dateSchedule = dateSchedule.AddHours(3);
                    break;
                case "tomorrow":
                    dateSchedule = DateTime.Now.AddDays(1);
                    dateSchedule = dateSchedule.AddHours(3);
                    break;
                case "custom":
                    dateSchedule = DateTime.ParseExact(date, "dd.MM.yyyy", CultureInfo.InvariantCulture);
                    break;
            }

            var fs = new DateTime(dateSchedule.Year, 9, 1);// 36 неделя (нечетная учебная неделя)
            DateTime lastDay = new DateTime(dateSchedule.Year, 12, 31);
            var cal = new GregorianCalendar();
            bool weekNumChetn;
            int weekNumInt = 0;
            var todayFirstDayNum = cal.GetWeekOfYear(dateSchedule, CalendarWeekRule.FirstDay, DayOfWeek.Monday);
            var todayFullWeekNum = cal.GetWeekOfYear(dateSchedule, CalendarWeekRule.FirstFullWeek, DayOfWeek.Monday);
            var weekNumberToday = cal.GetWeekOfYear(dateSchedule, CalendarWeekRule.FirstDay, DayOfWeek.Monday);
            weekNumChetn = weekNumberToday % 2 == 0;
            if ((dateSchedule <= lastDay && dateSchedule >= fs) || (todayFirstDayNum != todayFullWeekNum))
            {
                weekNumInt = weekNumChetn ? 1 : 2;
            }
            else if ((dateSchedule < new DateTime(dateSchedule.Year, 9, 1) && (todayFirstDayNum == todayFullWeekNum)))
            {
                weekNumInt = weekNumChetn ? 1 : 2;
            }

            string RUmonth = dateSchedule.ToString("dddd", CultureInfo.GetCultureInfo("ru-ru"));

            dynamic resp = new JObject();
            resp.status = "OK";

            var schedule = _PGRepo.getScheduleByTeacherFio(fio);

            if (schedule_type == "two_weeks")
            {
                resp.response = new JArray();

                // num of week
                for (int i = 1; i <= 2; i++)
                {
                    var weekScheduleJson = new JArray();
                    var weekSchedule = schedule.Where(s => s.weekNum == i);
                    // days of week
                    for (int j = 1; j <= 6; j++)
                    {
                        var dayScheduleJson = new JArray();
                        var daySchedule = weekSchedule.Where(ws => ws.weekdayNum == j);
                        foreach (var lesson in daySchedule)
                        {
                            dayScheduleJson.Add(new JObject(
                                        new JProperty("subjectName", lesson.subjectName),
                                        new JProperty("prepodName", lesson.prepodName),
                                        new JProperty("className", lesson.className),
                                        new JProperty("lessonType", lesson.lessonType),
                                        new JProperty("lessonStart", lesson.lessonStart),
                                        new JProperty("lessonEnd", lesson.lessonEnd),
                                        new JProperty("groupName", lesson.groupName),
                                        new JProperty("subGroup", lesson.subGroup),
                                        new JProperty("isRemote", lesson.isRemote),
                                        new JProperty("description", lesson.description),
                                        new JProperty("weekDayName", lesson.weekDayName)
                                ));
                        }
                        weekScheduleJson.Add(dayScheduleJson);
                    }
                    resp.response.Add(weekScheduleJson);
                }

            }
            else
            {
                schedule = schedule.Where(s => s.prepodName == fio &&
                                                s.weekNum == weekNumInt &&
                                                s.weekDayName == RUmonth)
                                   .OrderBy(s => s.lessonStart).ToList();

                resp.status = "OK";
                resp.response = new JArray();

                foreach (var si in schedule)
                {
                    resp.response.Add(new JObject(
                        new JProperty("subjectName", si.subjectName),
                        new JProperty("prepodName", si.prepodName),
                        new JProperty("className", si.className),
                        new JProperty("lessonType", si.lessonType),
                        new JProperty("lessonStart", si.lessonStart),
                        new JProperty("lessonEnd", si.lessonEnd),
                        new JProperty("groupName", si.groupName),
                        new JProperty("subGroup", si.subGroup),
                        new JProperty("isRemote", si.isRemote),
                        new JProperty("description", si.description),
                        new JProperty("weekDayName", si.weekDayName)
                        ));
                }
            }
            string respStr = resp.ToString();
            _logger.LogInformation($"Req id:{rnd}\n Time: {now}\n Resp: {respStr}\n");

            return Content(respStr);
        }

        /// <summary>
        /// Получить текущее состояние юзера чат-бота
        /// </summary>
        [HttpPost("getUserState")]
        public IActionResult GetUserState([FromBody] object data)
        {
            _logger.LogInformation($"Req id:{rnd}\n Time: {now} Method: {System.Reflection.MethodInfo.GetCurrentMethod()}\n");


            dynamic req = JObject.Parse(data.ToString());
            dynamic resp = new JObject();

            string idvk = req.idvk;

            resp.status = "OK";
            resp.response = new JObject();

            var user = _FBRepo.getUserByIdvk(idvk);
            if (user == null)
            {
                User newUser = new User()
                {
                    IDVK = idvk,
                    STATE = "None"
                };
                _FBRepo._ctx.USERS.Add(newUser);
                _FBRepo._ctx.SaveChanges();
                resp.status = "OK";
                resp.response = new JObject(
                                            new JProperty("role", newUser.ROLE),
                                            new JProperty("arg", newUser.ARG),
                                            new JProperty("state", newUser.STATE),
                                            new JProperty("subGroup", newUser.SUBGROUP)
                                            );
            }
            else
            {
                resp.status = "OK";
                resp.response = new JObject(new JProperty("role", user.ROLE),
                                            new JProperty("arg", user.ARG),
                                            new JProperty("state", user.STATE),
                                            new JProperty("subGroup", user.SUBGROUP));
            }


            string respStr = resp.ToString();
            _logger.LogInformation($"Req id:{rnd}\n Time: {now}\n Resp: {respStr}\n");

            return Content(respStr);
        }

        /// <summary>
        /// Зарегистрировать юзера в чат-боте
        /// </summary>
        [HttpPost("registerUser")]
        public IActionResult RegisterUser([FromBody] object data)
        {
            _logger.LogInformation($"Req id:{rnd}\n Time: {now} Method: {System.Reflection.MethodInfo.GetCurrentMethod()}\n");

            dynamic req = JObject.Parse(data.ToString());
            dynamic resp = new JObject();
            resp.status = "OK";
            resp.response = new JObject();

            string idvk = req.idvk;

            string role = req.role;
            string arg = req.arg;
            int? subgroup = req.subGroup;

            User user = new User();

            user.IDVK = idvk;
            user.ROLE = role;
            user.SUBGROUP = subgroup;
            user.ARG = arg;
            
            bool res = _FBRepo.changeUserByIdvk(user);
            if (res == true)
            {
                resp.status = "OK";
                resp.response = "OK";
            }
            else
            {
                resp.status = "FAIL";
                resp.response = "";
            }

            string respStr = resp.ToString();
            _logger.LogInformation($"Req id:{rnd}\n Time: {now}\n Resp: {respStr}\n");

            return Content(respStr);
        }

        /// <summary>
        /// Изменить поле юзера (состояние, фио, группу, подгруппу)
        /// </summary>
        [HttpPost("setUserField")]
        public IActionResult SetUserState([FromBody] object data)
        {
            _logger.LogInformation($"Req id:{rnd}\n Time: {now} Method: {System.Reflection.MethodInfo.GetCurrentMethod()}\n");


            dynamic req = JObject.Parse(data.ToString());
            dynamic resp = new JObject();
            resp.status = "OK";
            resp.response = new JObject();

            string idvk = req.idvk;
            string field = req.field;
            string value = req.value;

            User user = new User();
            user.IDVK = idvk;

           
            switch (field)
            {
                case "role":
                    user.ROLE = value;
                    break;
                case "arg":
                    user.ARG = value;
                    break;
                case "state":
                    user.STATE = value;
                    break;
                case "subgroup":
                    user.SUBGROUP = Convert.ToInt32(value);
                    break;
                }
            var res = _FBRepo.changeUserByIdvk(user);
            if (res == true)
            {
                resp.status = "OK";
                resp.response = "";
            }
            else
            {
                resp.status = "FAIL";
                resp.response = "";
            }

            string respStr = resp.ToString();
            _logger.LogInformation($"Req id:{rnd}\n Time: {now}\n Resp: {respStr}\n");

            return Content(respStr);
        }

        /// <summary>
        /// Получить актуальный неотвеченный вопрос для юзера
        /// </summary>
        [HttpPost("getQuestion")]
        public IActionResult GetQuestion([FromBody] object data)
        {
            _logger.LogInformation($"Req id:{rnd}\n Time: {now} Method: {System.Reflection.MethodInfo.GetCurrentMethod()}\n");

            dynamic req = JObject.Parse(data.ToString());
            dynamic resp = new JObject();
            resp.status = "OK";
            string idvk = req.idvk;

            int uId = -1;
            // получить айди юзера

            uId = _FBRepo._ctx.USERS.Where(u => u.IDVK == idvk).Select(u => u.ID).FirstOrDefault();

            // получить все опросы, на которые овтетил чел с этим idvk
            List<int?> usrAnsQ;
            usrAnsQ = _FBRepo._ctx.INTERVIEWRESULTS.Where(ir => ir.USER_ID == uId).Select(ir => ir.QUESTIONID).ToList();

            // получить все  активные опросы
            List<int> activeQ;
            activeQ = _FBRepo._ctx.QUESTIONS.Where(q => q.ISACTIVE == true).Select(q => q.ID).ToList();

            // первый из активных вопросов, на которые еще не отвечал юзер
            var result = activeQ.Where(aq => usrAnsQ.All(ua => ua != aq)).ToList();
            int? questionId = result.Count() == 0 ? null : result.First();
            if (questionId != null)
            {

                var answerVariantListJson = new JArray();
                //получить все варианты ответа этого вопроса
                var answerVariantList = _FBRepo._ctx.ANSWERVARIANTS.Where(aw => aw.QUESTION_ID == questionId).Select(aw => aw.ANSWER_VARIANT).ToList();
                foreach (var aw in answerVariantList)
                {
                    answerVariantListJson.Add(new JValue(aw));
                }

                //добавить взятый вопрос юзеру
                var user = _FBRepo._ctx.USERS.Where(u => u.IDVK == idvk).FirstOrDefault();
                user.LASTOPENEDQUESTION_ID = questionId;
                _FBRepo._ctx.SaveChanges();

                // получить текст вопроса
                string questionText;
                questionText = _FBRepo._ctx.QUESTIONS.Where(q => q.ID == questionId).Select(q => q.TEXT).FirstOrDefault().ToString();

                resp.response = new JObject(new JProperty("question", questionText),
                                            new JProperty("answerVariants", answerVariantListJson));
            }
            else
            {
                resp.response = new JValue("");
            }

            string respStr = resp.ToString();
            _logger.LogInformation($"Req id:{rnd}\n Time: {now}\n Resp: {respStr}\n");

            return Content(respStr);
        }

        /// <summary>
        /// Ответить на взятый юзером вопрос
        /// </summary>
        [HttpPost("answerQuestion")]
        public IActionResult AnswerQuestion([FromBody] object data)
        {
            _logger.LogInformation($"Req id:{rnd}\n Time: {now} Method: {System.Reflection.MethodInfo.GetCurrentMethod()}\n");

            dynamic req = JObject.Parse(data.ToString());
            dynamic resp = new JObject();
            resp.status = "OK";

            string idvk = req.idvk;
            string answer = req.answer;

            // получить айди вопроса из базы
            var user = new User();
            user = _FBRepo._ctx.USERS.Where(u => u.IDVK == idvk).FirstOrDefault();

            //получить айди ответа на этот вопрос
            int? ansId;
            ansId = _FBRepo._ctx.ANSWERVARIANTS.Where(aw => aw.QUESTION_ID == user.LASTOPENEDQUESTION_ID && aw.ANSWER_VARIANT == answer)
                                         .Select(aw => aw.ID).FirstOrDefault();

            // занести ответ на вопрос в базу результатов
            var interviewResult = new InterviewResult()
            {
                USER_ID = user.ID,
                QUESTIONID = user.LASTOPENEDQUESTION_ID,
                ANSWER_VARIANTID = ansId
            };

            _FBRepo._ctx.INTERVIEWRESULTS.AddRange(interviewResult);
            _FBRepo._ctx.SaveChanges();

            //обнулить открытый вопрос юзера
            user = _FBRepo._ctx.USERS.Where(u => u.IDVK == idvk).FirstOrDefault();
            user.LASTOPENEDQUESTION_ID = null;
            _FBRepo._ctx.SaveChanges();

            string respStr = resp.ToString();
            _logger.LogInformation($"Req id:{rnd}\n Time: {now}\n Resp: {respStr}\n");

            return Content(respStr);
        }

        /// <summary>
        /// Получить список чатов
        /// </summary>
        [HttpGet("getChatList")]
        public ActionResult GetChatList([FromQuery(Name = "role")] string role,
                                        [FromQuery(Name = "login")] string login)
        {
            _logger.LogInformation($"Req id:{rnd}\n Time: {now} Method: {System.Reflection.MethodInfo.GetCurrentMethod()}\n");

            List<Chat> lst = new List<Chat>();
            dynamic chats = new JArray();
            dynamic response = new JObject();

            switch (role)
            {
                case "student":
                    {
                        lst = _chatService.GetByGroup(login);
                        break;
                    }
                case "prepod":
                    {
                        lst = _chatService.GetByTeacher(login);
                        break;
                    }
            }
            foreach (var chat in lst)
            {
                chats.Add(new JObject(
                        new JProperty("prepodName", chat.prepodName),
                        new JProperty("group", chat.group),
                        new JProperty("lessonName", chat.lessonName),
                        new JProperty("messageCount", chat.messages.Count)
                    ));
            }
            

            response.status = "OK";
            response.response = chats;

            string respStr = response.ToString();
            _logger.LogInformation($"Req id:{rnd}\n Time: {now}\n Resp: {respStr}\n");

            return Content(respStr);
        }

       

        /// <summary>
        /// Получить чат
        /// </summary>
        [HttpGet("getChat")]
        public ActionResult GetChat([FromQuery(Name = "prepod")] string prepod,
                                    [FromQuery(Name = "group")] string group,
                                    [FromQuery(Name = "lessonName")] string lessonName)
        {
            _logger.LogInformation($"Req id:{rnd}\n Time: {now} Method: {System.Reflection.MethodInfo.GetCurrentMethod()}\n");

            JsonResponse resp = new JsonResponse();
            
            var msgList = new List<Message>();

            Chat chat = _chatService.GetByTeacherByGroupByLessonName(prepod, group, lessonName);

            if (chat != null)
            {
                resp.status = "OK";
                resp.response = chat.messages;
            }
            else
            {
                resp.status = "FAIL";
                resp.response = new String("No such teacher/group/lesson name");
            }
            
            string respStr = JsonConvert.SerializeObject(resp);

            _logger.LogInformation($"Req id:{rnd}\n Time: {now}\n Resp: {respStr}\n");

            return Content(respStr, "application/json");
        }

        /// <summary>
        /// Отправить сообщение в чат
        /// </summary>
        [HttpPost("sendMessage")]
        public ActionResult SendMessage([FromBody] object data)
        {
            _logger.LogInformation($"Req id:{rnd}\n Time: {now} Method: {System.Reflection.MethodInfo.GetCurrentMethod()}\n");

            dynamic req = JObject.Parse(data.ToString());

            string prepod = req.prepod;
            string group = req.group;
            string lessonName = req.lessonName;
            string text = req.text;

            JsonResponse resp = new JsonResponse();

            var msgList = new List<Message>();

            Chat chat = _chatService.GetByTeacherByGroupByLessonName(prepod, group, lessonName);

            if (chat != null)
            {
                // add message to the chat
                chat.messages.Add(new Message()
                {
                    time = now.ToString(),
                    msg = text
                });

                _chatService.Update(chat.Id, chat);

                resp.status = "OK";
                resp.response = chat.messages;
            }
            else
            {
                resp.status = "FAIL";
                resp.response = new String("No such teacher/group/lesson name");
            }

            string respStr = JsonConvert.SerializeObject(resp);

            _logger.LogInformation($"Req id:{rnd}\n Time: {now}\n Resp: {respStr}\n");

            return Content(respStr, "application/json");
        }

        /// <summary>
        /// Получить описание команды
        /// </summary>
        [HttpGet("handbook")]
        public ActionResult GetHandbookInfo([FromQuery(Name = "word")] string word)
        {
            _logger.LogInformation($"Req id:{rnd}\n Time: {now} Method: {System.Reflection.MethodInfo.GetCurrentMethod()}\n");

            JsonResponse resp = new JsonResponse();
            if (word == null)
            {
                var items = _FBRepo.GetHandbooks();
                resp.status = "OK";
                resp.response = items;
            }
            else
            {
                var desc = _FBRepo.GetHandbookInfo(word);
                if (desc != "")
                {
                    resp.status = "OK";
                    resp.response = desc;
                }
                else
                {
                    resp.status = "FAIL";
                    resp.response = "No such command";
                }
            }
 
            string respStr = JsonConvert.SerializeObject(resp);

            _logger.LogInformation($"Req id:{rnd}\n Time: {now}\n Resp: {respStr}\n");

            return Content(respStr, "application/json");
        }
    }
}
