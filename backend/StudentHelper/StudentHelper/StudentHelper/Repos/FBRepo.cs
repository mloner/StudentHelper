using Microsoft.Extensions.Logging;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using FirebirdSql.EntityFrameworkCore.Firebird;
using Microsoft.EntityFrameworkCore;
using StudentHelper.Entities;
using static StudentHelper.Entities.FBEntities;

namespace StudentHelper.Repos
{
	public class FBRepo
	{
		public FBEntities _ctx;

		public User getUserByIdvk(string idvk)
		{
			return _ctx.USERS.FirstOrDefault(u => u.IDVK == idvk);
		}

		public FBRepo(FBEntities ctx)
		{
			_ctx = ctx;
		}


	}
}
