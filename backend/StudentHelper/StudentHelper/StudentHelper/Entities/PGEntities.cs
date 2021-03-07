using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace StudentHelper.Entities
{
    public class PGEntities : DbContext
    {
        public class TeacherPosition
        {
            public int id { get; set; }
            public string name { get; set; }

            public List<Teacher> Teachers { get; set; } = new List<Teacher>();

        }
        public class TeacherDegree
        {
            public int id { get; set; }
            public string name { get; set; }

            public List<Teacher> Teachers { get; set; } = new List<Teacher>();
        }
        public class Teacher
        {
            public int id { get; set; }
            public string FIO { get; set; }
            public int Cathedraid { get; set; }
            public int TeacherPositionid { get; set; }
            public int TeacherDegreeid { get; set; }
            public string password { get; set; }
            public string? phone { get; set; }
            public string? email { get; set; }

            public Cathedra Cathedra { get; set; }
            public TeacherPosition TeacherPosition { get; set; }
            public TeacherDegree TeacherDegree { get; set; }
        }
        public class Cathedra
        {
            public int id { get; set; }
            public string name { get; set; }
            public int facultate_id { get; set; }
            public string location { get; set; }

            public Facultate Facultate { get; set; }
            public List<Teacher> Teachers { get; set; } = new List<Teacher>();

        }
        public class Facultate
        {
            public int id { get; set; }
            public string name { get; set; }
            public List<Cathedra> Cathedras { get; set; } = new List<Cathedra>();
            public List<Group> Groups { get; set; } = new List<Group>();
        }
        public class Group
        {
            public int id { get; set; }
            public string name { get; set; }
            public int Facultateid { get; set; }
            public int course { get; set; }
            public Facultate Facultate { get; set; }
            public List<Lesson> Lessons { get; set; } = new List<Lesson>();
        }
        public class Class
        {
            public int id { get; set; }
            public string name { get; set; }

            public List<Lesson> Lessons { get; set; } = new List<Lesson>();
        }
        public class LessonType
        {
            public int id { get; set; }
            public string name { get; set; }

            public List<Lesson> Lessons { get; set; } = new List<Lesson>();
        }
        public class SubjectName
        {
            public int id { get; set; }
            public string name { get; set; }

            public List<Lesson> Lessons { get; set; } = new List<Lesson>();
        }
        public class WeekDay
        {
            public int id { get; set; }
            public string name { get; set; }

            public List<Lesson> Lessons { get; set; } = new List<Lesson>();
        }
        public class Lesson
        {
            public int id { get; set; }
            public int week_num { get; set; }
            public int day_num_id { get; set; }
            public int subject_name_id { get; set; }
            public int group_id { get; set; }
            public int subgroup { get; set; }
            public int teacher_id { get; set; }
            public int class_id { get; set; }
            public int lesson_type_id { get; set; }
            public int start_lesson_num { get; set; }
            public int lesson_duration { get; set; }
            public bool remote { get; set; }
            public string description { get; set; }
            public Class Class { get; set; }
            public LessonType LessonType { get; set; }
            public Teacher Teacher { get; set; }
            public Group Group { get; set; }
            public SubjectName SubjectName { get; set; }
            public WeekDay WeekDay { get; set; }
            public LessonTime LessonTime { get; set; }
        }
        public class LessonTime
        {
            public int id { get; set; }
            public string start_time { get; set; }
            public string end_time { get; set; }
            public List<Lesson> Lessons { get; set; } = new List<Lesson>();
        }

        public DbSet<TeacherPosition> TeacherPositions { get; set; }
        public DbSet<TeacherDegree> TeacherDegrees { get; set; }
        public DbSet<Teacher> Teachers { get; set; }
        public DbSet<Cathedra> Cathedras { get; set; }
        public DbSet<Facultate> Facultates { get; set; }
        public DbSet<Group> Groups { get; set; }
        public DbSet<Class> Classes { get; set; }
        public DbSet<LessonType> LessonTypes { get; set; }
        public DbSet<SubjectName> SubjectNames { get; set; }
        public DbSet<WeekDay> WeekDays { get; set; }
        public DbSet<Lesson> Lessons { get; set; }
        public DbSet<LessonTime> LessonTimes { get; set; }

        public PGEntities(DbContextOptions<PGEntities> dboe)
            : base(dboe)
        {}
    }
}
