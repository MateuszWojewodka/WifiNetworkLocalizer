using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Unity;
using Unity.Injection;

namespace DependencyInjectionTest
{
    interface IExampleService
    {
        string Cos { get; set; }

        void napisz();
    }

    class ExampleService : IExampleService
    {
        public string Cos
        {
            get
            {
                throw new NotImplementedException();
            }

            set
            {
                throw new NotImplementedException();
            }
        }

        public void napisz()
        {
            Console.WriteLine("EXAMPLE SERVICE");
        }
    }

    class AnotherExampleService : IExampleService
    {
        private string NAPIS;

        public AnotherExampleService()
        {
            NAPIS = "PIERWOTNY KONST";
            Cos = "duppa";
        }

        public string Cos { get; set; }

        public void napisz()
        {
            Console.WriteLine(NAPIS);
        }
    }
}
