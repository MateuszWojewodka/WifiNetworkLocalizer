using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Http.ExceptionHandling;

namespace Controller
{
    public class CustomExceptionLogger : ExceptionLogger
    {
        public override void Log(ExceptionLoggerContext context)
        {
            Console.ForegroundColor = ConsoleColor.Red;

            Console.WriteLine(context.Exception.Message);
            Console.WriteLine(context.Request);

            Console.ResetColor();
        }
    }
}
