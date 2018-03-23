using Model;
using System;
using System.Collections.Generic;
using System.Configuration;
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
        private const string PORT = "1471";

        static Configuration Configuration;

        static void Main(string[] args)
        {
            Registration.ConfigureDBForMySql();

            Configuration = ReadConfiguration(Path.Combine(@"C:\Users\mateusz.wojewodka\Documents\Visual Studio 2015\Projects\wifiFromGit\Server\Accsolution\WifiNetworkLocalizer\Controller\DBConfig.config"));

            var config = GetPreparedWebApiConfig(PORT);

            using (HttpSelfHostServer server = new HttpSelfHostServer(config))
            {
                server.OpenAsync().Wait();
                Console.WriteLine("Press Enter to quit.");
                Console.ReadLine();
            }
        }

        private static Configuration ReadConfiguration(string configurationFilePath)
        {
            var configMap = new ExeConfigurationFileMap
            {
                ExeConfigFilename = configurationFilePath
            };

            return ConfigurationManager.OpenMappedExeConfiguration(configMap, ConfigurationUserLevel.None);
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

            container.RegisterType<ILocalization, Localization>(new HierarchicalLifetimeManager()); 

            return container;
        }
    }
}
