using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.SelfHost;
using Unity;
using Unity.AspNet.WebApi;
using Unity.Lifetime;
using WifiNetworkLocalizer.Model;
using WifiNetworkLocalizer.Model.Database_Handlers;

namespace WifiNetworkLocalizer
{
    class StartUp
    {
        static void Main(string[] args)
        {
            var config = new HttpSelfHostConfiguration("http://localhost:1471");

            config.MapHttpAttributeRoutes();

            var container = BuildUnityContainer();
            config.DependencyResolver = new UnityDependencyResolver(container);

            //using (var ctx = new SchoolContext())
            //{
            //    var stud = new Student() { StudentName = "Bill" };

            //    ctx.Students.Add(stud);
            //    ctx.SaveChanges();
            //}

            using (HttpSelfHostServer server = new HttpSelfHostServer(config))
            {
                server.OpenAsync().Wait();
                Console.WriteLine("Press Enter to quit.");
                Console.ReadLine();
            }

        }

        private static IUnityContainer BuildUnityContainer()
        {
            var container = new UnityContainer();

            ILocalization localization = new Localization();
            container.RegisterInstance<ILocalization>(localization, new HierarchicalLifetimeManager()); 

            return container;
        }
    }
}
