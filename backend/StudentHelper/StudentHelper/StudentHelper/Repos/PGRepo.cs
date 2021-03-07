using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using StudentHelper.Entities;
using static StudentHelper.Entities.PGEntities;

namespace StudentHelper.Repos
{
    public class PGRepo
    {
        public PGEntities _ctx;
        public PGRepo(PGEntities ctx)
        {
            _ctx = ctx;
        }
        //Teachers
        public List<Teacher> getAllTeachers()
        {
            return _ctx.Teachers.ToList();
        }
    }
}
