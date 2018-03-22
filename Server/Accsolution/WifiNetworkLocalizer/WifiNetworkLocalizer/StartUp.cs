using MySql.Data.Entity;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Data.Entity;
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
        private const string PORT = "1471";

        static void Main(string[] args)
        {
            DbConfiguration.SetConfiguration(new MySqlEFConfiguration());

            var config = GetPreparedWebApiConfig(PORT);

            using (var ctx = new SchoolContext())
            {
                var stud = new Student() { StudentName = "Bill" };

                ctx.Students.Add(stud);
                ctx.SaveChanges();

                Console.WriteLine("Rekord dodany w bazie danych.");
            }

            using (HttpSelfHostServer server = new HttpSelfHostServer(config))
            {
                server.OpenAsync().Wait();
                Console.WriteLine("Press Enter to quit.");
                Console.ReadLine();
            }

        }

        private static HttpSelfHostConfiguration GetPreparedWebApiConfig(string port)
        {
            HttpSelfHostConfiguration config = new HttpSelfHostConfiguration("http://localhost:1471");
            config.MapHttpAttributeRoutes();

            config.DependencyResolver = new UnityDependencyResolver(GetNewUnityContainer());

            return config;
        }

        private static IUnityContainer GetNewUnityContainer()
        {
            var container = new UnityContainer();

            container.RegisterType<ILocalization>(new HierarchicalLifetimeManager()); 

            return container;
        }
    }
}
