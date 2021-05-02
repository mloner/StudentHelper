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

		// Users
		public User getUserByIdvk(string idvk)
		{
			return _ctx.USERS.FirstOrDefault(u => u.IDVK == idvk);
		}

		public bool changeUserByIdvk(User user)
		{
			try
			{
				var _user = _ctx.USERS.FirstOrDefault(u => u.IDVK == user.IDVK);

				_user.LASTOPENEDQUESTION_ID = user.LASTOPENEDQUESTION_ID == null ? _user.LASTOPENEDQUESTION_ID : user.LASTOPENEDQUESTION_ID;
				_user.ROLE = user.ROLE == null ? _user.ROLE : user.ROLE;
				_user.STATE = user.STATE == null ? _user.STATE : user.STATE;
				_user.SUBGROUP = user.SUBGROUP == null ? _user.SUBGROUP : user.SUBGROUP;
				_user.ARG = user.ARG == null ? _user.ARG : user.ARG;

				_ctx.USERS.Update(_user);
				_ctx.SaveChanges();
			}
			catch (Exception e)
			{
				return false;
			}
			return true;
		}

		public string GetHandbookInfo(string word)
		{
			string res = "";
			try
			{
				var item = _ctx.HANDBOOK.Where(x => x.WORD == word.ToLower()).FirstOrDefault();
				if (item != null)
				{
					res = item.DESC;
				}
			}
			catch (Exception e)
			{
				
			}
			return res;
		}

		public List<string> GetHandbooks()
		{
			return _ctx.HANDBOOK.Select(x => x.WORD).ToList();
		}

		public FBRepo(FBEntities ctx)
		{
			_ctx = ctx;
		}
	}
}
