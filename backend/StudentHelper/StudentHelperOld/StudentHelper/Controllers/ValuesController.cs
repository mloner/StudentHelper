using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace StudentHelper.Controllers
{
    [Route("api")]
    [ApiController]
    public class ValuesController : ControllerBase
    {
        public class Response
        {
            public string status;
            public object response;
        }
        // GET api
        [HttpGet]
        public JsonResult Get()
        {
            return new JsonResult(new Response { status = "OK" , response = "OK"} );
        }

        // GET api/values/5
        //[HttpGet("{id}")]
        //public ActionResult<string> Get(int id)
        //{
        //    return "value";
        //}

        // POST api
        //[HttpPost]
        //public ActionResult<string> Post([FromForm] dynamic json)
        //{
        //    dynamic data = serializer.Deserialize(json, typeof(object));
        //    return value["dsf"];
        //}

        //// PUT api/values/5
        //[HttpPut("{id}")]
        //public void Put(int id, [FromBody] string value)
        //{
        //}

        //// DELETE api/values/5
        //[HttpDelete("{id}")]
        //public void Delete(int id)
        //{
        //}
    }
}
