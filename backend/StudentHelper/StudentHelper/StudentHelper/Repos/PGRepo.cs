using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;

namespace StudentHelper.Repos
{   
    public class PGRepo : DbContext
    {
        public class TeacherPosition
        {
            public int id { get; set; }
            public string name { get; set; }
        }
        public class User
        {
            public int id { get; set; }
            public string idvk { get; set; }
            public string role { get; set; }
            public string arg { get; set; }
        }
        public class Teacher
        {
            public int id { get; set; }
            public string FIO { get; set; }
            public int cafedra_id { get; set; }
            public int position_id { get; set; }
            public string password { get; set; }
        }
        public class Group
        {
            public int id { get; set; }
            public string name { get; set; }
            public string facultate_id { get; set; }
            public int course { get; set; }
        }

        public DbSet<TeacherPosition> TeacherPositions { get; set; }
        public DbSet<User> Users { get; set; }
        public DbSet<Teacher> Teachers { get; set; }
        public DbSet<Group> Groups { get; set; }
        public PGRepo()
        {
            Database.EnsureCreated();
        }
        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseNpgsql("Host=shipshon.fvds.ru;Port=5432;Database=studenthelperdb;Username=postgres;Password=hfk3egrk");
        }
    }
}
