﻿using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json.Linq;
using StudentHelper.Repos;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
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
        public ApiController(ILogger<ApiController> logger, IConfiguration config)
        {
            _logger = logger;
            _config = config;
            _PGConStr = _config["Config:PGConnectionString"];
            _FBConStr = _config["Config:FBConnectionString"];
        }

        /// <summary>
        /// Получить список преподавателей
        [HttpGet("getPrepodList")]
        public IActionResult GetPrepodList()
        {
            dynamic resp = new JObject();
            resp.status = "OK";
            resp.response = new JArray();
            using (PGRepo db = new PGRepo(_PGConStr))
            {
                var teachers = db.Teachers.ToList();
                foreach (var t in teachers)
                {
                    resp.response.Add(new JValue(t.FIO));
                }
            }
            string respStr = resp.ToString();
            _logger.LogInformation("Time: " + DateTime.Now + "\n" + "Request: " + System.Reflection.MethodInfo.GetCurrentMethod() + "\n" + "Response: " + respStr);

            return Content(respStr, "application/json");
        }

        /// <summary>
        /// Получить информацию о преподавателе
        [HttpGet("getPrepodInfo")]
        public IActionResult GetPrepodInfo([FromQuery(Name = "fio")] string fio)
        {
            dynamic resp = new JObject();
            resp.status = "OK";
            resp.response = new JObject();

            using (PGRepo db = new PGRepo(_PGConStr))
            {
                var teachers =
                    from t in db.Teachers
                    join tp in db.TeacherPositions on t.TeacherPositionid equals tp.id
                    join c in db.Cathedras on t.Cathedraid equals c.id
                    join f in db.Facultates on c.facultate_id equals f.id
                    join td in db.TeacherDegrees on t.TeacherDegreeid equals td.id
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
        }

        /// <summary>
        /// Авторизация с мобильного приложения
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
                        using (PGRepo db = new PGRepo(_PGConStr))
                        {
                            string name = arg;
                            var group = db.Groups.FirstOrDefault(g => g.name == name);
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
                        }
                        break;
                    }
                case "prepod":
                    {
                        using (PGRepo db = new PGRepo(_PGConStr))
                        {
                            string fio = arg;
                            var teacher = db.Teachers.FirstOrDefault(t => t.FIO == fio);
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
        [HttpGet("checkGroup")]
        public IActionResult CheckGroup([FromQuery(Name = "group")] string group)
        {
            dynamic resp = new JObject();
            resp.status = "OK";
            resp.response = new JObject();

            using (PGRepo db = new PGRepo(_PGConStr))
            {
                string groupName = group;
                var reqGroup = db.Groups.FirstOrDefault(g => g.name == groupName);
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
            }

            string respStr = resp.ToString();
            _logger.LogInformation("Time: " + DateTime.Now + "\n" + "Request: " + System.Reflection.MethodInfo.GetCurrentMethod() + "\n" + "Response: " + respStr);

            return Content(respStr, "application/json");
        }

        /// <summary>
        /// Проверить, есть ли такой преподаватель
        [HttpGet("checkFio")]
        public IActionResult CheckFio([FromQuery(Name = "fio")] string fio)
        {
            dynamic resp = new JObject();
            resp.status = "OK";
            resp.response = new JObject();

            using (PGRepo db = new PGRepo(_PGConStr))
            {
                var teacher = db.Teachers.FirstOrDefault(t => t.FIO == fio);
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
            }

            string respStr = resp.ToString();
            _logger.LogInformation("Time: " + DateTime.Now + "\n" + "Request: " + System.Reflection.MethodInfo.GetCurrentMethod() + "\n" + "Response: " + respStr);

            return Content(respStr, "application/json");
        }

        /// <summary>
        /// Получить расписание на сегодня/завтра/две недели/любую дату для определенной группы
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
            else if((dateSchedule < new DateTime(dateSchedule.Year, 9,1) && (todayFirstDayNum == todayFullWeekNum)))
            {
                weekNumInt = weekNumChetn ? 1 : 2;
            }
            
            string RUmonth = dateSchedule.ToString("dddd", CultureInfo.GetCultureInfo("ru-ru"));

            dynamic resp = new JObject();
            resp.status = "OK";

            using (PGRepo db = new PGRepo(_PGConStr))
            {
                var schedules =
                    from l in db.Lessons
                    join c in db.Classes on l.class_id equals c.id
                    join ltp in db.LessonTypes on l.lesson_type_id equals ltp.id
                    join t in db.Teachers on l.teacher_id equals t.id
                    join g in db.Groups on l.group_id equals g.id
                    join sn in db.SubjectNames on l.subject_name_id equals sn.id
                    join wd in db.WeekDays on l.day_num_id equals wd.id
                    join ltm in db.LessonTimes on l.start_lesson_num equals ltm.id
                    select new
                    {
                        subjectName = sn.name,
                        prepodName = t.FIO,
                        className = c.name,
                        lessonType = ltp.name,
                        lessonStart = ltm.start_time,
                        lessonEnd = (from ltm1 in db.LessonTimes
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
            }
            string respStr = resp.ToString();
            _logger.LogInformation("Time: " + DateTime.Now + "\n" + "Request: " + System.Reflection.MethodInfo.GetCurrentMethod() + "\n" + "Response: " + respStr);

            return Content(respStr, "application/json");
        }

        /// <summary>
        /// Получить расписание на сегодня/завтра/две недели/любую дату для определенного преподавателя
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

            using (PGRepo db = new PGRepo(_PGConStr))
            {
                var schedules =
                    from l in db.Lessons
                    join c in db.Classes on l.class_id equals c.id
                    join ltp in db.LessonTypes on l.lesson_type_id equals ltp.id
                    join t in db.Teachers on l.teacher_id equals t.id
                    join g in db.Groups on l.group_id equals g.id
                    join sn in db.SubjectNames on l.subject_name_id equals sn.id
                    join wd in db.WeekDays on l.day_num_id equals wd.id
                    join ltm in db.LessonTimes on l.start_lesson_num equals ltm.id
                    select new
                    {
                        subjectName = sn.name,
                        prepodName = t.FIO,
                        className = c.name,
                        lessonType = ltp.name,
                        lessonStart = ltm.start_time,
                        lessonEnd = (from ltm1 in db.LessonTimes
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
            }
            string respStr = resp.ToString();
            _logger.LogInformation("Time: " + DateTime.Now + "\n" + "Request: " + System.Reflection.MethodInfo.GetCurrentMethod() + "\n" + "Response: " + respStr);

            return Content(respStr, "application/json");
        }

        /// <summary>
        /// Получить текущее состояние юзера чат-бота
        [HttpPost("getUserState")]
        public IActionResult GetUserState([FromBody] object data)
        {
            dynamic req = JObject.Parse(data.ToString());
            dynamic resp = new JObject();
            resp.status = "OK";
            resp.response = new JObject();

            using (var db = new FBRepo(_FBConStr))
            {
                string idvk = req.idvk;
                var user = db.USERS.FirstOrDefault(u => u.IDVK == idvk);
                if (user == null)
                {
                    User newUser = new User()
                    {
                        IDVK = idvk,
                        STATE = "None"
                    };
                    db.USERS.AddRange(newUser);
                    db.SaveChanges();
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
            }


            string respStr = resp.ToString();
            _logger.LogInformation("Time: " + DateTime.Now + "\n" + "Request: " + System.Reflection.MethodInfo.GetCurrentMethod() + "\n" + "Response: " + respStr);

            return Content(respStr, "application/json");
        }

        /// <summary>
        /// Зарегистрировать юзера в чат-боте
        [HttpPost("registerUser")]
        public IActionResult RegisterUser([FromBody] object data)
        {
            dynamic req = JObject.Parse(data.ToString());
            dynamic resp = new JObject();
            resp.status = "OK";
            resp.response = new JObject();

            using (var db = new FBRepo(_FBConStr))
            {
                string idvk = req.idvk;

                string role = req.role;
                string arg = req.arg;
                int? subgroup = req.subGroup;

                var user = db.USERS.FirstOrDefault(u => u.IDVK == idvk);

                user.ROLE = role;
                if(role != "student")
                    user.ARG = arg;
                user.SUBGROUP = subgroup;

                db.SaveChanges();

                resp.status = "OK";
                resp.response = "OK";
            }


            string respStr = resp.ToString();
            _logger.LogInformation("Time: " + DateTime.Now + "\n" + "Request: " + System.Reflection.MethodInfo.GetCurrentMethod() + "\n" + "Response: " + respStr);

            return Content(respStr, "application/json");
        }

        /// <summary>
        /// Изменить поле юзера (состояние, фио, группу, подгруппу)
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
            using (var db = new FBRepo(_FBConStr))
            {
                var user = db.USERS.FirstOrDefault(u => u.IDVK == idvk);
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

                    db.SaveChanges();

                    resp.status = "OK";
                    resp.response = "";
                }
            }

            string respStr = resp.ToString();
            _logger.LogInformation("Time: " + DateTime.Now + "\n" + "Request: " + System.Reflection.MethodInfo.GetCurrentMethod() + "\n" + "Response: " + respStr);

            return Content(respStr, "application/json");
        }




    //    // idvk
    //    [HttpPost("getQuestion")]
    //    public IActionResult GetQuestion([FromBody] object data)
    //    {
    //        dynamic req = JObject.Parse(data.ToString());
    //        dynamic resp = new JObject();
    //        resp.status = "OK";
    //        resp.response = new JObject();
    //        string idvk = req.idvk;

    //        //get all answered questions for this user
    //        IQueryable<Question> usrAnsQ;
    //        using (var db = new FBRepo(_FBConStr))
    //        {

    //        }

           


    //    string respStr = resp.ToString();
    //    _logger.LogInformation("Time: " + DateTime.Now + "\n" + "Request: " + System.Reflection.MethodInfo.GetCurrentMethod() + "\n" + "Response: " + respStr);

    //        return Content(respStr, "application/json");
    //}

}
}