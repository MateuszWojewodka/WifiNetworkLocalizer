using MySql.Data.Entity;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WifiNetworkLocalizer.Model.Database_Entities
{
    [DbConfigurationType(typeof(MySqlEFConfiguration))]
    public class WifiLocalizerContext : DbContext
    {
        public WifiLocalizerContext() : base("name=WifiNetworkDatabaseConnectionString")
        {
            Database.SetInitializer<WifiLocalizerContext>(new DropCreateDatabaseIfModelChanges<WifiLocalizerContext>());
        }

        public DbSet<ThreeMacIdsForDB> Students { get; set; }
    }

    public class ThreeMacIdsForDB
    {
        public int ThreeMacIdsForDBId { get; set; }
        public string FirstMacId { get; set; }
        public string SecondMacId { get; set; }
        public string ThirdMacId { get; set; }
    }
}
