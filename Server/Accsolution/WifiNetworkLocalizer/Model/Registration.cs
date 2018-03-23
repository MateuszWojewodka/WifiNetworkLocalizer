using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MySql.Data.Entity;
using System.Data.Entity;

namespace Model
{
    public static class Registration
    {
        public static void ConfigureDBForMySql()
        {
            DbConfiguration.SetConfiguration(new MySqlEFConfiguration());
        }
    }
}
