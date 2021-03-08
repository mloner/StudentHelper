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

        public Teacher getTeacherFullInfoByFio(string fio)
        {
            return _ctx.Teachers
                .Include(t => t.Cathedra)
                .Include(t => t.Cathedra.Facultate)
                .Include(t => t.TeacherPosition)
                .Include(t => t.TeacherDegree)
                .FirstOrDefault(t => t.FIO == fio);
        }

        public Teacher getTeacherByFio(string fio)
        {
            return _ctx.Teachers.FirstOrDefault(t => t.FIO == fio);
        }

        // Groups
        public Group getGroupByName(string groupName)
        {
            return _ctx.Groups.FirstOrDefault(g => g.name == groupName);
        }
    }
}
