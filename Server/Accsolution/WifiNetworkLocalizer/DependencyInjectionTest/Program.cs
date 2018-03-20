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

            HomeController(new Func<string, IExampleService>(name => container.Resolve<IExampleService>(name)));
        }

        public static void HomeController(Func<string, IExampleService> serviceFactory)
        {
            IExampleService _service;

            var exampleServiceImplementation = "default"; // TODO get dynamically
            _service = serviceFactory(exampleServiceImplementation);
        }

        private static IUnityContainer BuildUnityContainer()
        {
            var container = new UnityContainer();

            container.RegisterType<IExampleService, ExampleService>("default");
            container.RegisterType<IExampleService, AnotherExampleService>("another");

            container.RegisterType<Func<string, IExampleService>>(
                new InjectionFactory(c =>
                new Func<string, IExampleService>(name => c.Resolve<IExampleService>(name))));

            //container.RegisterControllers();

            return container;
        }
    }
}
