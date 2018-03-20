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

    }

    class ExampleService : IExampleService
    {

    }

    class AnotherExampleService : IExampleService
    {

    }

    class IocContainer
    {


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
