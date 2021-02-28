using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
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
    [ApiController]
    [Route("api")]
    public class ApiController : ControllerBase
    {
        private readonly ILogger<ApiController> _logger;

        public ApiController(ILogger<ApiController> logger)
        {
            _logger = logger;
        }
        public class Response
        {
            public string status { get; set; }
            public object response { get; set; }
        }

        [HttpGet]
        public ActionResult<String> Get()
        {
            return new JsonResult(new object());
        }

        [HttpPost]
        [AllowAnonymous]
        public IActionResult Post([FromBody] object data)
        {
            dynamic req = JObject.Parse(data.ToString());
            //// добавление данных
            //using (PGRepo db = new PGRepo())
            //{
            //    // создаем два объекта User
            //    TeacherPosition user1 = new TeacherPosition { name = "ert"};

            //    // добавляем их в бд
            //    db.TeacherPositions.AddRange(user1);
            //    db.SaveChanges();
            //}
            dynamic resp = new JObject();
            string command = req.command;
            
            try
            {
                switch (command)
                {
                    case "getTeacherPositions":
                        {

                            using (PGRepo db = new PGRepo())
                            {
                                resp.response = new JArray();
                                var teacherPositions = db.TeacherPositions.ToList();
                                foreach (TeacherPosition tp in teacherPositions)
                                {
                                    resp.response.Add(new JObject(
                                            new JProperty("id", tp.id),
                                            new JProperty("name", tp.name)
                                        ));
                                    resp.status = "OK";
                                }
                            }

                            break;
                        }
                    case "getAuthData":
                        {
                            using (PGRepo db = new PGRepo())
                            {
                                var users = db.Users.ToList();
                                string vkid = req.idvk;
                                var u = users.Where(u => u.idvk == vkid).ToList();
                                if (u.Count == 0)
                                {
                                    resp.status = "FAIL";
                                    resp.response = "WRONG_LOGIN";
                                }
                                else
                                {
                                    resp.status = "OK";
                                    resp.response = new JObject(new JProperty("role", u[0].role),
                                                                new JProperty("arg", u[0].arg));
                                }
                            }
                            break;
                        }
                    case "registerUser":
                        {
                            using (PGRepo db = new PGRepo())
                            {
                                var users = db.Users.ToList();
                                string idvk = req.idvk;
                                string role = req.role;
                                string arg = req.arg;
                                var user = users.FirstOrDefault(u => u.idvk == idvk);
                                user.role = role;
                                user.arg = arg;
                                db.SaveChanges();
                                resp.status = "OK";
                                resp.response = "OK";
                            }
                            break;
                        }
                    case "authorizationMobile":
                        {
                            string role = req.role;
                            switch(role)
                            {
                                case "student":
                                    {
                                        using (PGRepo db = new PGRepo())
                                        {
                                            var groups = db.Groups.ToList();
                                            string name = req.arg;
                                            var group = groups.Where(g => g.name == name).ToList();
                                            if (group.Count == 0)
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
                                        using (PGRepo db = new PGRepo())
                                        {
                                            var teachers = db.Teachers.ToList();
                                            string fio = req.arg;
                                            var teacher = teachers.Where(t => t.FIO == fio).ToList();
                                            if (teacher.Count == 0)
                                            {
                                                resp.status = "FAIL";
                                                resp.response = "WRONG_LOGIN";
                                            }
                                            else
                                            {
                                                string pass = req.pass;
                                                if (teacher[0].password == pass)
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
                    case "getUserState":
                        {
                            string idvk = req.idvk;
                            using (PGRepo db = new PGRepo())
                            {
                                var users = db.Users.ToList();
                                var user = users.FirstOrDefault(u => u.idvk == idvk);
                                if (user == null)
                                {
                                    resp.status = "OK";
                                    resp.response = "None";

                                    User newUser = new User() {
                                        idvk = idvk,
                                        state = "None"
                                    };
                                    db.Users.AddRange(newUser);
                                    db.SaveChanges();
                                }
                                else
                                {
                                    resp.status = "OK";
                                    resp.response = user.state;
                                }
                            }
                            break;
                        }
                    case "setUserState":
                        {
                            string idvk = req.idvk;
                            using (PGRepo db = new PGRepo())
                            {
                                var user = db.Users.ToList().FirstOrDefault(u => u.idvk == idvk);
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
                    case "getPrepodList":
                        {
                            resp.status = "OK";
                            resp.response = new JArray();
                            resp.response.Add(new JValue(
                                            "Скоря"
                                        ));
                            resp.response.Add(new JValue(
                                            "Грецов"
                                        ));
                            break;
                        }
                    case "getTeacherInfo":
                        {
                            using (PGRepo db = new PGRepo())
                            {

                            }
                            break;
                        }
                    case "checkGroup":
                        {
                            using (PGRepo db = new PGRepo())
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
                            using (PGRepo db = new PGRepo())
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

