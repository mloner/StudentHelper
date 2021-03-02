﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;

namespace StudentHelper.Repos
{
    public class PGRepo : DbContext
    {
        private readonly string _connectionString;
        public class TeacherPosition
        {
            public int id { get; set; }
            public string name { get; set; }

            public List<Teacher> Teachers { get; set; } = new List<Teacher>();

        }
        //public class User
        //{
        //    public int id { get; set; }
        //    public string idvk { get; set; }
        //    public string role { get; set; }
        //    public string arg { get; set; }
        //    public string state { get; set; }
        //}
        public class Teacher
        {
            public int id { get; set; }
            public string FIO { get; set; }
            public int Cathedraid { get; set; }
            public int TeacherPositionid { get; set; }
            public string password { get; set; }
            public string? phone { get; set; }
            public string? email { get; set; }

            public Cathedra Cathedra { get; set; }
            public TeacherPosition TeacherPosition { get; set; }
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
        }
        public class Group
        {
            public int id { get; set; }
            public string name { get; set; }
            public int facultate_id { get; set; }
            public int course { get; set; }
        }

        public DbSet<TeacherPosition> TeacherPositions { get; set; }
       // public DbSet<User> Users { get; set; }
        public DbSet<Teacher> Teachers { get; set; }
        public DbSet<Group> Groups { get; set; }
        public DbSet<Cathedra> Cathedras { get; set; }
        public DbSet<Facultate> Facultates { get; set; }

        public PGRepo(string connectionString)
        {
            _connectionString = connectionString;
            Database.EnsureCreated();
        }
        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseNpgsql(_connectionString);
        }
    }
}
