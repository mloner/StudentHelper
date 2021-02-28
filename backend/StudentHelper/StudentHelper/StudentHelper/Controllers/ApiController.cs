using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using StudentHelper.Repos;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text.Json;
using System.Threading.Tasks;
using System.Web.Helpers;
using static StudentHelper.Repos.PGRepo;

namespace StudentHelper.Controllers
{
    [Route("api")]
    [ApiController]
    public class ApiController : ControllerBase
    {
        private readonly ILogger<ApiController> _logger;
        private readonly IConfiguration _config;
        private readonly string _PGConStr;
        public ApiController(ILogger<ApiController> logger, IConfiguration config)
        {
            _logger = logger;
            _config = config;
            _PGConStr = _config["Config:PGConnectionString"];
        }

        [HttpGet]
        public IActionResult Get()
        {
            return Content("Do Post request", "text/plain");
        }

        [HttpPost]
        [AllowAnonymous]
        public IActionResult Post([FromBody] object data)
        {
            dynamic req = JObject.Parse(data.ToString());
            dynamic resp = new JObject();

            string command = req.command;
            try
            {
                switch (command)
                {
                    // Получить данные юзера, если он есть в базе
                    case "getAuthData":
                        {
                            using (PGRepo db = new PGRepo(_PGConStr))
                            {
                                string idvk = req.idvk;
                                var user = db.Users.FirstOrDefault(u => u.idvk == idvk);
                                if (user == null)
                                {
                                    resp.status = "FAIL";
                                    resp.response = "WRONG_LOGIN";
                                }
                                else
                                {
                                    resp.status = "OK";
                                    resp.response = new JObject(
                                                                new JProperty("role", user.role),
                                                                new JProperty("arg", user.arg)
                                                                );
                                }
                            }
                            break;
                        }
                    // Зарегистрировать юзера
                    case "registerUser":
                        {
                            using (PGRepo db = new PGRepo(_PGConStr))
                            {
                                string idvk = req.idvk;
                                string role = req.role;
                                string arg = req.arg;

                                var user = db.Users.FirstOrDefault(u => u.idvk == idvk);

                                user.role = role;
                                user.arg = arg;

                                db.SaveChanges();

                                resp.status = "OK";
                                resp.response = "OK";
                            }
                            break;
                        }
                    // Авторизоваться с мобильного
                    case "authorizationMobile":
                        {
                            string role = req.role;
                            switch(role)
                            {
                                case "student":
                                    {
                                        using (PGRepo db = new PGRepo(_PGConStr))
                                        {
                                            string name = req.arg;
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
                                            string fio = req.arg;
                                            var teacher = db.Teachers.FirstOrDefault(t => t.FIO == fio);
                                            if (teacher == null)
                                            {
                                                resp.status = "FAIL";
                                                resp.response = "WRONG_LOGIN";
                                            }
                                            else
                                            {
                                                // check password
                                                string pass = req.pass;
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
                            break;
                        }
                    // Получить текущее состояние юзера
                    case "getUserState":
                        {
                            using (PGRepo db = new PGRepo(_PGConStr))
                            {
                                string idvk = req.idvk;
                                var user = db.Users.FirstOrDefault(u => u.idvk == idvk);
                                if (user == null)
                                {  
                                    User newUser = new User() {
                                        idvk = idvk,
                                        state = "None"
                                    };
                                    db.Users.AddRange(newUser);
                                    db.SaveChanges();
                                    resp.status = "OK";
                                    resp.response = new JObject(
                                                                new JProperty("role", newUser.role),
                                                                new JProperty("arg", newUser.arg),
                                                                new JProperty("state", newUser.state)
                                                                );
                                }
                                else
                                {
                                    resp.status = "OK";
                                    resp.response = new JObject(new JProperty("role" , user.role),
                                                                new JProperty("arg", user.arg),
                                                                new JProperty("state", user.state));
                                }
                            }
                            break;
                        }
                    // Задать текущее состояние юзера
                    case "setUserState":
                        {
                            string idvk = req.idvk;
                            using (PGRepo db = new PGRepo(_PGConStr))
                            {
                                var user = db.Users.FirstOrDefault(u => u.idvk == idvk);
                                if (user == null)
                                {
                                    resp.status = "FAIL";
                                    resp.response = "No such user";
                                }
                                else
                                {
                                    string state = req.state;
                                    user.state = state;

                                    db.SaveChanges();

                                    resp.status = "OK";
                                    resp.response = "";
                                }
                            }
                            break;
                        }
                    // Получить список ФИО преподавателей
                    case "getPrepodList":
                        {
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
                            break;
                        }
                    // Получить инф-ю о преподавателе
                    case "getPrepodInfo":
                        {
                            using (PGRepo db = new PGRepo(_PGConStr))
                            {
                                string fio = req.fio;
                                var teachers =
                                    from t in db.Teachers
                                    join tp in db.TeacherPositions on t.TeacherPositionid equals tp.id
                                    join c in db.Cathedras on t.Cathedraid equals c.id
                                    join f in db.Facultates on c.facultate_id equals f.id
                                    select new { FIO = t.FIO, position = tp.name, facultate = f.name, location = c.location, cathedraName = c.name };
                                var teacher = teachers.FirstOrDefault(t => t.FIO == fio);
                                if (teacher != null)
                                {
                                    resp.status = "OK";
                                    resp.response = new JObject(
                                        new JProperty("faculty", teacher.facultate),
                                        new JProperty("cathedra", teacher.cathedraName),
                                        new JProperty("location", teacher.location),
                                        new JProperty("position", teacher.position),
                                        new JProperty("phone", "8 800 555 35 35"),
                                        new JProperty("mail", "mail@mail.ru")
                                        );
                                }
                                else
                                {
                                    resp.status = "FAIL";
                                    resp.response = new JValue("No such teacher");
                                }
                            }
                            break;
                        }
                    case "checkGroup":
                        {
                            using (PGRepo db = new PGRepo(_PGConStr))
                            {
                                string groupName = req.group;
                                var group = db.Groups.ToList().FirstOrDefault(g => g.name == groupName);
                                if (group != null)
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
                            break;
                        }
                    case "checkFIO":
                        {
                            using (PGRepo db = new PGRepo(_PGConStr))
                            {
                                string fio = req.FIO;
                                var teacher = db.Teachers.ToList().FirstOrDefault(t => t.FIO == fio);
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
                            break;
                        }
                    default:
                        { 
                            resp.status = "FAIL";
                            resp.response = new JObject("Unknown command");
                            break;
                        }
                }
            }
            catch (Exception ex)
            {
                resp.status = "FAIL";
                resp.response = ex.Message;
            }
            return Content(resp.ToString(), "application/json");
        }
    }
}

