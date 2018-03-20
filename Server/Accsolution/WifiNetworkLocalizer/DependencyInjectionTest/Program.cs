using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Unity;
using Unity.Injection;

namespace DependencyInjectionTest
{
    class Program
    {
        static void Main(string[] args)
        {
            var container = BuildUnityContainer();

            var example = container.Resolve<IExampleService>();

            Console.WriteLine(example.Cos);
            example.Cos = "smienilem";

            var example2 = container.
            Console.WriteLine(example2.Cos);

        }

        private static IUnityContainer BuildUnityContainer()
        {
            var container = new UnityContainer();

            container.RegisterType<IExampleService, AnotherExampleService>();
            //container.RegisterType<IExampleService, AnotherExampleService>(new InjectionProperty("Cos", "dupppa"));

            return container;
        }
    }
}
