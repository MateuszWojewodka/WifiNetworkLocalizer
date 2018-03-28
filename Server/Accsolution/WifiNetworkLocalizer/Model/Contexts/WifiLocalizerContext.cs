using Model.Entity_Classes;
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
            Database.SetInitializer<WifiLocalizerContext>(new CreateDatabaseIfNotExists<WifiLocalizerContext>());
        }

        public DbSet<DeterminantMacIds> DeterminantMacIds { get; set; }
        public DbSet<RSSIMeasurmentPoint> RSSIMeasurmentPoints { get; set; }
    }
}
