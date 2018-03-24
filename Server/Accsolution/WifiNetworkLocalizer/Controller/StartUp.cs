using MySql.Data.Entity;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data.Entity;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.SelfHost;
using Unity;
using Unity.AspNet.WebApi;
using Unity.Lifetime;
using WifiNetworkLocalizer.Model.Database_Handlers;

namespace WifiNetworkLocalizer
{
    class StartUp
    {
        static void Main(string[] args)
        {
            string serverPort = "1471";

            SetEFForConnectionWithMySql();

            using (HttpSelfHostServer server = new HttpSelfHostServer(GetPreparedWebApiConfig(serverPort)))
            {
                server.OpenAsync().Wait();
                Console.WriteLine("Server is running...\nPress Enter to shut it down.\n");
                Console.ReadLine();
            }
        }

        #region HELPER_METHODS

        private static void SetEFForConnectionWithMySql()
        {
            DbConfiguration.SetConfiguration(new MySqlEFConfiguration());
        }

        private static HttpSelfHostConfiguration GetPreparedWebApiConfig(string port)
        {
            HttpSelfHostConfiguration config = new HttpSelfHostConfiguration($"http://localhost:{port}");
            config.MapHttpAttributeRoutes();

            config.DependencyResolver = new UnityDependencyResolver(GetNewUnityContainer());

            return config;
        }

        private static IUnityContainer GetNewUnityContainer()
        {
            var container = new UnityContainer();

            container.RegisterType<ILocalization, Localization>(new HierarchicalLifetimeManager()); 

            return container;
        }

        #endregion
    }
}
