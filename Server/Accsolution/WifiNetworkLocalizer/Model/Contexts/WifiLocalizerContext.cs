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
            Database.SetInitializer<WifiLocalizerContext>(new DropCreateDatabaseAlways<WifiLocalizerContext>());
        }

        public DbSet<Student> Students { get; set; }
    }

    public class ThreeMacIdsForDB
    {
        public int ThreeMacIdsForDBId { get; set; }
        public string FirstMacId { get; set; }
        public string SecondMacId { get; set; }
        public string ThirdMacId { get; set; }
    }

    public class Student
    {
        public int StudentID { get; set; }
        public string StudentName { get; set; }
        public DateTime? DateOfBirth { get; set; }
        public byte[] Photo { get; set; }
        public decimal Height { get; set; }
        public float Weight { get; set; }
    }
}
