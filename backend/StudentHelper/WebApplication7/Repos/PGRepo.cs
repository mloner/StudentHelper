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

        //Schedule
        public List<ScheduleView> getScheduleByGroup(string group)
        {
            var schedules =
                from l in _ctx.Lessons
                join c in _ctx.Classes on l.class_id equals c.id
                join ltp in _ctx.LessonTypes on l.lesson_type_id equals ltp.id
                join t in _ctx.Teachers on l.teacher_id equals t.id
                join g in _ctx.Groups on l.group_id equals g.id
                join sn in _ctx.SubjectNames on l.subject_name_id equals sn.id
                join wd in _ctx.WeekDays on l.day_num_id equals wd.id
                join ltm in _ctx.LessonTimes on l.start_lesson_num equals ltm.id
                where g.name == @group
                select new ScheduleView
                {
                    subjectName = sn.name,
                    prepodName = t.FIO,
                    className = c.name,
                    lessonType = ltp.name,
                    lessonStart = ltm.start_time,
                    lessonEnd = (from ltm1 in _ctx.LessonTimes
                                 where ltm1.id == (l.start_lesson_num + l.lesson_duration - 1)
                                 select ltm1.end_time).Single(),
                    groupName = g.name,
                    subGroup = l.subgroup,
                    isRemote = l.remote,
                    weekNum = l.week_num,
                    weekDayName = wd.name,
                    duration = l.lesson_duration,
                    description = l.description,
                    weekdayNum = wd.id
                };
            return schedules.ToList();
        }

        public List<ScheduleView> getScheduleByTeacherFio(string fio)
        {
            var schedules =
                from l in _ctx.Lessons
                join c in _ctx.Classes on l.class_id equals c.id
                join ltp in _ctx.LessonTypes on l.lesson_type_id equals ltp.id
                join t in _ctx.Teachers on l.teacher_id equals t.id
                join g in _ctx.Groups on l.group_id equals g.id
                join sn in _ctx.SubjectNames on l.subject_name_id equals sn.id
                join wd in _ctx.WeekDays on l.day_num_id equals wd.id
                join ltm in _ctx.LessonTimes on l.start_lesson_num equals ltm.id
                where t.FIO == fio
                select new ScheduleView
                {
                    subjectName = sn.name,
                    prepodName = t.FIO,
                    className = c.name,
                    lessonType = ltp.name,
                    lessonStart = ltm.start_time,
                    lessonEnd = (from ltm1 in _ctx.LessonTimes
                                 where ltm1.id == (l.start_lesson_num + l.lesson_duration - 1)
                                 select ltm1.end_time).Single(),
                    groupName = g.name,
                    subGroup = l.subgroup,
                    isRemote = l.remote,
                    weekNum = l.week_num,
                    weekDayName = wd.name,
                    duration = l.lesson_duration,
                    description = l.description,
                    weekdayNum = wd.id
                };
            return schedules.ToList();
        }
    }
}
