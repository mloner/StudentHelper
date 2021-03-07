using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace StudentHelper.Entities
{
    public class FBEntities : DbContext
	{
		public DbSet<User> USERS { get; set; }
		public DbSet<AnswerVariant> ANSWERVARIANTS { get; set; }
		public DbSet<Question> QUESTIONS { get; set; }
		public DbSet<InterviewResult> INTERVIEWRESULTS { get; set; }
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
			public int? QUESTION_ID { get; set; }
			public string? ANSWER_VARIANT { get; set; }

			public List<InterviewResult> InterviewResultS { get; set; } = new List<InterviewResult>();
			public Question Question { get; set; }
		}

		public class InterviewResult
		{
			public int ID { get; set; }
			public int? USER_ID { get; set; }
			public int? QUESTIONID { get; set; }
			public int? ANSWER_VARIANTID { get; set; }

			public Question QUESTION { get; set; }
			public AnswerVariant ANSWER_VARIANT { get; set; }
		}

		public FBEntities(DbContextOptions<FBEntities> dboe)
			: base(dboe)
		{ }

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
	}
}
