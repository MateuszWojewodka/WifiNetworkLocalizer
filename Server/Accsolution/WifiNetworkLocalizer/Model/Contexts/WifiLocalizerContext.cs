using MySql.Data.Entity;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WifiNetworkLocalizer.Model.Message_Types;

namespace WifiNetworkLocalizer.Model.Database_Entities
{
    [DbConfigurationType(typeof(MySqlEFConfiguration))]
    public class WifiLocalizerContext : DbContext
    {
        public WifiLocalizerContext() : base("name=WifiNetworkDatabaseConnectionString")
        {
            Database.SetInitializer<WifiLocalizerContext>(new DropCreateDatabaseAlways<WifiLocalizerContext>());
        }

        public DbSet<ThreeMacIds> ThreeMeasurmentMacIds { get; set; }
    }
}
