using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json.Linq;
using StudentHelper.Repos;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Net;
using static StudentHelper.Entities.FBEntities;
using static StudentHelper.Repos.FBRepo;

namespace StudentHelper.Controllers
{
    [Route("api")]
    [ApiController]
    public class ApiController : ControllerBase
    {
        private readonly ILogger<ApiController> _logger;
        private readonly IConfiguration _config;
        private readonly string _PGConStr;
        private readonly string _FBConStr;

        PGRepo _PGRepo;
        FBRepo _FBRepo;

        //[HttpGet("get302")]
        //public IActionResult Get302()
        //{
        //    return Redirect("~/swagger");
        //}

        //[HttpGet("get401")]
        //public IActionResult Get401()
        //{
        //    return Unauthorized();
        //}

        //[HttpGet("get501")]
        //public IActionResult Get501()
        //{
        //    return StatusCode(StatusCodes.Status501NotImplemented);
        //}
        public ApiController(ILogger<ApiController> logger, IConfiguration config, PGRepo PGRepo, FBRepo FBRepo)
        {
            _logger = logger;
            _config = config;
            //_PGConStr = _config["Config:PGConnectionString"];
            //_FBConStr = _config["Config:FBConnectionString"];
            _PGRepo = PGRepo;
            _FBRepo = FBRepo;
        }

        /// <summary>
        /// Получить список преподавателей
        /// </summary>
        [HttpGet("getPrepodList")]
        public IActionResult GetPrepodList()
        {
            var now = DateTime.Now;
            var rnd = new Random(now.Millisecond);
            _logger.LogInformation($"Req id:{rnd}\n" +
                                   $"Time: {now.ToLocalTime()}\n" +
                                   $"Method: {System.Reflection.MethodInfo.GetCurrentMethod()}\n");

            dynamic resp = new JObject();

            resp.response = new JArray();

            var teachers = _PGRepo.getAllTeachers();
            foreach (var t in teachers)
            {
                resp.response.Add(new JValue(t.FIO));
            }
            resp.status = "OK";

            string respStr = resp.ToString();
            _logger.LogInformation($"Req id:{rnd}\n" +
                                   $"Time: {now.ToLocalTime()}\n" +
                                   $"Resp: {respStr}\n");

            return Content(respStr, "application/json");
        }

