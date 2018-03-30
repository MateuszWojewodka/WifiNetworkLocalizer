using AutoMapper;
using Controller;
using Model.Database_Classes;
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
using System.Web.Http.ExceptionHandling;
using System.Web.Http.SelfHost;
using Unity;
using Unity.AspNet.WebApi;
using Unity.Lifetime;
using WifiNetworkLocalizer.Model.Database_Handlers;
using WifiNetworkLocalizer.Model.Message_Types;

namespace WifiNetworkLocalizer
{
    class StartUp
    {
        static void Main(string[] args)
        {
            string serverPort = "1471";

            ConfigureEFForConnectionWithMySql();
            using (HttpSelfHostServer server = new HttpSelfHostServer(GetPreparedWebApiConfig(serverPort)))
            {
                server.OpenAsync().Wait();
                Console.WriteLine("Server is running...\nPress Enter to shut it down.\n");
                Console.ReadLine();
            }
        }

        #region HELPER_METHODS

        private static void ConfigureEFForConnectionWithMySql()
        {
            DbConfiguration.SetConfiguration(new MySqlEFConfiguration());
        }

        private static HttpSelfHostConfiguration GetPreparedWebApiConfig(string port)
        {
            HttpSelfHostConfiguration config = new HttpSelfHostConfiguration($"http://localhost:{port}");
            config.MapHttpAttributeRoutes();

            config.DependencyResolver = new UnityDependencyResolver(GetNewUnityContainer());

            config.Services.Replace(typeof(IExceptionLogger), new CustomExceptionLogger());

            return config;
        }

        private static IUnityContainer GetNewUnityContainer()
        {
            var container = new UnityContainer();

            container.RegisterType<ILocalization, Localization>(new HierarchicalLifetimeManager());
            container.RegisterInstance<IMapper>(GetConfiguredMapper());

            return container;
        }

        private static IMapper GetConfiguredMapper()
        {
            var config = new MapperConfiguration
                (e =>
                {
                    e.CreateMap<DeterminantMacIds, ThreeMacIds>();
                });

            return config.CreateMapper();
        }
        #endregion
    }
}
