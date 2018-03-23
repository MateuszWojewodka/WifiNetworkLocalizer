using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WifiNetworkLocalizer.Model.Database_Entities;
using WifiNetworkLocalizer.Model.Message_Types;

namespace WifiNetworkLocalizer.Model.Database_Handlers
{
    public class Localization : ILocalization
    {
        public Point GetXYLocalizationPoint(ThreeMacIds threeMacIds)
        {
            throw new NotImplementedException();
        }

        public List<string> GetPossibleBuildings()
        {
            using (var ctx = new WifiLocalizerContext())
            {
                var stud = new Student() { StudentName = "Bill" };

                ctx.Students.Add(stud);
                ctx.SaveChanges();

                Console.WriteLine("Rekord dodany w bazie danych.");
            }

            return null;
        }

        public ThreeMacIds GetThreeMeasurmentMacIds(string buildingName)
        {
            throw new NotImplementedException();
        }

        public void PutThreeMeasurmentPointsIntoDatabase(ThreeMacIds threeMacIds)
        {
            //using (var ctx = new SchoolContext())
            //{
            //    var stud = new Student() { StudentName = "Bill" };

            //    ctx.Students.Add(stud);
            //    ctx.SaveChanges();

            //    Console.WriteLine("Rekord dodany w bazie danych.");
            //}
        }
    }
}