        /// <summary>
        /// Получить информацию о преподавателе
        /// </summary>
        [HttpGet("getPrepodInfo")]
        public IActionResult GetPrepodInfo([FromQuery(Name = "fio")] string fio)
        {
            dynamic resp = new JObject();
            resp.response = new JObject();


            var teachers =
                from t in _PGRepo._ctx.Teachers
                join tp in _PGRepo._ctx.TeacherPositions on t.TeacherPositionid equals tp.id
                join c in _PGRepo._ctx.Cathedras on t.Cathedraid equals c.id
                join f in _PGRepo._ctx.Facultates on c.facultate_id equals f.id
                join td in _PGRepo._ctx.TeacherDegrees on t.TeacherDegreeid equals td.id
                select new
                {
                    FIO = t.FIO,
                    position = tp.name,
                    facultate = f.name,
                    location = c.location,
                    cathedraName = c.name,
                    phone = t.phone,
                    email = t.email,
                    degree = td.name
                };
            var teacher = teachers.FirstOrDefault(t => t.FIO == fio);
            if (teacher != null)
            {
                resp.status = "OK";
                resp.response = new JObject(
                    new JProperty("faculty", teacher.facultate),
                    new JProperty("cathedra", teacher.cathedraName),
                    new JProperty("location", teacher.location),
                    new JProperty("position", teacher.position),
                    new JProperty("phone", teacher.phone),
                    new JProperty("email", teacher.email),
                    new JProperty("degree", teacher.degree)
                    );
            }
            else
            {
                resp.status = "FAIL";
                resp.response = new JValue("No such teacher");
            }



            string respStr = resp.ToString();
            _logger.LogInformation("Time: " + DateTime.Now + "\n" + "Request: " + System.Reflection.MethodInfo.GetCurrentMethod() + "\n" + "Response: " + respStr);

            return Content(respStr, "application/json");
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
            dynamic resp = new JObject();
            resp.status = "OK";
            resp.response = new JObject();

            switch (role)
            {
                case "student":
                    {
                        string name = arg;
                        var group = _PGRepo._ctx.Groups.FirstOrDefault(g => g.name == name);
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
                        string fio = arg;
                        var teacher = _PGRepo._ctx.Teachers.FirstOrDefault(t => t.FIO == fio);
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
            _logger.LogInformation("Time: " + DateTime.Now + "\n" + "Request: " + System.Reflection.MethodInfo.GetCurrentMethod() + "\n" + "Response: " + respStr);

            return Content(respStr, "application/json");
        }

        /// <summary>
        /// Проверить, есть ли такая группа
        /// </summary>
        [HttpGet("checkGroup")]
        public IActionResult CheckGroup([FromQuery(Name = "group")] string group)
        {
            dynamic resp = new JObject();
            resp.status = "OK";
            resp.response = new JObject();

            string groupName = group;
            var reqGroup = _PGRepo._ctx.Groups.FirstOrDefault(g => g.name == groupName);
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
            _logger.LogInformation("Time: " + DateTime.Now + "\n" + "Request: " + System.Reflection.MethodInfo.GetCurrentMethod() + "\n" + "Response: " + respStr);

            return Content(respStr, "application/json");
        }

        /// <summary>
        /// Проверить, есть ли такой преподаватель
        /// </summary>
        [HttpGet("checkFio")]
        public IActionResult CheckFio([FromQuery(Name = "fio")] string fio)
        {
            dynamic resp = new JObject();
            resp.status = "OK";
            resp.response = new JObject();

                var teacher = _PGRepo._ctx.Teachers.FirstOrDefault(t => t.FIO == fio);
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
            _logger.LogInformation("Time: " + DateTime.Now + "\n" + "Request: " + System.Reflection.MethodInfo.GetCurrentMethod() + "\n" + "Response: " + respStr);

            return Content(respStr, "application/json");
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

                var schedules =
                    from l in _PGRepo._ctx.Lessons
                    join c in _PGRepo._ctx.Classes on l.class_id equals c.id
                    join ltp in _PGRepo._ctx.LessonTypes on l.lesson_type_id equals ltp.id
                    join t in _PGRepo._ctx.Teachers on l.teacher_id equals t.id
                    join g in _PGRepo._ctx.Groups on l.group_id equals g.id
                    join sn in _PGRepo._ctx.SubjectNames on l.subject_name_id equals sn.id
                    join wd in _PGRepo._ctx.WeekDays on l.day_num_id equals wd.id
                    join ltm in _PGRepo._ctx.LessonTimes on l.start_lesson_num equals ltm.id
                    select new
                    {
                        subjectName = sn.name,
                        prepodName = t.FIO,
                        className = c.name,
                        lessonType = ltp.name,
                        lessonStart = ltm.start_time,
                        lessonEnd = (from ltm1 in _PGRepo._ctx.LessonTimes
                                     where ltm1.id == (l.start_lesson_num + l.lesson_duration - 1)
                                     select ltm1.end_time).Single(),
                        groupName = g.name,
                        subGroup = l.subgroup,
                        isRemote = l.remote,
                        weekNum = l.week_num,
                        weekDayName = wd.name,
                        duration = l.lesson_duration,
                        description = l.description,
                        weekdayNum = wd.id
                    };
                var schedule = schedules.Where(s => s.groupName == group);

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
                    schedule = schedules.Where(s => s.groupName == group &&
                                                    s.weekNum == weekNumInt &&
                                                    s.weekDayName == RUmonth).OrderBy(s => s.lessonStart);

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
            _logger.LogInformation("Time: " + DateTime.Now + "\n" + "Request: " + System.Reflection.MethodInfo.GetCurrentMethod() + "\n" + "Response: " + respStr);

            return Content(respStr, "application/json");
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

            var schedules =
                from l in _PGRepo._ctx.Lessons
                join c in _PGRepo._ctx.Classes on l.class_id equals c.id
                join ltp in _PGRepo._ctx.LessonTypes on l.lesson_type_id equals ltp.id
                join t in _PGRepo._ctx.Teachers on l.teacher_id equals t.id
                join g in _PGRepo._ctx.Groups on l.group_id equals g.id
                join sn in _PGRepo._ctx.SubjectNames on l.subject_name_id equals sn.id
                join wd in _PGRepo._ctx.WeekDays on l.day_num_id equals wd.id
                join ltm in _PGRepo._ctx.LessonTimes on l.start_lesson_num equals ltm.id
                select new
                {
                    subjectName = sn.name,
                    prepodName = t.FIO,
                    className = c.name,
                    lessonType = ltp.name,
                    lessonStart = ltm.start_time,
                    lessonEnd = (from ltm1 in _PGRepo._ctx.LessonTimes
                                    where ltm1.id == (l.start_lesson_num + l.lesson_duration - 1)
                                    select ltm1.end_time).Single(),
                    groupName = g.name,
                    subGroup = l.subgroup,
                    isRemote = l.remote,
                    weekNum = l.week_num,
                    weekDayName = wd.name,
                    duration = l.lesson_duration,
                    description = l.description,
                    weekdayNum = wd.id
                };
            var schedule = schedules.Where(s => s.prepodName == fio);

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
                schedule = schedules.Where(s => s.prepodName == fio &&
                                                s.weekNum == weekNumInt &&
                                                s.weekDayName == RUmonth).OrderBy(s => s.lessonStart);

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
            _logger.LogInformation("Time: " + DateTime.Now + "\n" + "Request: " + System.Reflection.MethodInfo.GetCurrentMethod() + "\n" + "Response: " + respStr);

            return Content(respStr, "application/json");
        }

        // <summary>
        // Получить текущее состояние юзера чат-бота
        // </summary>
        [HttpPost("getUserState")]
        public IActionResult GetUserState([FromBody] object data)
        {
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
            _logger.LogInformation("Time: " + DateTime.Now + "\n" + "Request: " + System.Reflection.MethodInfo.GetCurrentMethod() + "\n" + "Response: " + respStr);

            return Content(respStr, "application/json");
        }

        /// <summary>
        /// Зарегистрировать юзера в чат-боте
        /// </summary>
        [HttpPost("registerUser")]
        public IActionResult RegisterUser([FromBody] object data)
        {
            dynamic req = JObject.Parse(data.ToString());
            dynamic resp = new JObject();
            resp.status = "OK";
            resp.response = new JObject();

                string idvk = req.idvk;

                string role = req.role;
                string arg = req.arg;
                int? subgroup = req.subGroup;

                var user = _FBRepo._ctx.USERS.FirstOrDefault(u => u.IDVK == idvk);

                user.ROLE = role;
                if (role != "student")
                    user.ARG = arg;
                user.SUBGROUP = subgroup;

                _FBRepo._ctx.SaveChanges();

                resp.status = "OK";
                resp.response = "OK";


            string respStr = resp.ToString();
            _logger.LogInformation("Time: " + DateTime.Now + "\n" + "Request: " + System.Reflection.MethodInfo.GetCurrentMethod() + "\n" + "Response: " + respStr);

            return Content(respStr, "application/json");
        }

        /// <summary>
        /// Изменить поле юзера (состояние, фио, группу, подгруппу)
        /// </summary>
        [HttpPost("setUserField")]
        public IActionResult SetUserState([FromBody] object data)
        {
            dynamic req = JObject.Parse(data.ToString());
            dynamic resp = new JObject();
            resp.status = "OK";
            resp.response = new JObject();

            string idvk = req.idvk;
            string field = req.field;
            string value = req.value;
            var user = _FBRepo._ctx.USERS.FirstOrDefault(u => u.IDVK == idvk);
            if (user == null)
            {
                resp.status = "FAIL";
                resp.response = "No such user";
            }
            else
            {
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

                _FBRepo._ctx.SaveChanges();

                resp.status = "OK";
                resp.response = "";
            }

            string respStr = resp.ToString();
            _logger.LogInformation("Time: " + DateTime.Now + "\n" + "Request: " + System.Reflection.MethodInfo.GetCurrentMethod() + "\n" + "Response: " + respStr);

            return Content(respStr, "application/json");
        }

        [HttpPost("getQuestion")]
        public IActionResult GetQuestion([FromBody] object data)
        {
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

            //foreach (var ua in usrAnsQ)
            //{
            //    Console.WriteLine(ua);
            //}

            // получить все  активные опросы
            List<int> activeQ;
            activeQ = _FBRepo._ctx.QUESTIONS.Where(q => q.ISACTIVE == true).Select(q => q.ID).ToList();

            //foreach (var aq in activeQ)
            //{
            //    Console.WriteLine(aq);
            //}

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

            //foreach (var q in result)
            //{
            //    Console.WriteLine(q);
            //}

            string respStr = resp.ToString();
            _logger.LogInformation("Time: " + DateTime.Now + "\n" + "Request: " + System.Reflection.MethodInfo.GetCurrentMethod() + "\n" + "Response: " + respStr);

            return Content(respStr, "application/json");
        }

        [HttpPost("answerQuestion")]
        public IActionResult AnswerQuestion([FromBody] object data)
        {
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
            _logger.LogInformation("Time: " + DateTime.Now + "\n" + "Request: " + System.Reflection.MethodInfo.GetCurrentMethod() + "\n" + "Response: " + respStr);

            return Content(respStr, "application/json");
        }
    }
}