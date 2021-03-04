using Microsoft.Extensions.Logging;
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
		public DbSet<AnswerVariant> ANSWERVARIANTS { get; set; }
		public DbSet<Question> QUESTIONS { get; set; }
		public DbSet<InterviewResult> INTERVIEWRESULTS { get; set; }
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
			public int? SUBGROUP { get; set; }
			public int? LASTOPENEDQUESTION_ID { get; set; }
		}


		public class Question
		{
			public int ID { get; set; }
			public string? NAME { get; set; }
			public string? TEXT { get; set; }
			public bool? ISACTIVE { get; set; }

			public List<AnswerVariant> Answer_variants { get; set; } = new List<AnswerVariant>();
			public List<InterviewResult> InterviewResultS { get; set; } = new List<InterviewResult>();
		}

		public class AnswerVariant
		{
			public int ID { get; set; }
			public string? QUESTION_ID { get; set; }
			public string? ANSWER_VARIANT_ID { get; set; }

			public List<InterviewResult> InterviewResultS { get; set; } = new List<InterviewResult>();
			public Question Question { get; set; }
		}

		public class InterviewResult
		{
			public int ID { get; set; }
			public int? USER_ID { get; set; }
			public int? QUESTION_ID { get; set; }
			public int? ANSWER_VARIANT_ID { get; set; }

			public Question Question { get; set; }
			public AnswerVariant Answer_variant { get; set; }
		}

	}
}
