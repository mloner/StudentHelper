﻿using Microsoft.Extensions.Logging;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using FirebirdSql.EntityFrameworkCore.Firebird;
using Microsoft.EntityFrameworkCore;

namespace StudentHelper.Repos
{
	public class FBRepo : DbContext
	{
		public FBRepo(string connectionStr)
		{
			_connString = connectionStr;
		}
		private string _connString;
		public DbSet<User> USERS { get; set; }
		protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
		{
			optionsBuilder.UseFirebird(_connString);
		}
		protected override void OnModelCreating(ModelBuilder modelo)
		{
			//Fluent Api
			modelo.Entity<User>(entity =>
			{
				entity.HasIndex(e => e.ID)
					.HasName("ID")
					.IsUnique();
			});
		}

		public class User
		{
			public int ID { get; set; }
			public string IDVK { get; set; }
			public string ROLE { get; set; }
			public string ARG { get; set; }
			public string STATE { get; set; }
		}
	}
}
