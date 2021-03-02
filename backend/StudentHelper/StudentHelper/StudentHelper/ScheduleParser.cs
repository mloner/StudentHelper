using OfficeOpenXml;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;

namespace StudentHelper
{
    public class ScheduleParser
    {
        public static void Parse()
        {
            var package = new ExcelPackage(new FileInfo(@"ОН_ФЭВТ_3 курс с 1 марта.xls"));
            ExcelWorksheet sheet = package.Workbook.Worksheets[1];

            var table = sheet.Tables.First();
        }
    }
}
